<template>
  <div :class="`bg-${variant}`" class="p-4 m-2 text-sm font-bold text-white shadow-lg rounded inline-flex absolute right-2 bottom-2 cursor-pointer z-30">
    <svg fill="currentColor" height="20px" viewBox="0 0 20 20" width="20px">
      <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"/>
      <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"/>
    </svg>
    <span class="ml-2">Notifications</span>
  </div>
</template>

<script>
export default {
  name: 'Notifications',

  props: {
    variant: {
      type: String,
      default: 'primary',
    },
  },
}
</script>

<style scoped>

</style>
