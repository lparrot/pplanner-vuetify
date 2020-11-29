<template>
  <div class="container mx-auto">
    <ValidationObserver ref="observer" v-slot="{ invalid }" class="flex flex-col items-center" tag="form" @submit.prevent="onSubmit">
      <Card class="w-full lg:w-1/2" title="Connexion">
        <ValidationProvider v-slot="{ errors }" name="username" rules="required">
          <Input v-model="formLogin.username" :bottom="false" :error="errors[0]" label="Nom d'utilisateur" placeholder="Nom d'utilisateur"></Input>
        </ValidationProvider>
        <ValidationProvider v-slot="{ errors }" name="password" rules="required">
          <Input v-model="formLogin.password" :bottom="false" :error="errors[0]" label="Mot de passe" placeholder="Mot de passe" type="password"></Input>
        </ValidationProvider>
        <Button :disabled="invalid" class="w-full mt-4">Se connecter</Button>
      </Card>
    </ValidationObserver>
  </div>
</template>

<script>
import Button from '@/components/Button'
import Card from '@/components/Card'
import Input from '@/components/Input'

export default {
  components: { Card, Button, Input },

  data () {
    return {
      formLogin: {
        username: null,
        password: null,
      },
      showPassword: false,
    }
  },

  created () {
    this.formLogin = {
      username: 'laurent.parrot',
      password: '123',
    }
  },

  methods: {
    async onSubmit () {
      const valid = await this.$refs.observer.validate()
      if (valid) {
        const err = null
        try {
          await this.$auth.loginWith('local', { data: this.formLogin })
        } catch (err) {
          console.log(err.response)
          await this.$auth.logout()
          this.$toast.show('Login ou mot de passe incorrect', { timeout: 3000 })
        }

      }
    },
  },
}
</script>
