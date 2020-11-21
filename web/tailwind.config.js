const defaultTheme = require('./themes/default')

module.exports = {
  purge: [
    'src/**/*.vue',
    'src/**/*.html',
  ],
  darkMode: false, // or 'media' or 'class'
  theme: defaultTheme,
  variants: {
    extend: {},
  },
  plugins: [],
}
