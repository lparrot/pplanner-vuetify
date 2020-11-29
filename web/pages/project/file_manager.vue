<template>
  <div class="flex flex-grow">
    <div class="hidden xl:block xl:w-1/5">

      <Tree v-model="fileSelected" :nodes="nodes" :on-expand="onExpand" @on-node-click="onNodeClick">
        <template #icon(default)="{opened}">
          <i v-if="opened" class="fas fa-folder-open text-sm fa-fw text-yellow-400"></i>
          <i v-else class="fas fa-folder text-sm fa-fw text-yellow-400"></i>
        </template>
      </Tree>

    </div>
    <div class="xl:w-4/5">
      <div v-if="fileSelected != null">
        <table class="table-auto w-full text-left whitespace-no-wrap">
          <thead>
          <tr>
            <th class="table-th rounded-tl rounded-bl">Nom du fichier</th>
            <th class="table-th">Taille</th>
            <th class="table-th rounded-tr rounded-br">Dernière modification le</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(content, contentIndex) in contents" :key="contentIndex" class="hover:bg-gray-50 cursor-pointer" @click="onContentClick(content, contentIndex)">
            <td class="table-td">
              <i v-if="content.directory" class="fas fa-folder text-sm text-yellow-400"></i>
              <i v-else class="fas fa-file text-sm text-blue-400"></i>
              <span class="ml-2">{{ content.name }}</span>
            </td>
            <td class="table-td">{{ content.size }}</td>
            <td class="table-td">{{ content.updatedAt }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import Tree from '@/components/Tree'
import { mapState } from 'vuex'

export default {
  middleware: 'auth',

  components: { Tree },

  data () {
    return {
      fileSelected: null,
      nodes: [],
      contents: [],
    }
  },

  computed: {
    ...mapState({
      selectedProject: state => state.project.selectedProject,
    }),
  },

  async fetch () {
    const res = await this.$axios.$get(`/api/file_manager/projects/${this.selectedProject.id}/files`)
    this.nodes = res.data.map(directory => this.convertNode(directory))
  },

  methods: {
    async onNodeClick (event) {
      const res = await this.$axios.$get(`/api/file_manager/projects/${this.selectedProject.id}/files/${event.key}/contents`)
      this.contents = res.data
    },

    async onExpand ({ opened, node }) {
      if (!opened) {
        const res = await this.$axios.$get(`/api/file_manager/projects/${this.selectedProject.id}/files/${node.key}`)
        node.children = res.data.map(directory => this.convertNode(directory))
      }
    },

    async onContentClick (content, contentIndex) {
      console.log(content)
      if (content.directory) {
        this.fileSelected = content
      }
    },

    convertNode (directory) {
      return { key: directory.key, label: directory.name, data: directory, empty: directory.empty }
    },
  },
}
</script>

<style scoped>
.table-th {
  @apply px-4 py-3 tracking-wider font-medium text-gray-900 text-sm bg-gray-200;
}

.table-td {
  @apply px-4 py-3 border-t-2 border-b-2 border-gray-200;
}
</style>
