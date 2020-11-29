<template>
  <nuxt-link v-bind="$attrs" :to="$attrs.to || ''" class="block px-4 py-2 mt-2 text-sm font-semibold bg-transparent rounded-lg cursor-pointer md:mt-0 hover:text-white hover:bg-primary-500 focus:outline-none focus:shadow-outline" @click.native="onItemClick">
    <slot></slot>
  </nuxt-link>
</template>

<script>
export default {
  name: 'DropdownItem',

  inject: {
    dropdown: {
      from: 'dropdown',
    },
  },

  methods: {
    onItemClick () {
      this.dropdown.hide()
    },
  },
}
</script>

<style scoped>

</style>
