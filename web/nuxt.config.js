import colors from 'vuetify/es5/util/colors'

export default {
  /*
** Nuxt rendering mode (https://nuxtjs.org/api/configuration-mode)
*/
  ssr: false,

  /*
  ** Nuxt target (https://nuxtjs.org/api/configuration-target)
  */
  target: 'static',

  // Global page headers (https://go.nuxtjs.dev/config-head)
  head: {
    titleTemplate: '%s - PPlanner',
    title: 'PPlanner',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
    ],
  },

  /**
   * Target for generated files (https://nuxtjs.org/api/configuration-generate)
   */
  generate: {
    dir: 'target/dist',
  },

  // Global CSS (https://go.nuxtjs.dev/config-css)
  css: [
    '~/assets/app.scss',
  ],

  // Plugins to run before rendering page (https://go.nuxtjs.dev/config-plugins)
  plugins: [
    '~/plugins/vee-validate',
  ],

  // Auto import components (https://go.nuxtjs.dev/config-components)
  components: true,

  // Modules for dev and build (recommended) (https://go.nuxtjs.dev/config-modules)
  buildModules: [
    // https://go.nuxtjs.dev/typescript
    '@nuxt/typescript-build',
    // https://go.nuxtjs.dev/vuetify
    '@nuxtjs/vuetify',
    'nuxt-typed-vuex',
  ],

  // Modules (https://go.nuxtjs.dev/config-modules)
  modules: [
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
    '@nuxtjs/auth',
    '~/modules/app',
  ],

  // Axios module configuration (https://go.nuxtjs.dev/config-axios)
  axios: {
    proxy: true,
  },

  proxy: {
    '/api': {
      target: 'http://[::1]:8080',
      changeOrigin: false,
    },
  },

  /*
  ** Auth module configuration (https://auth.nuxtjs.org/api)
  */
  auth: {
    cookie: false,
    watchLoggedIn: false,
    strategies: {
      local: {
        endpoints: {
          login: {
            url: '/api/security/login',
            method: 'post',
            propertyName: 'data.token',
          },
          user: {
            url: '/api/security/user',
            method: 'get',
            propertyName: 'data',
          },
          logout: false,
        },
      },
    },
    redirect: {
      callback: false,
      home: '/',
      login: '/login',
      logout: '/login',
    },
  },

  // Vuetify module configuration (https://go.nuxtjs.dev/config-vuetify)
  vuetify: {
    customVariables: ['~/assets/variables.scss'],
    theme: {
      dark: true,
      themes: {
        dark: {
          primary: colors.blue.darken2,
          accent: colors.grey.darken3,
          secondary: colors.amber.darken3,
          info: colors.teal.lighten1,
          warning: colors.amber.base,
          error: colors.deepOrange.accent4,
          success: colors.green.accent3,
        },
      },
    },
  },

  // Build Configuration (https://go.nuxtjs.dev/config-build)
  build: {},
}
