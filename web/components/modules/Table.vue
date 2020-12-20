<template>
  <v-simple-table dense>
    <thead>
    <tr>
      <th v-for="(field, fieldIndex) in fields" :key="fieldIndex" v-bind="field.attrs">
        <div class="d-flex align-center justify-center flex-nowrap">
          <v-edit-dialog :return-value="field.label" cancel-text="Annuler" large save-text="Valider">
            <span class="text-h6 font-weight-bold">{{ field.label }}</span>
            <template v-slot:input>
              <v-text-field v-model="field.label"></v-text-field>
            </template>
          </v-edit-dialog>
          <v-btn v-if="!field.fixed" icon text>
            <v-icon dense small>mdi-chevron-down</v-icon>
          </v-btn>
        </div>
      </th>
      <th style="width: 1em">
        <v-btn icon text @click="onClickAddNewField">
          <v-icon color="green" dense small>mdi-plus-circle</v-icon>
        </v-btn>
      </th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(item, itemIndex) in items" :key="itemIndex">
      <td v-for="(field, fieldIndex) in fields" :key="fieldIndex" class="pp-table_field">
        <v-edit-dialog :return-value="item[itemIndex + '-' + fieldIndex]" cancel-text="Annuler" large save-text="Valider">
          <div>{{ item[itemIndex + '-' + fieldIndex] }}</div>
          <template v-slot:input>
            <v-text-field v-model="item[itemIndex + '-' + fieldIndex]"></v-text-field>
          </template>
        </v-edit-dialog>
      </td>
      <td></td>
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

<script>
import { stateMixin } from '@/mixins/state.mixin'

export default {
  mixins: [stateMixin],

  data () {
    return {
      fields: [
        { label: '', fixed: true },
        { label: 'Personne', attrs: { width: '64px' } },
      ],
      items: [],
      ...this.$props.module.state,
    }
  },

  created () {
    console.log(this.$data)
  },

  methods: {
    onClickAddNewElement () {
      this.items.push({})
    },

    onClickAddNewField () {
      this.fields.push({ label: 'Colonne' })
    },
  },
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
