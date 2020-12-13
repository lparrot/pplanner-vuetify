import {Module} from "@nuxt/types";
import chalk from 'chalk'
import packageJson from '../../package.json'

interface Options {

}

const appModule: Module<Options> = function (moduleOptions) {
  const {nuxt} = this

  nuxt.hook('listen', async function (server, {port}) {
    // Affichage d'un message dans l'encadré de Nuxt au démarrage de l'application
    nuxt.options.cli.badgeMessages.push(
      `App Version: ${chalk.underline.yellow(packageJson.version)}`
    )

    // Ajout d'une propriété dans $config
    nuxt.options.publicRuntimeConfig.app = {
      version: packageJson.version,
      title: nuxt.options.head.title
    }
  })
}

export default appModule
