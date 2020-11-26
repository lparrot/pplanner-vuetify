<template>
  <section class="tree-treenode">

    <template v-if="hasChildren">
				<span @click="onIconClick" class="cursor-pointer">
					<slot name="icon" :opened="d_opened">
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

    <span @click="onNodeClick" class="cursor-pointer">
				<slot name="node" :selected="selected" :node="node" :opened="d_opened" :multiple="tree.multiple">
					<span :class="classes">{{ node.label }}</span>
				</slot>
		</span>
    <TreeItem ref="treenode_children" v-if="showChildren" v-for="(child, childIndex) in node.children" :key="childIndex" class="ml-4" :node="child" v-bind="{$scopedSlots}" v-on="{$listeners}"></TreeItem>
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
        classes.push('bg-dark text-light p-1 rounded')
      }
      return classes
    },

    hasChildren () {
      console.log(this.node.children != null && this.node.children.length > 0)
      return this.node.children != null && this.node.children.length > 0
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

    onIconClick () {
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
