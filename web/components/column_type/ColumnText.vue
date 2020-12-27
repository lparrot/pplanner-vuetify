<template>
  <v-edit-dialog :return-value="itemSync" cancel-text="Annuler" large save-text="Valider" @open="tempItem = itemSync" @save="handleSave">
    <slot>
      {{ itemSync }}
    </slot>

    <template #input>
      <validation-observer ref="validator">
        <validation-provider #default="{invalid, errors}" :rules="{required: field.props.required === true}" name=" " slim>
          <v-text-field v-model.trim="tempItem" :error="invalid" :error-messages="errors[0]" clearable></v-text-field>
        </validation-provider>
      </validation-observer>
    </template>
  </v-edit-dialog>
</template>

<script lang="ts">
import { Component, mixins, Ref } from "nuxt-property-decorator";
import { tableColumnMixin } from "~/mixins/table-column.mixin";

@Component
export default class PpColumnText extends mixins(tableColumnMixin) {
  @Ref('validator')
  public validator!: ValidationContext

  public tempItem!: string = null

  mounted () {
    this.tempItem = this.itemSync
  }

  async handleSave () {
    let valid = true
    if (this.field.props.required) {
      valid = await this.validator.validate()
    }

    if (valid) {
      this.itemSync = this.tempItem
    }
  }
}
</script>

<style scoped>

</style>
