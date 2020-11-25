<template>
  <div class="p-4 m-2 text-sm font-bold bg-primary text-white shadow-lg rounded inline-flex absolute right-2 bottom-2 cursor-pointer z-30" @click="onContainerClick">
    <slot>
      <i class="far fa-envelope text-sm"></i>
      <span class="ml-2">Notifications</span>
    </slot>
  </div>
</template>

<script>
export default {
  name: 'Notifications',

  props: {
    items: {
      type: Array,
      default: () => [],
    },
    variant: {
      type: String,
      default: 'primary',
    },
  },

  methods: {
    onContainerClick () {
      console.log('click')
    },
  },
}
</script>

<style scoped>

</style>
