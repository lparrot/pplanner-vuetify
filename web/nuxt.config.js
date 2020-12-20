import colors from 'vuetify/es5/util/colors'

const isDev = process.env.NODE_ENV === 'development'

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
      {
        name: 'viewport',
        content: 'width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no',
      },
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
    '~/plugins/pplanner',
  ],

  loading: '~/components/Loading.vue',

  // Auto import components (https://go.nuxtjs.dev/config-components)
  components: [
    '~/components/',
    { path: '~/components/pplanner/', prefix: 'pp' },
    { path: '~/components/modules/', prefix: 'pp', global: true },
  ],

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
    treeShake: true,
    theme: {
      dark: false,
      themes: {
        light: {
          primary: '#1976D2',
          secondary: '#424242',
          accent: '#82B1FF',
          error: '#FF5252',
          info: '#2196F3',
          success: '#4CAF50',
          warning: '#FB8C00',
        },
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
  build: {
    quiet: !isDev,
  },
}
