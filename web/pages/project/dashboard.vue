<template>
  <v-container>
    <v-row>
      <v-col v-for="(stat, statIndex) in statistics" :key="statIndex" cols="12" lg="4" md="6">
        <v-alert :icon="stat.icon" prominent>
          {{ stat.label }} | {{ stat.number }}
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

  async asyncData (ctx) {
    await ctx.store.dispatch('project/updateStatistics')
  },
})
</script>
