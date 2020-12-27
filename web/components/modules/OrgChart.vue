<template>
  <div>
    Org Chart
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
export default class PPOrgChart extends mixins(stateMixin) {
}
</script>

<style scoped>

</style>
