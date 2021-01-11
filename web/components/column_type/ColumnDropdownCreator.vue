<template>
  <v-row>
    <v-col cols="12">
      <validation-observer>
        <validation-provider #default="{invalid, errors}" name="liste d'élément" rules="empty">
          <pp-item-collector v-model="itemSync.props.items" :before-create="handleBeforeShowItemCollector">

          </pp-item-collector>

          <div v-if="invalid" class="text-caption red--text">{{ errors[0] }}</div>
        </validation-provider>
      </validation-observer>
    </v-col>
  </v-row>
</template>

<script>
import { tableFieldCreatorMixin } from '../../mixins/table-field-creator.mixin'
import { Component, mixins } from 'nuxt-property-decorator'

@Component
export default class PPColumnDropdownCreator extends mixins(tableFieldCreatorMixin) {

  handleBeforeShowItemCollector (collector) {
    // collector.item = {}
  }

}
</script>

<style scoped>

</style>
