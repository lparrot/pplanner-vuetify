<template>
  <div>
    <v-skeleton-loader boilerplate type="table"></v-skeleton-loader>
  </div>
</template>

<script lang="ts">
import {stateMixin} from '@/mixins/state.mixin'
import {Component, Vue} from 'nuxt-property-decorator'

@Component({
  mixins: [stateMixin],
  data() {
    return {
      ...this.$props.module.state,
    }
  },
})
export default class PPTaskList extends Vue {
}
</script>

<style scoped>

</style>
