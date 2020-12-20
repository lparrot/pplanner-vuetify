<template>
	<v-app>
		<v-navigation-drawer v-if="$auth.loggedIn && selectedProject != null" v-model="drawer" app clipped color="blue-grey darken-3" dark fixed floating>
			<pp-workspace-menu v-model="selectedProject.id" @on-link-click="onLinkClick"></pp-workspace-menu>
		</v-navigation-drawer>

		<v-app-bar app clipped-left color="blue-grey darken-3" dark dense fixed flat>
			<v-app-bar-nav-icon v-if="$auth.loggedIn && selectedProject != null" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>

			<!-- Titre -->
			<v-toolbar-items class="d-inline-flex justify-center mr-2">
				<v-btn text to="/">
					<img alt="logo" src="/logo.png" width="24">
					<div v-if="!isTabletAndMobile" v-text="$config.app.title"></div>
				</v-btn>
			</v-toolbar-items>

			<!-- Projet courant -->
			<v-toolbar-items v-if="$auth.loggedIn">
				<v-bottom-sheet v-model="sheetProjectSelection">
					<template #activator="{ on, attrs }">
						<template v-if="selectedProject == null">
							<v-btn v-bind="attrs" v-on="on" text>Selectionner un projet</v-btn>
						</template>
						<template v-else>
							<v-btn v-bind="attrs" v-on="on" text>Espace de travail: {{ selectedProject.name }}</v-btn>
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

			<v-spacer></v-spacer>

			<!-- Icones de profil -->
			<template v-if="$auth.loggedIn">
				<v-btn icon to="/profile">
					<vue-avatar :size="24" :src="$auth.user.avatar" :username="$auth.user.fullname"></vue-avatar>
				</v-btn>
				<v-bottom-sheet v-model="sheetDeconnexion" persistent>
					<template #activator="{ on, attrs }">
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
		</v-app-bar>

		<v-main>
			<nuxt ref="nuxt_navigation"/>
		</v-main>
	</v-app>
</template>

<script>
import VueAvatar from 'vue-avatar'
import { mapState } from 'vuex'

export default {
  name: 'AppLayout',

  components: {
    VueAvatar,
  },

  data () {
    return {
      drawer: false,
      loading: false,
      mini: true,
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

    isTabletAndMobile () {
      return this.$vuetify.breakpoint.mdAndDown
    },
  },

  async middleware (ctx) {
    await ctx.$accessor.project.updateProjectList()
  },

  created () {
    this.menu = [
      { label: 'Administration', path: '/admin', icon: 'mdi-cog', showIf: () => this.$auth.loggedIn },
    ]
    this.drawer = !this.isTabletAndMobile
  },

  methods: {
    onLinkClick () {
      if (this.isTabletAndMobile) {
        this.drawer = false
      }
    },

    async logout () {
      await this.$auth.logout()
      await this.$router.push('/login')
      this.sheetDeconnexion = false
    },

    async selectProject (project) {
      const noSelectedProject = this.selectedProject == null
      await this.$store.dispatch('project/selectProject', project)
      if (noSelectedProject) {
        await this.$router.push('/project/dashboard')
      } else {
        this.$root.$emit('project-selected')
      }
      this.sheetProjectSelection = false
    },
  },
}
</script>
