<template>
  <v-container class="fill-height">
    <v-layout class="align-center justify-center">
      <v-card>
        <div class="display-1 text-center align-center mb-3">
          <v-icon class="mr-2" large>mdi-circle-slice-5</v-icon>
          <span v-text="$config.app.title"></span>
        </div>
        <v-card light>
          <v-card-text>
            <div class="subheading">
              <p>Connectez-vous pour accéder à votre tableau de bord</p>
            </div>
            <validation-observer ref="validator" tag="form" @submit.prevent="onSubmit">
              <validation-provider #default="{invalid, errors}" immediate name="nom d'utilisateur" rules="required" tag="div">
                <v-text-field v-model="form.username" :error="invalid" :error-messages="errors[0]" dense filled label="Nom d'utilisateur" light prepend-icon="mdi-email"></v-text-field>
              </validation-provider>
              <validation-provider #default="{invalid, errors}" immediate name="mot de passe" rules="required" tag="div">
                <v-text-field v-model="form.password" :error="invalid" :error-messages="errors[0]" dense filled label="Mot de passe" light prepend-icon="mdi-lock" type="password"></v-text-field>
              </validation-provider>
              <v-btn block="block" color="primary" type="submit">Se connecter</v-btn>
            </validation-observer>
          </v-card-text>
        </v-card>
      </v-card>
    </v-layout>
  </v-container>
</template>

<script>
import Vue from 'vue'

export default Vue.extend({
  auth: false,

  fetch (ctx) {
    if (ctx.$auth.loggedIn) {
      ctx.redirect('/project/dashboard')
    }
  },

  data () {
    return {
      form: {
        username: null,
        password: null,
      },
    }
  },

  methods: {
    async onSubmit () {
      const valid = await this.$refs.validator.validate()
      if (valid) {
        try {
          await this.$auth.loginWith('local', { data: this.form })
          await this.$router.push(this.$auth.$state.redirect || this.$auth.options.redirect.home || '/')
          // this.$toast.show('Vous êtes maintenant connecté', { variant: 'success' })
        } catch (err) {
          await this.$auth.logout()
          // this.$toast.show('Login ou mot de passe incorrect', { variant: 'danger' })
        }
      }
    },
  },
})
</script>
