<template>
  <component :is="tag" v-bind="$attrs" v-on="$listeners" :class="[`bg-${variant} hover:bg-${variant}-600`, disabledClasses]" class="rounded text-white px-2 py-1 font-bold focus:outline-none">
    <slot></slot>
  </component>
</template>

<script>
export default {
  name: 'Button',

  props: {
    disabled: {
      type: Boolean,
    },
    tag: {
      type: String,
      default: 'button',
    },
    variant: {
      type: String,
      default: 'primary',
    },
  },

  computed: {
    disabledClasses () {
      if (this.disabled) {
        return `bg-opacity-50 hover:bg-opacity-50 hover:bg-${this.variant}`
      }
    },
  },
}
</script>

<style scoped>

</style>
