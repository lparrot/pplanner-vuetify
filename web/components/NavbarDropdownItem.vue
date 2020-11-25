<template>
  <nuxt-link v-bind="$attrs" :class="[`hover:bg-${p_variant}`]" :to="$attrs.to || ''" class="block px-4 py-2 mt-2 text-sm font-semibold bg-transparent rounded-lg cursor-pointer md:mt-0 hover:text-white focus:outline-none focus:shadow-outline">
    <slot></slot>
  </nuxt-link>
</template>

<script>
export default {
  name: 'NavbarDropdownItem',

  inject: {
    navbar_dropdown: {
      from: 'navbar_dropdown',
    },
  },

  props: {
    variant: {
      type: String,
      default: 'primary',
    },
  },

  data () {
    return {
      p_variant: this.navbar_dropdown.variant,
    }
  },
}
</script>

<style scoped>

</style>
