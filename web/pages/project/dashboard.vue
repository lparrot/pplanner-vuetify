<template>
  <v-container>
    <v-row>
      <v-col v-for="(stat, statIndex) in statistics" :key="statIndex" cols="12" lg="3" sm="6">
        <v-alert :icon="stat.icon" class="dashboard-item cursor-pointer" dark @click="$router.push(stat.to)">
          <div class="d-flex flex-column align-end">
            <div class="text-h6 grey--text text--lighten-1">{{ stat.label }}</div>
            <div class="text-h3 grey--text text--lighten-1">{{ stat.number }}</div>
          </div>
        </v-alert>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Vue from 'vue'
import { mapState } from 'vuex'

export default Vue.extend({
  middleware: 'project',

  computed: {
    ...mapState({
      statistics: state => state.project.statistics,
    }),
  },

  async fetch (ctx) {
    await ctx.store.dispatch('project/updateStatistics')
  },

  async mounted () {
    await this.$root.$on('project-selected', async () => {
      await this.$nuxt.refresh()
    })
  },
})
</script>

<style>
.dashboard-item {
  transition: ease-in-out 0.1s;
}

.dashboard-item:hover {
  transform-origin: center;
  transform: scale(1.02);
  z-index: 200;
  outline: none;
  border-color: #9ecaed !important;
  box-shadow: 0 0 10px #9ecaed !important;
}
</style>
