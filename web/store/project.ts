import {actionTree, mutationTree} from "nuxt-typed-vuex"

export const strict = false

/**
 * State
 */
export const state = () => ({
  selectedProject: null as any,
  projectList: [] as Array<any>,
  memberList: [] as Array<any>,
  statistics: [] as Array<any>,
})

/**
 * Getters
 */
export const getters = {}

/**
 * Mutations
 */
export const mutations = mutationTree(state, {
  SET_SELECTED_PROJECT(state, project) {
    state.selectedProject = project
  },

  SET_PROJECT_LIST(state, projects) {
    state.projectList = projects
  },

  SET_MEMBER_LIST(state, members) {
    state.memberList = members
  },

  SET_STATISTICS(state, statistics) {
    state.statistics = statistics
  },
})

/**
 * Actions
 */
export const actions = actionTree(
  {state, getters, mutations},
  {
    async selectProject({commit}, project) {
      commit('SET_SELECTED_PROJECT', project)
    },

    async updateProjectList({commit}) {
      if (!this["$auth"].loggedIn) {
        commit('SET_PROJECT_LIST', [])
      } else {
        const res = await this.$axios.$get('/api/projects')
        commit('SET_PROJECT_LIST', res.data)
      }
    },

    async updateStatistics({commit, state}) {
      if (state.selectedProject != null) {
        const res = await this.$axios.$get(`/api/projects/${state.selectedProject.id}/statistics`)
        const statistics = [
          {icon: 'mdi-sitemap', label: 'Members', number: res.data.members, to: '/project/equipe'},
          {icon: 'mdi-folder', label: 'Fichiers', number: res.data.files, to: '/project/fichier'},
          {icon: 'mdi-format-align-left', label: 'Items de backlog', number: res.data.backlogs, to: '/project/backlog'},
          {icon: 'mdi-clock', label: 'Nombre J.H', number: res.data.totalDuration, to: '/project/backlog'},
          {icon: 'mdi-sync', label: 'Itérations', number: res.data.iterations, to: '/project/iteration'},
        ]
        commit('SET_STATISTICS', statistics)
      }
    },

    async findTeam(ctx) {
      if (ctx.state.selectedProject != null) {
        const res = await this.$axios.$get(`/api/projects/${ctx.state.selectedProject.id}/teams`)
        ctx.commit('SET_MEMBER_LIST', res.data.members)
      }
    },
  }
)
