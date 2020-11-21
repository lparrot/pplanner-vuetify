<template>
  <div v-else v-on-clickaway="hide" class="relative">
    <button :class="`hover:bg-${variant}-600`" class="flex flex-row items-center w-full px-4 py-2 mt-2 text-sm font-semibold text-left bg-transparent rounded-lg md:w-auto md:inline md:mt-0 md:ml-4 hover:text-white focus:outline-none focus:shadow-outline" @click="toggle()">
      <slot>
        <span>{{ label }}</span>
      </slot>
      <svg :class="{'rotate-180': opened, 'rotate-0': !opened}" class="inline w-4 h-4 mt-1 ml-1 transition-transform duration-200 transform md:-mt-1" fill="currentColor" viewBox="0 0 20 20">
        <path clip-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" fill-rule="evenodd"></path>
      </svg>
    </button>
    <div v-show="opened" class="absolute right-0 w-full mt-2 origin-top-right rounded-md shadow-lg md:w-48">
      <div class="px-2 py-2 bg-white rounded-md shadow">
        <template v-for="(item, itemIndex) in items">
          <nuxt-link :key="itemIndex" :class="`hover:bg-${variant}-600`" :exact-active-class="`text-white bg-${variant}`" :to="item.path" class="block px-4 py-2 mt-2 text-sm font-semibold bg-transparent rounded-lg md:mt-0 hover:text-white focus:outline-none focus:shadow-outline" exact @click.native="onItemClick">{{ item.label }}</nuxt-link>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import { mixin as clickaway } from 'vue-clickaway'

export default {
  name: 'NarvarDropdown',

  mixins: [clickaway],

  props: {
    items: {
      type: Array,
      default: () => [],
    },
    label: {
      type: String,
      default: '',
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
      this.opened = !this.opened
    },

    onItemClick () {
      this.hide()
      this.$emit('on-item-click', { item: this.item, index: this.itemIndex })
    },
  },
}
</script>
