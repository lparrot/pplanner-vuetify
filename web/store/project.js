export const strict = false

export const state = () => ({
  project: null,
})

export const getters = {}

export const actions = {
  setProject (context, project) {
    context.commit('setProject', project)
  },
}

export const mutations = {
  setProject (state, project) {
    state.project = project
  },
}
