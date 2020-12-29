<template>
  <div>
    <!-- Tableau dynamique -->
    <v-simple-table dense>
      <thead>
      <tr>
        <th class="pa-0" style="width: 16px"></th>
        <th v-for="(field, fieldIndex) in fields" :key="fieldIndex" v-bind="field.attrs" :style="field.thStyles">
          <div class="d-flex align-center justify-center flex-nowrap">
            <pp-column-text :field="{props: {required: true}}" :item="field.label" @update:item="handleUpdateField(field, $event)">
              <span class="text-h6 font-weight-bold">{{ field.label }}</span>
            </pp-column-text>
            <v-btn v-if="!field.fixed" :ripple="false" icon text tile width="20" @click="handleClickFieldMenu(field, fieldIndex)">
              <v-icon dense small>mdi-chevron-down</v-icon>
            </v-btn>
          </div>
        </th>
        <th class="pa-0" style="width: 18px">
          <v-menu offset-y>
            <template #activator="{on, attrs}">
              <v-btn v-bind="attrs" v-on="on" :ripple="false" icon text tile width="16">
                <v-icon color="green" dense small style="width: 16px">mdi-plus-circle</v-icon>
              </v-btn>
            </template>
            <v-card>
              <div class="pa-4">
                <span class="subtitle-2 grey--text">Créer une nouvelle colonne</span>
                <div style="max-height: 50vh; overflow-y: auto">
                  <v-list class="pt-2 px-0" dense nav>
                    <v-list-item v-for="(columnType, columnTypeIndex) in pr_columnTypes" :key="columnTypeIndex" class="minh-0" link @click="handleClickAddNewField(columnType)">
                      <v-list-item-avatar size="16">
                        <v-icon dense small>{{ columnType.icon }}</v-icon>
                      </v-list-item-avatar>
                      <v-list-item-content>{{ columnType.text }}</v-list-item-content>
                    </v-list-item>
                  </v-list>
                </div>
              </div>
            </v-card>
          </v-menu>
        </th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, itemIndex) in items" :key="itemIndex">
        <td class="pa-0">
          <v-btn :ripple="false" icon text tile width="16" @click="handleClickItemMenu(item, itemIndex)">
            <v-icon dense small style="width: 16px">mdi-chevron-down</v-icon>
          </v-btn>
        </td>
        <td v-for="(field, fieldIndex) in fields" :key="fieldIndex" :style="field.tdStyles" class="pp-table_field">
          <component :is="field.type" :field="field" :item="item[field.id]" @update:item="handleUpdateItem(item, field.id, $event)"></component>
        </td>
        <td class="pa-0" style="width: 16px"></td>
      </tr>
      <tr>
        <td class="cursor-pointer" colspan="100%" @click="handleClickAddNewElement">
          <div class="d-flex align-center">
            <v-icon color="green" small>mdi-plus</v-icon>
            <span class="grey--text">Ajouter un élément ...</span>
          </div>
        </td>
      </tr>
      </tbody>
    </v-simple-table>

    <!-- Menu colonne tableau -->
    <v-bottom-sheet v-model="pr_sheetTableField">
      <v-list dense nav>
        <v-subheader>Options</v-subheader>
        <v-list-item link @click="handleClickDeleteField">
          <v-list-item-avatar size="16">
            <v-icon color="red" dense small>mdi-trash-can</v-icon>
          </v-list-item-avatar>
          <v-list-item-content>Supprimer</v-list-item-content>
        </v-list-item>
      </v-list>
    </v-bottom-sheet>

    <!-- Menu champ tableau -->
    <v-bottom-sheet v-model="pr_sheetTableItem">
      <v-list dense nav>
        <v-subheader>Options</v-subheader>
        <v-list-item link @click="handleClickDeleteItem">
          <v-list-item-avatar size="16">
            <v-icon color="red" dense small>mdi-trash-can</v-icon>
          </v-list-item-avatar>
          <v-list-item-content>Supprimer</v-list-item-content>
        </v-list-item>
      </v-list>
    </v-bottom-sheet>

    <!-- Dialog ajout colonne -->
    <v-dialog v-model="pr_dialogAddColumn" :width="$vuetify.breakpoint.mdAndDown ? '100vw': '60vw'">
      <validation-observer ref="validatorAddColumn" #default="{invalid}">
        <v-card v-if="pr_newField != null">
          <v-card-title>Ajout d'une nouvelle colonne</v-card-title>
          <v-card-text style="max-height: 75vh; overflow-y: auto">
            <v-row>
              <v-col cols="12" md="6">
                <validation-provider #default="{invalid, errors}" name="nom" rules="required" slim>
                  <v-text-field v-model="pr_newField.label" :error="invalid" :error-messages="errors[0]" dense label="Nom" outlined></v-text-field>
                </validation-provider>
              </v-col>
              <v-col cols="12" md="6">
                <validation-provider #default="{invalid, errors}" name="type" rules="required" slim>
                  <v-select v-model="pr_newField.type" :error="invalid" :error-messages="errors[0]" :items="pr_columnTypes" dense item-text="text" item-value="type" label="Type" outlined></v-select>
                </validation-provider>
              </v-col>
            </v-row>
            <component :is="pr_newField.type + '-creator'" :item="pr_newField"></component>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="red" text @click="pr_dialogAddColumn = false">Annuler</v-btn>
            <v-btn :disabled="invalid" color="green" text @click="handleSubmitNewField">Ajouter</v-btn>
          </v-card-actions>
        </v-card>
      </validation-observer>
    </v-dialog>
  </div>
