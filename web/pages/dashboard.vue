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
import { mapState } from 'vuex'

export default {
  middleware: 'auth',

  computed: {
    ...mapState({
      statistics: state => state.project.statistics,
    }),
  },

  async asyncData (ctx) {
    await ctx.store.dispatch('project/updateStatistics')
  },
}
</script>

<style scoped>

</style>
