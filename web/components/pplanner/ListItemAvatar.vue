<template>
	<v-list-item-avatar height="18" min-width="18" width="18">
		<slot></slot>
	</v-list-item-avatar>
</template>

<script lang="ts">
import {Component, Vue} from 'nuxt-property-decorator'

@Component
export default class PPListItemAvatar extends Vue {
}
</script>
