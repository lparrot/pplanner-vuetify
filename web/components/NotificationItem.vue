<template>
  <div class="relative p-4 my-1 text-sm font-bold bg-primary text-white shadow-lg rounded cursor-pointer z-30 max-w-full md:max-w-2xl">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'NotificationItem',
}
</script>
