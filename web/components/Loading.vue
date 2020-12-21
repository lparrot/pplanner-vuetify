<template>
  <v-overlay :value="loading">
    <v-progress-circular indeterminate size="64"></v-progress-circular>
  </v-overlay>
</template>

<script lang="ts">
import {Component, Vue} from 'nuxt-property-decorator'

@Component
export default class Loading extends Vue {
  loading: boolean = false

  start() {
    this.loading = true
  }

  finish() {
    this.loading = false
  }
}
</script>
