export const strict = false

export const state = () => ({
  selectedProject: null,
  projectList: [],
})

export const getters = {}

export const actions = {
  async selectProject (context, project) {
    context.commit('setSelectedProject', project)
  },

  async updateProjectList (context) {
    if (!this.$auth.loggedIn) {
      context.commit('setProjectList', [])
    } else {
      const res = await this.$axios.$get('/api/projects')
      context.commit('setProjectList', res.data)
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
}
