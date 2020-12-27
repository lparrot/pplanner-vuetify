<template>
  <div>
    <v-skeleton-loader boilerplate type="table"></v-skeleton-loader>
  </div>
</template>

<script lang="ts">
import { stateMixin } from '@/mixins/state.mixin'
import { Component, mixins } from 'nuxt-property-decorator'

@Component({
  data () {
    return {
      ...this.$props.module.state,
    }
  },
})
export default class PPTaskList extends mixins(stateMixin) {
}
</script>

<style scoped>

</style>
