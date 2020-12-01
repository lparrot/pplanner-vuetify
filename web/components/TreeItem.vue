<template>
  <section class="tree-treenode">

    <template v-if="hasChildren">
				<span @click="onIconClick" class="cursor-pointer">
					<slot :opened="d_opened" name="expand_icon">
						<i v-if="d_opened" class="treenode_icon" :class="iconOpen"></i>
						<i v-else class="treenode_icon" :class="iconClose"></i>
				  </slot>
				</span>
    </template>

    <template v-else>
      <i class="treenode_icon text-transparent" :class="iconClose"></i>
    </template>

    <template v-if="tree.multiple">
      <b-form-checkbox class="align-middle m-0" inline :checked="selected" @change="onCheckboxClick"></b-form-checkbox>
    </template>

    <span :class="classes" class="cursor-pointer" @click="onNodeClick">
        <slot :name="`icon(${node.type || 'default'})`" :opened="d_opened"></slot>
				<slot name="node" :selected="selected" :node="node" :opened="d_opened" :multiple="tree.multiple">
					<span class="transition-all duration-500 ease-out">{{ node.label }}</span>
				</slot>
		</span>

    <transition name="bounce">
      <div v-show="showChildren" class="ml-4 origin-top">
        <TreeItem v-for="(child, childIndex) in node.children" :key="childIndex" ref="treenode_children" v-bind="{$scopedSlots}" v-on="$listeners" :node="child"></TreeItem>
      </div>
    </transition>
  </section>
</template>

<script>
export default {
  name: 'TreeItem',

  inject: ['tree'],

  props: {
    iconClose: {
      type: String,
      default: 'fas fa-angle-right',
    },

    iconOpen: {
      type: String,
      default: 'fas fa-angle-down',
    },

    opened: {
      type: Boolean,
      default: false,
    },

    node: {
      type: Object,
      required: true,
    },
  },

  data () {
    return {
      d_opened: this.opened,
    }
  },

  computed: {
    classes () {
      let classes = []
      if (this.tree.d_value && this.selected) {
        classes.push('bg-primary text-white p-1 rounded')
      }
      return classes
    },

    hasChildren () {
      return !this.node.empty || (this.node.children != null && this.node.children.length > 0)
    },

    showChildren () {
      return this.hasChildren && this.d_opened
    },

    selected () {
      if (this.tree.d_value != null) {
        if (this.tree.multiple) {
          return this.tree.d_value.findIndex(value => value.key === this.node.key) > -1
        }
        return this.tree.d_value.key === this.node.key
      }
    },
  },

  mounted () {
    this.d_opened = this.node.opened
  },

  methods: {
    onNodeClick () {
      const data = { key: this.node.key, data: this.node.data }
      /**
       * Evènement lors d'un clic sur le node
       */
      this.$emit('on-node-click', data)
      if (!this.tree.multiple) {
        this.tree.setValue(data)
      }
    },

    onCheckboxClick (value) {
      if (value) {
        this.tree.addValue(this.node.key, this.node.data)
      } else {
        this.tree.removeValue(this.node.key)
      }

      if (this.node.children != null) {
        this.checkOrUncheckAllChildren(this.node.children, value)
      }
    },

    async onIconClick () {
      if (this.node.children == null && this.tree.onExpand != null) {
        await this.tree.onExpand({ opened: this.d_opened, node: this.node })
      }
      this.d_opened = !this.d_opened
    },

    checkOrUncheckAllChildren (children, value) {
      if (children != null) {
        children.forEach(child => {
          if (value) {
            this.tree.addValue(child.key, child.data)
          } else {
            this.tree.removeValue(child.key)
          }
          this.checkOrUncheckAllChildren(child.children, value)
        })
      }
    },
  },
}
</script>

<style scoped>
.treenode_icon {
  min-width: 20px;
}
</style>
