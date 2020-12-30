<template>
  <v-edit-dialog :return-value="itemSync" cancel-text="Annuler" large save-text="Valider" @open="handleOpen" @save="handleSave">
    <slot>
      <v-chip v-if="itemSync != null" :color="itemSync.color" small text-color="white">{{ itemSync.text }}</v-chip>
    </slot>

    <template #input>
      <template v-if="tempItem != null">
        <validation-observer ref="validator">
          <validation-provider #default="{invalid, errors}" :rules="{required: true}" name="texte" slim>
            <v-text-field v-model="tempItem.text" :error="invalid" :error-messages="errors[0]" clearable></v-text-field>
          </validation-provider>
          <validation-provider #default="{invalid, errors}" :rules="{required: true}" name="couleur" slim>
            <v-color-picker v-model="tempItem.color" :swatches="swatches" flat hide-canvas hide-inputs hide-mode-switch hide-sliders show-swatches></v-color-picker>
          </validation-provider>
        </validation-observer>
      </template>
    </template>
  </v-edit-dialog>
</template>

<script lang="ts">
import {Component, mixins, Ref} from "nuxt-property-decorator";
import {tableColumnMixin} from "~/mixins/table-column.mixin";
import {sharedData} from '~/assets/shared-data'
import {ArrayUtils} from '~/utils/array.utils'

interface BadgeItem {
  color?: any
  text: string
}

@Component
export default class PpColumnBadge extends mixins(tableColumnMixin) {
  @Ref('validator')
  public validator!: ValidationContext

  public tempItem: BadgeItem = null
  public swatches = null

  handleOpen() {
    this.tempItem = this.itemSync || {}
    this.swatches = ArrayUtils.chunck(sharedData.swatches, 4)
  }

  async handleSave() {
    const valid = await this.validator.validate()

    if (valid) {
      this.itemSync = {
        text: this.tempItem.text,
        color: this.tempItem.color.hex || this.tempItem.color,
      }
    }
  }
}
</script>

<style scoped>

</style>
