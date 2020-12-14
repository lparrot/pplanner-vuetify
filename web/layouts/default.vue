<template>
  <v-app>
    <v-navigation-drawer v-if="$auth.loggedIn" v-model="drawer" :expand-on-hover="$vuetify.breakpoint.lgAndUp" :mini-variant="$vuetify.breakpoint.lgAndUp" app clipped fixed>
      <v-list dense nav>
        <v-list-item v-for="(menuItem, menuItemIndex) in menu" :key="menuItemIndex" :to="menuItem.path" exact router>
          <v-list-item-action class="justify-center">
            <v-icon small>{{ menuItem.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title v-text="menuItem.label"/>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app clipped-left dense fixed flat>
      <v-app-bar-nav-icon v-if="$vuetify.breakpoint.mdAndDown" @click.stop="drawer = !drawer"/>
      <v-toolbar-title class="mr-4">
        <v-btn text to="/">{{ $config.app.title }}</v-btn>
      </v-toolbar-title>
      <v-toolbar-items v-if="$auth.loggedIn">
        <v-bottom-sheet v-model="sheetProjectSelection">
          <template v-slot:activator="{ on, attrs }">
            <template v-if="selectedProject == null">
              <v-btn v-bind="attrs" v-on="on" text>Selectionner un projet</v-btn>
            </template>
            <template v-else>
              <v-btn v-bind="attrs" v-on="on" text>Projet: {{ selectedProject.name }}</v-btn>
            </template>
          </template>
          <v-list>
            <v-subheader>Selectionnez un projet</v-subheader>
            <v-list-item v-for="(project, projectIndex) in projectList" :key="projectIndex" @click="selectProject(project)">
              <v-list-item-title>{{ project.name }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-bottom-sheet>
      </v-toolbar-items>
      <v-spacer/>
      <template v-if="$auth.loggedIn">
        <v-btn icon to="/profile">
          <v-icon dense>mdi-account-box</v-icon>
        </v-btn>
        <v-bottom-sheet v-model="sheetDeconnexion" persistent>
          <template v-slot:activator="{ on, attrs }">
            <v-btn v-bind="attrs" v-on="on" icon>
              <v-icon dense>mdi-power</v-icon>
            </v-btn>
          </template>
          <v-sheet class="text-center">
            <v-btn class="mt-6" color="primary" text @click="sheetDeconnexion = !sheetDeconnexion">
              <span>Rester connecté</span>
            </v-btn>
            <v-btn class="mt-6" color="error" text @click="logout">
              <span>Se déconnecter</span>
            </v-btn>
            <div class="py-3">
              <p>Cliquez sur le bouton déconnexion pour vous déconnecter de l'application</p>
            </div>
          </v-sheet>
        </v-bottom-sheet>
      </template>
      <template v-else>
        <v-btn icon to="/login">
          <v-icon dense>mdi-login</v-icon>
        </v-btn>
      </template>

      <template v-if="$auth.loggedIn && selectedProject != null" #extension>
        <v-tabs fixed-tabs>
          <v-tab to="/project/dashboard">Tableau de bord</v-tab>
          <v-tab to="/project/equipe">Equipe</v-tab>
          <v-tab to="/project/fichier">Fichiers</v-tab>
          <v-tab to="/project/backlog">Backlog</v-tab>
          <v-tab to="/project/iteration">Itérations</v-tab>
        </v-tabs>
      </template>
    </v-app-bar>

    <v-main>
      <nuxt ref="nuxt_navigation"/>
    </v-main>

    <v-footer absolute app>
      <small>PPlanner &copy; {{ new Date().getFullYear() }}</small>
    </v-footer>
  </v-app>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'AppLayout',

  data () {
    return {
      drawer: false,
      menu: [],
      sheetDeconnexion: false,
      sheetProjectSelection: false,
    }
  },

  computed: {
    ...mapState({
      selectedProject: state => state.project.selectedProject,
      projectList: state => state.project.projectList,
    }),
  },

  async middleware (ctx) {
    await ctx.$accessor.project.updateProjectList()
  },

  created () {
    this.menu = [
      { label: 'Accueil', path: '/', icon: 'mdi-home' },
      { label: 'Administration', path: '/admin', icon: 'mdi-cog' },
    ]
  },

  async mounted () {
    this.drawer = this.$vuetify.breakpoint.lgAndUp
  },

  methods: {
    async logout () {
      await this.$auth.logout()
      await this.$router.push('/login')
      this.sheetDeconnexion = false
    },

    async selectProject (project) {
      console.log(project)
      const noSelectedProject = this.selectedProject == null
      await this.$store.dispatch('project/selectProject', project)
      if (noSelectedProject) {
        await this.$router.push('/project/dashboard')
      } else {
        // Utilisation du $forceUpdate pour rafraichir la page
        // this.$refs.nuxt_navigation.$forceUpdate()
        this.$root.$emit('project-selected')
      }
      this.sheetProjectSelection = false
    },
  },
}
</script>
