<template>
	<div class="flex flex-grow">
		<div class="w-0 xl:w-1/5">
			<Tree v-model="fileSelected" :nodes="nodes"></Tree>
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

export default {
  components: { Tree },

  data () {
    return {
      fileSelected: null,
      nodes: [
        {
          key: '1', label: 'TreeNode1', data: 1, children: [
            { key: '1.1', label: 'TreeNode1.1', data: 11 },
            { key: '1.2', label: 'TreeNode1.2', data: 12 },
            {
              key: '1.3', label: 'TreeNode1.3', data: 13, children: [
                { key: '1.3.1', label: 'TreeNode1.3.1', data: 131 },
                { key: '1.3.2', label: 'TreeNode1.3.2', data: 132 },
                { key: '1.3.3', label: 'TreeNode1.3.3', data: 133 },
              ],
            },
          ],
        },
        {
          key: '2', label: 'TreeNode2', data: 2, children: [
            { key: '2.1', label: 'TreeNode2.1', data: 21 },
            { key: '2.2', label: 'TreeNode2.2', data: 22 },
          ],
        },
      ],
    }
  },

  async asyncData (ctx) {
    const res = await ctx.$axios.$get(`/api/file_manager/projects/${ctx.store.state.project.selectedProject.id}`)
  },
}
</script>
