<template>
  <div class="flex flex-grow">
    <div class="w-0 xl:w-1/5">
      <Tree v-model="fileSelected" :nodes="nodes">
        <template #icon="{opened}">
          <i v-if="opened" class="fas fa-folder-open"></i>
          <i v-else class="fas fa-folder"></i>
        </template>
      </Tree>
    </div>
    <div class="w-full xl:w-4/5">
      <div v-if="fileSelected != null">
        {{ fileSelected.data }}
      </div>
    </div>
  </div>
</template>

<script>
import Tree from '@/components/Tree'
import { mapState } from 'vuex'

export default {
  components: { Tree },

  data () {
    return {
      fileSelected: null,
      nodes: [],
    }
  },

  computed: {
    ...mapState({
      selectedProject: state => state.project.selectedProject,
    }),
  },

  async fetch () {
    const res = await this.requestFiles()
    this.nodes = res.data.map((directory, directoryIndex) => this.convertNode(null, directoryIndex, directory))
  },

  methods: {
    async onNodeOpen () {
      const res = await this.requestFiles()

      if (res.data != null && res.data.length <= 0) {
        this.nodes = [{ key: '', label: '', data: null }]
      } else {
        this.nodes = res.data.map(directory => this.convertNode(directory))
      }
    },

    convertNode (parent, index, directory) {
      const key = parent == null ? `${index}` : `${parent.key}.${index}`
      return { key, label: directory.name, data: directory }
    },

    async requestFiles () {
      return await this.$axios.$get(`/api/projects/${this.selectedProject.id}/files`)
    },
  },
}
</script>
