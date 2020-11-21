<template>
  <div class="shadow-lg bg-gray-100 p-4 rounded">
    <div v-if="hasTitle">
      <div v-if="title">{{ title }}</div>
      <slot v-else name="title"></slot>
      <div class="divide-y divide-gray-300"></div>
    </div>

    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'Card',

  props: {
    title: {
      type: String,
    },
  },

  computed: {
    hasTitle () {
      return this.title != null || !!this.$slots.title
    },
  },
}
</script>

<style scoped>

</style>
