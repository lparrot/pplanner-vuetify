<template>
  <v-container fluid>
    <v-row>
      <v-col v-for="(stat, statIndex) in statistics" :key="statIndex" cols="12" lg="3" sm="6">
        <v-alert :icon="stat.icon" class="dashboard-item cursor-pointer" color="grey lighten-3" @click="$router.push(stat.to)">
          <div class="d-flex flex-column align-end">
            <div class="text-h6 grey--text text--darken-1">{{ stat.label }}</div>
            <div class="text-h3 grey--text text--darken-1">{{ stat.number }}</div>
          </div>
        </v-alert>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import {Component, namespace, Vue} from 'nuxt-property-decorator'

const projectModule = namespace('project')

@Component({
  middleware: 'project',
})
export default class PageProjectDashboard extends Vue {

  @projectModule.State statistics

  async fetch(ctx) {
    await ctx.$accessor.project.updateStatistics()
  }

  async mounted() {
    await this.$root.$on('project-selected', async () => {
      await this.$nuxt.refresh()
    })
  }
}
</script>

<style>
.dashboard-item {
  transition: ease-in-out 0.1s;
}

.dashboard-item:hover {
  transform-origin: center;
  transform: scale(1.02);
  outline: none;
  border-color: #757575 !important;
  box-shadow: 0 0 10px #757575 !important;
}
</style>
