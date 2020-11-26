<template>
  <div class="flex flex-grow">
    <div class="bg-red-300 w-0 xl:w-1/5">

    </div>
    <div class="bg-blue-300 w-full xl:w-4/5">

    </div>
  </div>
</template>

<script>
export default {
  async asyncData (ctx) {
    const res = await ctx.$axios.$get('/api/projects/' + ctx.store.state.project.selectedProject.id + '/files')
  },
}
</script>
