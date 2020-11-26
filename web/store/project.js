export const strict = false

export const state = () => ({
  selectedProject: null,
  projectList: [],
  memberList: [],
  statistics: [],
})

export const getters = {}

export const actions = {
  async selectProject (ctx, project) {
    ctx.commit('setSelectedProject', project)
  },

  async updateProjectList (ctx) {
    if (!this.$auth.loggedIn) {
      ctx.commit('setProjectList', [])
    } else {
      const res = await this.$axios.$get('/api/projects')
      ctx.commit('setProjectList', res.data)
    }
  },

  async updateStatistics (ctx) {
    if (ctx.state.selectedProject != null) {
      const res = await this.$axios.$get(`/api/projects/${ctx.state.selectedProject.id}/statistics`)
      const statistics = [
        { icon: 'fas fa-sitemap', label: 'Members', number: res.data.members, to: '/project/team' },
        { icon: 'fas fa-folder', label: 'Fichiers', number: res.data.files, to: '/project/file_manager' },
        { icon: 'fas fa-tasks', label: 'Items de backlog', number: res.data.backlogs, to: '/project/backlog' },
        { icon: 'fas fa-clock', label: 'Nombre J.H', number: res.data.totalDuration, to: '/project/backlog' },
        { icon: 'fas fa-redo', label: 'Itérations', number: res.data.iterations, to: '/project/iteration' },
      ]
      ctx.commit('setStatistics', statistics)
    }
  },

  async findTeam (ctx) {
    if (ctx.state.selectedProject != null) {
      const res = await this.$axios.$get(`/api/projects/${ctx.state.selectedProject.id}/teams`)
      ctx.commit('setMemberList', res.data.members)
    }
  },
}

export const mutations = {
  setSelectedProject (state, project) {
    state.selectedProject = project
  },

  setProjectList (state, projects) {
    state.projectList = projects
  },

  setMemberList (state, members) {
    state.memberList = members
  },

  setStatistics (state, statistics) {
    state.statistics = statistics
  },
}
