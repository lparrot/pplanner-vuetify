<template>
  <div v-on-clickaway="hide" class="absolute right-2 bottom-2 flex justify-center flex-col-reverse items-end">
    <div class="relative  bottom-0 right-0">
      <slot name="button">
        <button class="bg-primary text-white w-12 h-12 rounded-full font-bold shadow-lg z-30 focus:outline-none" @click="onContainerClick">
          <i class="fas fa-envelope"></i>
        </button>
      </slot>
    </div>

    <transition name="translate">
      <div v-show="opened" class="relative flex flex-col-reverse">
        <slot></slot>
      </div>
    </transition>
  </div>
</template>

<script>
import { mixin as clickaway } from 'vue-clickaway'

export default {
  name: 'Notifications',

  mixins: [clickaway],

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

  data () {
    return {
      opened: false,
    }
  },

  methods: {
    show () {
      this.opened = true
    },

    hide () {
      this.opened = false
    },

    toggle () {
      this.opened ? this.hide() : this.show()
    },

    onContainerClick () {
      this.toggle()
    },
  },
}
</script>

<style scoped>

</style>
