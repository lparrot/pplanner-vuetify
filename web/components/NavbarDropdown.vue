<template>
  <div v-else v-on-clickaway="hide" class="relative">
    <button :class="`hover:bg-${variant}-600`" class="flex flex-row items-center w-full px-2 py-2 mt-2 text-sm font-semibold text-left bg-transparent rounded-lg md:w-auto md:inline md:mt-0 md:mx-2 hover:text-white focus:outline-none focus:shadow-outline" @click="toggle()">
      <span>{{ label }}</span>
      <i class="fas fa-chevron-down text-sm"></i>
    </button>
    <div v-show="opened" class="absolute right-0 w-full mt-2 origin-top-right rounded-md shadow-lg md:w-48">
      <div class="px-2 py-2 bg-white rounded-md shadow">
        <template v-if="items != null && items.length > 0">
          <template v-for="(item, itemIndex) in items">
            <NavbarDropdownItem :key="itemIndex" :class="`hover:bg-${variant}-600`" :exact-active-class="`text-white bg-${variant}`" :to="item.path" exact @click.native="onItemClick">
              {{ item.label }}
            </NavbarDropdownItem>
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
import NavbarDropdownItem from '~/components/NavbarDropdownItem'

export default {
  name: 'NavbarDropdown',

  components: { NavbarDropdownItem },

  provide () {
    return {
      navbar_dropdown: this,
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
