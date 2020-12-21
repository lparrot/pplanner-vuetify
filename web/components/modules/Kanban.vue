<template>
  <div>
    <v-row>
      <v-col>
        <v-skeleton-loader boilerplate type="text"></v-skeleton-loader>
        <v-skeleton-loader boilerplate type="image@3"></v-skeleton-loader>
      </v-col>
      <v-col>
        <v-skeleton-loader boilerplate type="text"></v-skeleton-loader>
        <v-skeleton-loader boilerplate type="image@3"></v-skeleton-loader>
      </v-col>
      <v-col>
        <v-skeleton-loader boilerplate type="text"></v-skeleton-loader>
        <v-skeleton-loader boilerplate type="image@3"></v-skeleton-loader>
      </v-col>
      <v-col>
        <v-skeleton-loader boilerplate type="text"></v-skeleton-loader>
        <v-skeleton-loader boilerplate type="image@3"></v-skeleton-loader>
      </v-col>
    </v-row>
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
export default class PPKanban extends Vue {
}
</script>

<style scoped>

</style>
