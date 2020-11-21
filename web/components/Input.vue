<template>
  <div class="inline">
    <label v-if="label != null" :for="_uid" class="text-sm leading-7 text-gray-700" v-text="label"></label>
    <InputError v-if="!bottom" :error="error" class="mb-2"></InputError>
    <input :id="_uid" v-model="p_value" v-bind="$attrs" :class="[error != null && 'border-danger bg-danger bg-opacity-10', {rounded: 'rounded'}]" class="block w-full border border-gray-400 py-1 px-4 text-gray-700 text-md focus:outline-none">
    <InputError v-if="bottom" :error="error" class="mt-2"></InputError>
  </div>
</template>

<script>
import InputError from '@/components/InputError'

export default {
  name: 'Input',
  components: { InputError },
  props: {
    bottom: {
      type: Boolean,
      default: true,
    },
    error: {
      type: String,
    },
    label: {
      type: String,
    },
    rounded: {
      type: Boolean,
      default: true,
    },
    value: {},
  },

  data () {
    return {
      p_value: this.value,
    }
  },

  watch: {
    p_value: {
      handler: function (newVal) {
        this.$emit('input', newVal)
      },
      immediate: true,
    },
  },
}
</script>

<style scoped>

</style>
