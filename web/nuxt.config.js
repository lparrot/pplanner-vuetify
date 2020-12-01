export default {
  /*
  ** Nuxt rendering mode
  ** See https://nuxtjs.org/api/configuration-mode
  */
  ssr: false,
  /*
  ** Nuxt target
  ** See https://nuxtjs.org/api/configuration-target
  */
  target: 'static',
  /**
   * Target for generated files
   * See https://nuxtjs.org/api/configuration-generate
   */
  generate: {
    dir: 'target/dist',
  },
  /*
  ** Headers of the page
  ** See https://nuxtjs.org/api/configuration-head
  */
  head: {
    titleTemplate: '%s - PPlanner',
    title: 'PPlanner',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'description',
        name: 'description',
        content: 'Project planning application',
      },
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
    ],
  },
  /*
  ** Global CSS
  */
  css: [
    '@fortawesome/fontawesome-free/css/all.css',
    '~/assets/scss/styles.scss',
  ],
  /*
  ** Plugins to load before mounting the App
  ** https://nuxtjs.org/guide/plugins
  */
  plugins: [
    '~/plugins/app',
    '~/plugins/scoped-slots',
    '~/plugins/vue-fragment',
    '~/plugins/vee-validate',
  ],
  /*
  ** Auto import components
  ** See https://nuxtjs.org/api/configuration-components
  */
  components: true,
  /*
  ** Nuxt.js dev-modules
  */
  buildModules: [
    '@nuxtjs/tailwindcss',
    [
      'nuxt-purgecss', {
      paths: [
        'components/**/*.vue',
        'layouts/**/*.vue',
        'pages/**/*.vue',
        'plugins/**/*.js',
        'store/**/*.js',
        'nuxt.config.js',
      ],
    }],
  ],
  /*
  ** Nuxt.js modules
  */
  modules: [
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    '@nuxtjs/auth',
    '@nuxtjs/pwa',
  ],
  /*
  ** Axios module configuration
  ** See https://axios.nuxtjs.org/options
  */
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
  ** Auth module configuration
  ** See https://auth.nuxtjs.org/api
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
  /*
  ** Build configuration
  ** See https://nuxtjs.org/api/configuration-build/
  */
  build: {
    extractCSS: true,
    postcss: {
      preset: {
        features: {
          // Fixes: https://github.com/tailwindcss/tailwindcss/issues/1190#issuecomment-546621554
          'focus-within-pseudo-class': false,
        },
      },
    },
  },
}
