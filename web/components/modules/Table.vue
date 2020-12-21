<template>
  <v-simple-table dense>
    <thead>
    <tr>
      <th class="pa-0" style="width: 16px"></th>
      <th v-for="(field, fieldIndex) in fields" :key="fieldIndex" v-bind="field.attrs">
        <div class="d-flex align-center justify-center flex-nowrap">
          <v-edit-dialog :return-value="field.label" cancel-text="Annuler" large save-text="Valider">
            <span class="text-h6 font-weight-bold">{{ field.label }}</span>
            <template v-slot:input>
              <v-text-field v-model="field.label"></v-text-field>
            </template>
          </v-edit-dialog>
          <v-btn v-if="!field.fixed" icon text tile>
            <v-icon dense small>mdi-chevron-down</v-icon>
          </v-btn>
        </div>
      </th>
      <th class="pa-0" style="width: 18px">
        <v-btn icon text tile width="16" @click="onClickAddNewField">
          <v-icon color="green" dense small style="width: 16px">mdi-plus-circle</v-icon>
        </v-btn>
      </th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(item, itemIndex) in items" :key="itemIndex">
      <td class="pa-0">
        <v-btn icon text tile width="16">
          <v-icon dense small style="width: 16px">mdi-chevron-down</v-icon>
        </v-btn>
      </td>
      <td v-for="(field, fieldIndex) in fields" :key="fieldIndex" class="pp-table_field">
        <v-edit-dialog :return-value="item[itemIndex + '-' + fieldIndex]" cancel-text="Annuler" large save-text="Valider">
          <div>{{ item[itemIndex + '-' + fieldIndex] }}</div>
          <template v-slot:input>
            <v-text-field v-model="item[itemIndex + '-' + fieldIndex]"></v-text-field>
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

  fields: any[] = [
    {label: '', fixed: true},
    {label: 'Personne', attrs: {width: '64px'}},
  ]

  items: any[] = []

  onClickAddNewElement() {
    this.items.push({})
  }

  onClickAddNewField() {
    this.fields.push({label: 'Colonne'})
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
