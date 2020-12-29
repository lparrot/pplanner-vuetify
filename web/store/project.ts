import {actionTree, mutationTree} from 'nuxt-typed-vuex'

export const strict = false

/**
 * State
 */
export const state = () => ({
  memberList: [] as Array<any>,
  projectList: [] as Array<any>,
  selectedBoard: null as any,
  selectedProject: null as any,
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
  SET_MEMBER_LIST(state, members) {
    state.memberList = members
  },

  SET_PROJECT_LIST(state, projects) {
    state.projectList = projects
  },

  SET_SELECTED_BOARD(state, board) {
    state.selectedBoard = board
  },

  SET_SELECTED_PROJECT(state, project) {
    state.selectedProject = project
  },

  SET_STATISTICS(state, statistics) {
    state.statistics = statistics
  },
})

/**
 * Actions
 */
export const actions = actionTree(
  {
    state,
    getters,
    mutations,
  },
  {
    async selectProject({commit}, project) {
      commit('SET_SELECTED_PROJECT', project)
      commit('SET_SELECTED_BOARD', null)
    },

    async selectBoard({commit}, boardId) {
      commit('SET_SELECTED_BOARD', boardId)
    },

    async updateProjectList({commit}) {
      if (!this['$auth'].loggedIn) {
        commit('SET_PROJECT_LIST', [])
      } else {
        const res = await this.$axios.$get('/projects')
        commit('SET_PROJECT_LIST', res.data)
      }
    },

    async updateStatistics({commit, state}) {
      if (state.selectedProject != null) {
        try {
          const res = await this.$axios.$get(`/projects/${state.selectedProject.id}/statistics`)
          const statistics = [
            {icon: 'mdi-sitemap', label: 'Membres', number: res.data.members, to: '/project/team'},
            {icon: 'mdi-folder', label: 'Fichiers', number: res.data.files, to: '/project/fichier'},
            {icon: 'mdi-format-align-left', label: 'Items de backlog', number: res.data.backlogs, to: '/project/backlog'},
            {icon: 'mdi-clock', label: 'Nombre J.H', number: res.data.totalDuration, to: '/project/backlog'},
            {icon: 'mdi-sync', label: 'Itérations', number: res.data.iterations, to: '/project/iteration'},
          ]
          commit('SET_STATISTICS', statistics)
        } catch (err) {
          commit('SET_SELECTED_PROJECT', null)
        }
      }
    },

    async findTeam(ctx) {
      if (ctx.state.selectedProject != null) {
        const res = await this.$axios.$get(`/projects/${ctx.state.selectedProject.id}/teams`)
        ctx.commit('SET_MEMBER_LIST', res.data.members)
      }
    },
  },
)
