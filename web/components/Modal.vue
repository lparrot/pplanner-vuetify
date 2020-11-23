<template>
  <Transition name="fade">
    <div v-show="opened" class="fixed inset-0 w-full h-screen flex items-center justify-center bg-opacity-75 bg-black z-50" @click.self="onClickOutside">
      <div :class="`max-w-${size}`" class="relative w-full bg-white shadow-lg rounded-lg">
        <button class="absolute w-4 h-4 ml-auto mt-2 absolute right-2 top-0" @click="hide()">
          <svg class="fill-current text-gray-400 hover:text-gray-500" viewBox="0 0 20 20">
            <path clip-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" fill-rule="evenodd"/>
          </svg>
        </button>
        <div>
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
        return document.querySelector('body').classList.add('overflow-hidden')
      }
      this.$emit('on-hide')
      document.querySelector('body').classList.remove('overflow-hidden')
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
