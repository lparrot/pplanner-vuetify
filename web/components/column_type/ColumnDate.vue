<template>
  <v-edit-dialog :return-value="itemSync" cancel-text="Annuler" large save-text="Valider" @open="handleOpen" @save="handleSave">
    <slot>
      {{ itemSyncFormatted }}
    </slot>

    <template #input>
      <validation-observer ref="validator">
        <validation-provider #default="{invalid, errors}" :rules="{required: true}" name="date" slim>
          <v-date-picker v-model="tempItem" class="mt-4" flat locale="fr" no-title></v-date-picker>
        </validation-provider>
      </validation-observer>
    </template>
  </v-edit-dialog>
</template>

<script lang="ts">
import {Component, mixins, Ref} from "nuxt-property-decorator";
import {tableColumnMixin} from "~/mixins/table-column.mixin";

@Component
export default class PpColumnDate extends mixins(tableColumnMixin) {
  @Ref('validator')
  public validator!: ValidationContext

  public tempItem = null

  get itemSyncFormatted() {
    if (this.itemSync == null) {
      return null
    }

    const [year, month, day] = this.itemSync.split('-')
    return `${day}/${month}/${year}`
  }

  handleOpen() {
    this.tempItem = this.itemSync
  }

  async handleSave() {
    const valid = await this.validator.validate()

    if (valid) {
      console.log(this.tempItem);
      this.itemSync = this.tempItem
    }
  }
}
</script>

<style scoped>

</style>
