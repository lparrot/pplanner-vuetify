export const strict = false

export const state = () => ({
  selectedProject: null,
  projectList: [],
  memberList: [],
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
}
