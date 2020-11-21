<template>
  <component :is="tag" v-bind="$attrs" v-on="$listeners" :active-class="`text-white bg-${p_variant}`" :class="[`hover:bg-${p_variant}-600`, link && 'cursor-pointer']" :to="$attrs.to || ''" class="px-4 py-2 mt-2 text-sm font-semibold bg-transparent rounded-lg md:mt-0 md:ml-2 hover:text-white focus:outline-none focus:shadow-outline">
    <slot></slot>
  </component>
</template>

<script>
export default {
  name: 'NavbarItem',

  inject: { navbar: { from: 'navbar' } },

  props: {
    link: {
      type: Boolean,
      default: false,
    },
    tag: {
      type: String,
      default: 'nuxt-link',
    },
    variant: {
      type: String,
    },
  },

  data () {
    return {
      p_variant: this.navbar.variant,
    }
  },

}
</script>

<style scoped>

</style>
