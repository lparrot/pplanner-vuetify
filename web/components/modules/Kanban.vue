<template>
	<div>
		Kanban
	</div>
</template>

<script lang="ts">
import {stateMixin} from '@/mixins/state.mixin'
import Vue from 'vue'

export default Vue.extend({
	mixins: [stateMixin],

	data() {
		return {
			...this.$props.module.state,
		}
	}
})
</script>

<style scoped>

</style>
