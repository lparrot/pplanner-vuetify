import Vue from "vue";

export const stateMixin = Vue.extend({
	props: {
		module: {
			type: Object,
		},
	},

	watch: {
		$data: {
			handler: async function (val) {
				await this.$axios.$patch(`/api/boards/modules/${this.module.id}`, {state: JSON.stringify(val)})
			},
			deep: true,
		},
	},
})
