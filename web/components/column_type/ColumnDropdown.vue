<template>
  <v-edit-dialog :return-value="itemSync" cancel-text="Annuler" large save-text="Valider" @open="handleOpen" @save="handleSave">
    <slot>
      {{ itemSync }}
    </slot>

    <template #input>
      <validation-observer ref="validator">
        <validation-provider #default="{invalid, errors}" :rules="{required: true}" name="date" slim>
          <v-select v-model="tempItem" :items="field.props.items"></v-select>
        </validation-provider>
      </validation-observer>
    </template>
  </v-edit-dialog>
</template>

<script lang="ts">
import {Component, mixins, Ref} from "nuxt-property-decorator";
import {tableColumnMixin} from "~/mixins/table-column.mixin";

@Component
export default class PpColumnDropdown extends mixins(tableColumnMixin) {
  @Ref('validator')
  public validator!: any

  public tempItem = null

  handleOpen() {
    this.tempItem = this.itemSync
  }

  async handleSave() {
    const valid = await this.validator.validate()

    if (valid) {
      this.itemSync = this.tempItem
    }
  }
}
</script>

<style scoped>

</style>
