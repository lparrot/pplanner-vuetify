<template>
  <fragment>
    <TreeItem v-for="(node, nodeIndex) in nodes" :key="nodeIndex" v-bind="{$scopedSlots}" v-on="$listeners" :node.sync="node">
      <template #icon(default)>
        <i class="fas fa-folder"></i>
      </template>
    </TreeItem>
  </fragment>
</template>

<script>
import TreeItem from '@/components/TreeItem'

export default {
  name: 'Tree',

  components: { TreeItem },

  provide () {
    return {
      tree: this,
    }
  },

  props: {
    multiple: {
      type: Boolean,
      default: false,
    },

    nodes: {
      type: Array,
      default: () => [],
    },

    onExpand: {
      type: Function,
    },

    value: {
      type: Object | Array,
    },
  },

  data () {
    return {
      d_value: this.value,
    }
  },

  methods: {
    setValue (value) {
      this.d_value = value
      this.$emit('input', this.d_value)
    },

    addValue (key, data) {
      if (this.multiple) {
        const notExists = this.d_value.findIndex(value => value.key === key) < 0
        if (notExists) {
          this.d_value.push({ key, data })
          this.$emit('input', this.d_value)
        }
      }
    },

    removeValue (key) {
      if (this.multiple) {
        this.setValue(this.d_value.filter(it => it.key !== key))
      }
    },
  },

  watch: {
    value: {
      handler: function (val) {
        this.setValue(val)
      },
    },
    multiple: {
      handler: function (multiple) {
        // Si on passe en mode multiple, qu'une valeur existe déjà et que d_value n'est pas un tableau
        if (multiple && !Array.isArray(this.d_value)) {
          if (this.d_value == null) {
            this.setValue([])
          } else {
            this.setValue([this.d_value])
          }
        }
        // Si on passe en mode simple sélection et que d_value était déjà un tableau
        if (!multiple && Array.isArray(this.d_value)) {
          this.setValue(null)
        }
      },
      immediate: true,
    },
  },
}
</script>

<style scoped>

</style>
