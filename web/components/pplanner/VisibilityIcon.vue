<template>
	<v-icon v-bind="$attrs">{{ visibility === 'PRIVATE' ? 'mdi-lock-outline' : 'mdi-eye-outline' }}</v-icon>
</template>

<script>
import Vue from 'vue'

export default Vue.extend({
  props: ['visibility'],
})
</script>
