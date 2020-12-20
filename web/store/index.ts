import createPersistedState from 'vuex-persistedstate'
import {getAccessorType} from 'typed-vuex'

// Import all your submodules
import * as project from './project'

export const plugins = [createPersistedState({
	key: 'pplanner.storage',
	reducer: (state: any) => ({
		project: {
			selectedProject: state.project.selectedProject,
			selectedBoard: state.project.selectedBoard,
		},
	}),
})]

export const state = () => ({})

// This compiles to nothing and only serves to return the correct type of the accessor
export const accessorType = getAccessorType({
	state,
	modules: {
		project,
	},
})

