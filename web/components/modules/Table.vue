<template>
  <div>
    <v-simple-table dense>
      <thead>
      <tr>
        <th class="pa-0" style="width: 16px"></th>
        <th v-for="(field, fieldIndex) in fields" :key="fieldIndex" v-bind="field.attrs">
          <div class="d-flex align-center justify-center flex-nowrap">
            <v-edit-dialog :return-value="field.label" cancel-text="Annuler" large save-text="Valider">
              <span class="text-h6 font-weight-bold">{{ field.label }}</span>
              <template v-slot:input>
                <v-text-field v-model.trim="field.label"></v-text-field>
              </template>
            </v-edit-dialog>
            <v-btn v-if="!field.fixed" :ripple="false" icon text tile width="20">
              <v-icon dense small>mdi-chevron-down</v-icon>
            </v-btn>
          </div>
        </th>
        <th class="pa-0" style="width: 18px">
          <v-btn :ripple="false" icon text tile width="16" @click="onClickAddNewField">
            <v-icon color="green" dense small style="width: 16px">mdi-plus-circle</v-icon>
          </v-btn>
        </th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, itemIndex) in items" :key="itemIndex">
        <td class="pa-0">
          <v-btn :ripple="false" icon text tile width="16" @click="onClickItemMenu(item, itemIndex)">
            <v-icon dense small style="width: 16px">mdi-chevron-down</v-icon>
          </v-btn>
        </td>
        <td v-for="(field, fieldIndex) in fields" :key="fieldIndex" class="pp-table_field">
          <v-edit-dialog :return-value="item[field.id]" cancel-text="Annuler" large save-text="Valider">
            <div>{{ item[field.id] }}</div>
            <template v-slot:input>
              <v-text-field v-model.trim="item[field.id]"></v-text-field>
            </template>
          </v-edit-dialog>
        </td>
        <td class="pa-0" style="width: 16px"></td>
      </tr>
      <tr>
        <td class="cursor-pointer" colspan="100%" @click="onClickAddNewElement">
          <div class="d-flex align-center">
            <v-icon color="green" small>mdi-plus</v-icon>
            <span class="grey--text">Ajouter un élément ...</span>
          </div>
        </td>
      </tr>
      </tbody>
    </v-simple-table>

    <v-bottom-sheet v-model="pr_sheetTableItem">
      <v-list dense nav>
        <v-subheader>Options</v-subheader>
        <v-list-item link @click="onClickDeleteItem">
          <v-list-item-avatar>
            <v-icon color="red" dense small>mdi-trash-can</v-icon>
          </v-list-item-avatar>
          <v-list-item-content>Supprimer</v-list-item-content>
        </v-list-item>
      </v-list>
    </v-bottom-sheet>
  </div>
</template>

<script lang="ts">
import {stateMixin} from '@/mixins/state.mixin'
import {Component, Vue} from 'nuxt-property-decorator'

@Component({
  mixins: [stateMixin],
  data() {
    return {
      ...this.$props.module.state,
    }
  },
})
export default class PPTable extends Vue {

  public fields: any[] = [
    {id: 0, label: '', fixed: true},
    {id: 1, label: 'Personne', attrs: {width: '64px'}},
  ]

  public items: any[] = []

  public lastFieldId = 1

  public pr_selectedItem: any = null

  public pr_sheetTableItem: boolean = false

  onClickAddNewElement() {
    this.items.push({})
  }

  onClickAddNewField() {
    this.fields.push({id: ++this.lastFieldId, label: 'Colonne'})
  }

  onClickDeleteItem() {
    this.items.splice(this.pr_selectedItem.itemIndex, 1)
    this.pr_sheetTableItem = false
  }

  onClickItemMenu(item, itemIndex) {
    this.pr_selectedItem = {item, itemIndex}
    this.pr_sheetTableItem = true
  }
}
</script>

<style lang="scss" scoped>
@import '~vuetify/src/styles/styles.sass';

.pp-table_field:hover {
  background: map-get($grey, lighten-5);
}

.v-data-table > .v-data-table__wrapper tbody tr:first-child td {
  border-radius: 0 !important;
}

.v-data-table > .v-data-table__wrapper tbody tr:hover td:not(:last-child) {
  border-right: thin solid map-get($grey, lighten-2);
}
</style>
