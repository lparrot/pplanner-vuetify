<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" md="6">
        <v-card light>
          <v-card-title class="display-1">Connexion</v-card-title>
          <v-card-text>
            <div class="subheading">
              <p>Connectez-vous pour accéder à votre tableau de bord</p>
            </div>
            <validation-observer ref="validator" tag="form" @submit.prevent="handleSubmitForm">
              <validation-provider #default="{invalid, errors}" immediate name="nom d'utilisateur" rules="required|email" tag="div">
                <v-text-field v-model.trim="form.username" :error="invalid" :error-messages="errors[0]" dense filled label="Nom d'utilisateur" light prepend-icon="mdi-email" type="email"></v-text-field>
              </validation-provider>
              <validation-provider #default="{invalid, errors}" immediate name="mot de passe" rules="required" tag="div">
                <v-text-field v-model.trim="form.password" :error="invalid" :error-messages="errors[0]" dense filled label="Mot de passe" light prepend-icon="mdi-lock" type="password"></v-text-field>
              </validation-provider>
              <v-btn block text type="submit">Se connecter</v-btn>
              <v-btn v-if="isDev" block text @click="handleFillConnection">Connection avec kestounet@gmail.com</v-btn>
            </validation-observer>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'

interface PageLoginDataForm {
  username?: string
  password?: string
}

@Component({
  auth: false,
})
export default class PageLogin extends Vue {
  form: PageLoginDataForm = { username: undefined, password: undefined }

  get isDev () {
    return process.env.NODE_ENV === 'development'
  }

  fetch (ctx) {
    if (ctx.$auth.loggedIn) {
      ctx.redirect('/project/dashboard')
    }
  }

  handleFillConnection () {
    this.form.username = 'kestounet@gmail.com'
    this.form.password = '123'
  }

  async handleSubmitForm () {
    const validator: any = this.$refs.validator
    const valid = await validator.validate()
    if (valid) {
      try {
        await this.$auth.loginWith('local', { data: this.form })
        await this.$router.push(this.$auth.$state.redirect || this.$auth.options.redirect.home || '/')
        // TODO : Notification 'Vous êtes maintenant connecté'
      } catch (err) {
        await this.$auth.logout()
        // TODO : Notification 'Login ou mot de passe incorrect'
      }
    }
  }
}
</script>