</template>

<script lang="ts">
import {stateMixin} from '@/mixins/state.mixin'
import {Component, mixins, Ref} from 'nuxt-property-decorator'

interface Field {
  attrs?: any
  thStyles?: any
  tdStyles?: any
  fixed?: boolean
  id: number
  label: string
  props?: any
  type: string
}

interface FieldType {
  icon: string
  text: string
  type: string
  size?: string
  centered: boolean
}

@Component({
  data () {
    return {
      ...this.$props.module.state,
    }
  },
})
export default class PPTable extends mixins(stateMixin) {

  @Ref('validatorAddColumn')
  public validatorAddColumn!: ValidationContext

  public fields: Field[] = [
    { id: 0, label: '', fixed: true, type: 'pp-column-text', props: { required: true } }
  ]
  public items: any[] = []
  public lastFieldId = 0

  public pr_columnTypes: FieldType[] = []
  public pr_dialogAddColumn = false
  public pr_selectedField: any = null
  public pr_newField: Field = null
  public pr_selectedItem: any = null
  public pr_sheetTableField = false
  public pr_sheetTableItem = false

  created () {
    this.pr_columnTypes = [
      {type: 'pp-column-badge', icon: 'mdi-format-color-text', text: 'Badge', size: '100px', centered: true},
      {type: 'pp-column-checkbox', icon: 'mdi-format-color-text', text: 'Case à cocher', size: '100px', centered: true},
      {type: 'pp-column-date', icon: 'mdi-format-color-text', text: 'Date', size: '100px', centered: true},
      {type: 'pp-column-number', icon: 'mdi-format-color-text', text: 'Nombre', size: '100px', centered: true},
      {type: 'pp-column-progress', icon: 'mdi-format-color-text', text: 'Progression', size: '100px', centered: true},
      {type: 'pp-column-dropdown', icon: 'mdi-format-color-text', text: 'Selection', size: '100px', centered: true},
      {type: 'pp-column-text', icon: 'mdi-format-color-text', text: 'Texte', centered: false},
    ]
  }

  async handleClickAddNewElement () {
    this.items.push({})
    await this.saveState()
  }

  async handleClickAddNewField (columnType: FieldType) {
    this.pr_newField = {type: columnType.type}
    if (columnType.size != null) {
      this.pr_newField.thStyles = {'width': columnType.size}
    }
    if (columnType.centered) {
      this.pr_newField.tdStyles = {'text-align': 'center'}
    }
    this.pr_dialogAddColumn = true
  }

  async handleClickDeleteItem () {
    this.items.splice(this.pr_selectedItem.itemIndex, 1)
    this.pr_sheetTableItem = false
    await this.saveState()
  }

  async handleClickDeleteField () {
    this.fields.splice(this.pr_selectedField.fieldIndex, 1)
    this.pr_sheetTableField = false
    await this.saveState()
  }

  handleClickFieldMenu (field, fieldIndex) {
    this.pr_selectedField = { field, fieldIndex }
    this.pr_sheetTableField = true
  }

  handleClickItemMenu (item, itemIndex) {
    this.pr_selectedItem = { item, itemIndex }
    this.pr_sheetTableItem = true
  }

  async handleSubmitNewField () {
    const valid = await this.validatorAddColumn.validate()

    if (valid) {
      this.pr_newField.id = ++this.lastFieldId
      this.fields.push(this.pr_newField)
      await this.saveState()

      this.pr_dialogAddColumn = false
      this.pr_newField = null
    }
  }

  async handleUpdateField (field, value) {
    this.$set(field, 'label', value)
    await this.saveState()
  }

  async handleUpdateItem (item, field, value) {
    this.$set(item, field, value)
    await this.saveState()
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
