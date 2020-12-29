<template>
  <v-row>
    <v-col cols="12">
      <v-checkbox v-model="itemSync.props.required" dense label="Champ obligatoire"></v-checkbox>
    </v-col>
  </v-row>
</template>

<script>
import { tableFieldCreatorMixin } from '../../mixins/table-field-creator.mixin'
import { Component, mixins } from 'nuxt-property-decorator'

@Component
export default class PPColumnTextCreator extends mixins(tableFieldCreatorMixin) {

  created () {
    this.$set(this.itemSync, 'props', this.itemSync.props || {})
  }

}
</script>

<style scoped>

</style>
