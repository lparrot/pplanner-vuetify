<template>
  <v-row>
    <v-col cols="12">

    </v-col>
  </v-row>
</template>

<script>
import { tableFieldCreatorMixin } from '../../mixins/table-field-creator.mixin'
import { Component, mixins } from 'nuxt-property-decorator'

@Component
export default class PPColumnProgressCreator extends mixins(tableFieldCreatorMixin) {

  created () {
    this.$set(this.itemSync, 'props', this.itemSync.props || {})
  }

}
</script>

<style scoped>

</style>
