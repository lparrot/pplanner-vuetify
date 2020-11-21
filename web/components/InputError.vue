<template>
  <div v-if="error" class="text-danger text-sm" v-text="error"></div>
</template>

<script>
export default {
  name: 'InputError',

  props: {
    error: {
      type: String,
    },
  },
}
</script>

<style scoped>

</style>
