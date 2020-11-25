<template>
  <section>
    <div class="grid grid-cols-1 lg:grid-cols-5 gap-4">
      <nuxt-link v-for="(stat, statIndex) in statistics" :key="statIndex" :to="stat.to || ''" class="cursor-pointer text-center border-2 border-gray-200 px-4 py-6 rounded-lg" tag="div">
        <i :class="stat.icon" class="text-primary-500 text-center mb-3 text-6xl"></i>
        <h2 class="title-font font-medium text-3xl text-gray-800">{{ stat.number }}</h2>
        <p class="leading-relaxed">{{ stat.label }}</p>
      </nuxt-link>
    </div>
  </section>
</template>

<script>
export default {
  middleware: 'auth',

  data () {
    return {
      statistics: [
        { icon: 'fas fa-sitemap', label: 'Members', number: 0, to: '/project/team' },
        { icon: 'fas fa-folder', label: 'Fichiers', number: 0, to: '/project/file_manager' },
        { icon: 'fas fa-tasks', label: 'Items de backlog', number: 0, to: '/project/backlog' },
        { icon: 'fas fa-clock', label: 'Nombre J.H', number: 0, to: '/project/backlog' },
        { icon: 'fas fa-redo', label: 'Itérations', number: 0, to: '/project/iteration' },
      ],
    }
  },
}
</script>

<style scoped>

</style>
