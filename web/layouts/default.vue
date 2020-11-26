<template>
  <div class="container mx-auto">
    <Navbar :menu="menu" :show="$auth.loggedIn">
      <template #title>
        <span class="font-bold text-red-700">PP</span><span class="text-red-500">laner</span>
      </template>

      <template #right>
        <template v-if="!$auth.loggedIn">
          <NavbarItem to="/register">S'inscrire</NavbarItem>
          <NavbarItem to="/login">Se connecter</NavbarItem>
        </template>
        <template v-else>
          <div class="flex items-center">
            <Avatar v-if="$auth.user.email != null" :size="36" :src="$auth.user.avatar" :username="$auth.user.email" alt="avatar" class="rounded-full"></Avatar>
            <Dropdown :label="$auth.user.fullname" right>
              <DropdownItem to="/profile">Profil</DropdownItem>
              <DropdownItem @click.native="logout">Se deconnecter</DropdownItem>
            </Dropdown>
          </div>
        </template>
      </template>
    </Navbar>

    <div v-if="$auth.loggedIn" class="bg-default-100 rounded py-2">
      <div class="flex justify-between items-center mx-2 text-sm text-gray-600">
        <div class="flex flex-row items-center">
          <div>Projet selectionné:</div>
          <Dropdown :label="selectedProjectName">
            <DropdownItem v-for="(project, projectIndex) in projectList" :key="projectIndex" @click.native="selectProject(project)">{{ project.name }}</DropdownItem>
          </Dropdown>
        </div>

        <div v-if="selectedProject != null" class="flex flex-row">
          <nuxt-link class="btn-menu-project" to="/project/team">Equipe</nuxt-link>
          <nuxt-link class="btn-menu-project" to="/project/file_manager">Fichiers</nuxt-link>
          <nuxt-link class="btn-menu-project" to="/project/backlog">Backlog</nuxt-link>
          <nuxt-link class="btn-menu-project" to="/project/iteration">Itérations</nuxt-link>
        </div>
      </div>
    </div>

    <div class="p-4 leading-8">
      <nuxt ref="nuxt_navigation"></nuxt>
    </div>

    <div class="bg-default-100 rounded-b">
      <div class="container mx-auto py-4 px-5 flex flex-wrap flex-col sm:flex-row">
        <p class="text-gray-500 text-sm text-center sm:text-left">
          &copy; {{ new Date().getFullYear() }} PPlanner</p>
        <p class="sm:ml-auto sm:mt-0 mt-2 sm:w-auto w-full sm:text-left text-center text-gray-500 text-sm">
          <span>Mentions légales</span>
          <span class="mx-1">&#9642;</span>
          <span>Conditions d'utilisation</span>
        </p>
      </div>
    </div>

    <Notifications />
  </div>
</template>

<script>
import Avatar from 'vue-avatar'
import Navbar from '@/components/Navbar'
import NavbarItem from '@/components/NavbarItem'
import Notifications from '@/components/Notifications'
import { mapState } from 'vuex'
import Dropdown from '~/components/Dropdown'
import DropdownItem from '~/components/DropdownItem'

export default {
  components: { Avatar, DropdownItem, Dropdown, NavbarItem, Notifications, Navbar },

  data () {
    return {
      menu: [],
    }
  },

  async fetch () {
    await this.$store.dispatch('project/updateProjectList')
  },

  computed: {
    ...mapState({
      selectedProject: state => state.project.selectedProject,
      projectList: state => state.project.projectList,
    }),

    selectedProjectName () {
      return this.selectedProject != null ? this.selectedProject.name : 'Aucun projet selectionné'
    },
  },

  created () {
    this.menu = [
      { label: 'Accueil', path: '/' },
      {
        label: 'Administration', children: [
          { label: 'Utilisateur', path: '/administration/utilisateur' },
          { label: 'Profil', path: '/administration/profil' },
        ],
      },
      {
        label: 'Configuration', children: [
          { label: 'Application', path: '/configuration/application' },
          { label: 'Sécurité', path: '/configuration/securite' },
        ],
      },
    ]
  },

  methods: {
    async logout () {
      await this.$auth.logout()
      await this.$router.push('/login')
    },

    async selectProject (project) {
      const noSelectedProject = this.selectedProject == null
      await this.$store.dispatch('project/selectProject', project)
      if (noSelectedProject) {
        await this.$router.push('/dashboard')
      } else {
        // Utilisation du $forceUpdate pour rafraichir la page
        this.$refs.nuxt_navigation.$forceUpdate()
      }
    },
  },
}
</script>

<style scoped>
.btn-menu-project {
  @apply text-sm text-white bg-gray-500 p-2 mx-1 rounded;
}
</style>
