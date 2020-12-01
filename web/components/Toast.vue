<template>
  <Transition name="fade">
    <div v-if="opened" :class="classes" class="relative m-2 font-bold p-4 rounded border w-80 z-50 shadow-2xl">
      <button class="absolute h-4 w-4 top-1 right-2 focus:outline-none" @click="onCloseIconClick">
        <i class="fas fa-times"></i>
      </button>
      <slot></slot>
    </div>
  </Transition>
</template>

<script>
export default {
  name: 'Toast',

  props: {
    timeout: {
      type: Number,
      default: 3000,
    },
    variant: {
      type: String,
      default: 'info',
    },
  },

  data () {
    return {
      opened: false,
    }
  },

  computed: {
    classes () {
      return `bg-${this.variant}-500 text-${this.variant}-200`
    },
  },

  created () {
    setTimeout(() => {
      this.hide()
    }, this.timeout)
  },

  methods: {
    hide () {
      this.opened = false
    },

    show () {
      this.opened = true
    },

    toggle () {
      this.opened ? this.hide() : this.show()
    },

    onCloseIconClick () {
      this.hide()
    },
  },

  watch: {
    opened (value) {
      if (value) {
        this.$emit('on-show')
        return
      }
      setTimeout(() => {
        this.$emit('on-hide')
      })
    },
  },
}
</script>

<style scoped>

</style>
