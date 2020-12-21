<template>
  <v-icon v-bind="$attrs">{{ visibility === 'PRIVATE' ? 'mdi-lock-outline' : 'mdi-eye-outline' }}</v-icon>
</template>

<script lang="ts">
import {Component, Prop, Vue} from 'nuxt-property-decorator'

@Component
export default class PPVisibilityIcon extends Vue {
  @Prop(String) readonly visibility
}
</script>
