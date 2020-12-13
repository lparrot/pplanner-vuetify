import {Middleware} from "@nuxt/types";

const projectMiddleware: Middleware = (context) => {
  if (!context['$auth'].loggedIn || context.app.$accessor.project.selectedProject == null) {
    context.redirect('/')
  }
}

export default projectMiddleware
