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
            <img v-if="$auth.user.email != null" :src="$gravatar.url($auth.user.email)" alt="avatar" class="w-6 h-6 rounded-full">
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

        <div class="flex flex-row">
          <nuxt-link class="text-sm text-white bg-gray-500 p-2 rounded" to="/project/team">Equipe</nuxt-link>
        </div>
      </div>
    </div>

    <div class="p-4 leading-8">
      <nuxt></nuxt>
    </div>

    <div class="bg-default-100 rounded-b">
      <div class="container mx-auto py-4 px-5 flex flex-wrap flex-col sm:flex-row">
        <p class="text-gray-500 text-sm text-center sm:text-left">© 2020 tailblocks —
          PPlanner &copy; {{ new Date().getFullYear() }}
        </p>
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
import Navbar from '@/components/Navbar'
import NavbarItem from '@/components/NavbarItem'
import Notifications from '@/components/Notifications'
import Dropdown from '~/components/Dropdown'
import DropdownItem from '~/components/DropdownItem'
import { mapState } from 'vuex'

export default {
  components: { DropdownItem, Dropdown, NavbarItem, Notifications, Navbar },

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
      await this.$store.dispatch('project/selectProject', project)
      await this.$router.push('/dashboard')
    },
  },
}
</script>
