import createPersistedState from 'vuex-persistedstate'

export const plugins = [createPersistedState({
  key: ['pplanner.storage'],
  reducer: state => ({
    project: {
      selectedProject: state.project.selectedProject,
    },
  }),
})]
