<template>
  <div v-else v-on-clickaway="hide" class="relative">
    <button :class="`hover:bg-${variant}-600`" class="flex flex-row items-center w-full px-2 py-2 mt-2 text-sm font-semibold text-left bg-transparent rounded-lg md:w-auto md:inline md:mt-0 md:mx-2 hover:text-white focus:outline-none focus:shadow-outline" @click="toggle()">
      <span>{{ label }}</span>
      <i class="fas fa-chevron-down text-sm"></i>
    </button>
    <div v-show="opened" :class="{'right-0': right}" class="absolute mt-2 rounded-md shadow-lg md:w-48">
      <div class="px-2 py-2 bg-white rounded-md shadow">
        <template v-if="items != null && items.length > 0">
          <template v-for="(item, itemIndex) in items">
            <DropdownItem :key="itemIndex" :class="`hover:bg-${variant}-600`" :exact-active-class="`text-white bg-${variant}`" :to="item.path" exact @click.native="onItemClick">
              {{ item.label }}
            </DropdownItem>
          </template>
        </template>

        <template v-else>
          <slot></slot>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import { mixin as clickaway } from 'vue-clickaway'
import DropdownItem from '~/components/DropdownItem'

export default {
  name: 'Dropdown',

  components: { DropdownItem },

  provide () {
    return {
      dropdown: this,
    }
  },

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
    right: {
      type: Boolean,
      default: false,
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
      this.$emit('on-item-click', { item: this.item, index: this.itemIndex })
      this.hide()
    },
  },
}
</script>
