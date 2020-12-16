<template>
  <v-container fluid>
    <v-toolbar class="mb-4" dense>
      <v-btn color="green">
        <v-icon>mdi-plus</v-icon>
        <span>Ajouter un membre</span>
      </v-btn>
    </v-toolbar>

    <v-row dense>
      <v-col v-for="(member, memberIndex) in memberList" :key="memberIndex" class="d-flex flex-row align-self-stretch" cols="12" lg="3">
        <v-card class="w-full">
          <v-card-actions>
            <v-list-item>
              <v-list-item-avatar color="grey darken-3">
                <avatar :size="48" :src="member.avatar" :username="member.fullname" alt="avatar" class="elevation-6"></avatar>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title>{{ member.fullname }}</v-list-item-title>
                <v-list-item-subtitle>{{ member.job }}</v-list-item-subtitle>
              </v-list-item-content>

              <v-list-item-action>
                <v-btn icon>
                  <v-icon class="mx-1 red--text text--lighten-1">mdi-trash-can</v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Vue from 'vue'
import { mapState } from 'vuex'
import Avatar from 'vue-avatar'


export default Vue.extend({
  middleware: 'project',

  components: {
    Avatar,
  },

  computed: {
    ...mapState({
      selectedProject: state => state.project.selectedProject,
      memberList: state => state.project.memberList,
    }),
  },

  async fetch (ctx) {
    await ctx.store.dispatch('project/findTeam')
  },

  async mounted () {
    await this.$root.$on('project-selected', async () => {
      await this.$nuxt.refresh()
    })
  },
})
</script>
