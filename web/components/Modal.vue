<template>
  <Transition name="fade">
    <div v-show="opened" class="fixed h-full w-full left-0 top-0 bottom-50 right-0 bg-opacity-75 bg-black z-50 overflow-auto p-5" @click.self="onClickOutside">
      <div :class="`max-w-${size}`" class="relative w-full bg-white shadow-lg rounded-lg mx-auto">
        <button class="absolute w-4 h-4 ml-auto mt-2 right-2 top-0" @click="hide()">
          <i class="fas fa-times text-sm text-gray-500"></i>
        </button>
        <div v-if="hasTitle" class="text-gray-500 uppercase px-4 py-2 border-b border-gray-300">
          <div v-if="title">{{ title }}</div>
          <slot v-else name="title"></slot>
          <div class="divide-y divide-gray-300"></div>
        </div>
        <div class="px-4 py-2">
          <slot></slot>
        </div>
        <div v-if="hasFooter" class="px-4 py-2 border-t border-gray-300">
          <slot name="footer"></slot>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script>
import { mixin as clickaway } from 'vue-clickaway'

export default {
  name: 'Modal',

  mixins: [clickaway],

  props: {
    closeOnBackdrop: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
    },
    size: {
      type: String,
      default: '2xl',
    },
    value: {
      type: Boolean,
      default: false,
    },
  },

  data () {
    return {
      opened: this.value,
    }
  },

  computed: {
    hasTitle () {
      return this.title != null || (this.$slots.title != null && this.$slots.title.length > 0)
    },

    hasFooter () {
      return this.$slots.footer != null && this.$slots.footer.length > 0
    },

    overlayClasses () {
      return [
        this.opened ? '' : 'hidden',
      ]
    },
  },

  methods: {
    onClickOutside () {
      if (this.closeOnBackdrop) {
        this.hide()
      }
    },

    hide () {
      this.opened = false
    },

    show () {
      this.opened = true
    },

    toggle () {
      this.opened = !this.opened
    },
  },

  watch: {
    opened (value) {
      if (value) {
        this.$emit('on-show')
        return //document.querySelector('body').classList.add('overflow-hidden')
      }
      this.$emit('on-hide')
      //document.querySelector('body').classList.remove('overflow-hidden')
    },
  },
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  @apply transition-all duration-200
}

.fade-enter,
.fade-leave-to {
  @apply opacity-0
}
</style>
