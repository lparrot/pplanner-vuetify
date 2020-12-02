<template>
  <div class="w-full bg-white">
    <div class="flex flex-col px-4 mx-auto md:items-center md:justify-between md:flex-row md:px-6 lg:px-8">
      <div class="p-4 flex flex-row items-center justify-between">
        <slot name="before-title"></slot>
        <nuxt-link :to="brandHref" class="text-lg font-semibold tracking-widest text-gray-900 uppercase rounded-lg focus:outline-none focus:shadow-outline">
          <slot name="title">
            <span :class="`text-${titleColor}-500`" class="font-bold">{{ title }}</span>
          </slot>
        </nuxt-link>
        <slot name="after-title"></slot>
        <button class="md:hidden rounded-lg focus:outline-none focus:shadow-outline" @click="open = !open">
          <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20">
            <path v-if="!open" clip-rule="evenodd" d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM9 15a1 1 0 011-1h6a1 1 0 110 2h-6a1 1 0 01-1-1z" fill-rule="evenodd"></path>
            <path v-else clip-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" fill-rule="evenodd"></path>
          </svg>
        </button>
      </div>
      <nav :class="[open ? 'flex': 'hidden', right && 'md:justify-end']" class="flex-col flex-grow md:pb-0 md:flex md:flex-row block bg-white z-50 relative top-0 left-0">
        <slot name="before-menu"></slot>
        <template v-if="show">
          <template v-for="(menuItem, menuItemIndex) in menu">
            <NavbarItem v-if="menuItem.children == null" :key="menuItemIndex" :to="menuItem.path" exact>{{ menuItem.label }}</NavbarItem>
            <Dropdown v-else :key="menuItemIndex" :items="menuItem.children" :label="menuItem.label" navbar></Dropdown>
          </template>
        </template>
        <slot name="after-menu"></slot>
      </nav>
      <nav :class="[open ? 'flex': 'hidden']" class="flex flex-col block bg-white z-50 relative top-0 left-0 md:justify-end md:flex md:flex-row">
        <slot name="right"></slot>
      </nav>
    </div>
  </div>
</template>

<script>
import Dropdown from '@/components/Dropdown'
import NavbarItem from '@/components/NavbarItem'

export default {
  name: 'Navbar',

  components: { NavbarItem, Dropdown },

  provide () {
    return {
      navbar: this,
    }
  },

  props: {
    brandHref: {
      type: String,
      default: '/',
    },
    menu: {
      type: Array,
      default: [],
    },
    right: {
      type: Boolean,
      default: false,
    },
    show: {
      type: Boolean,
      default: true,
    },
    title: {
      type: String,
      default: 'Title',
    },
    titleColor: {
      type: String,
      default: 'red',
    },
    variant: {
      type: String,
      default: 'primary',
    },
  },

  data () {
    return {
      open: false,
    }
  },
}
</script>

<style scoped>

</style>
