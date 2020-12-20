<template>
	<v-list-item :class="{'v-list-item--active': active}" dense link @click="onSelectBoard(board)">
		<pp-list-item-avatar>
			<pp-visibility-icon :visibility="board.visibility" dense small></pp-visibility-icon>
		</pp-list-item-avatar>
		<v-list-item-content v-text="board.name"></v-list-item-content>
	</v-list-item>
</template>

<script>
export default {
  props: ['active', 'board', 'selectedIndex'],

  methods: {
    onSelectBoard (board) {
      this.$emit('on-select-board', board)
    },
  },
}
</script>

<style scoped>
.v-list-item--dense, .v-list--dense .v-list-item {
	min-height: 0;
}
</style>
