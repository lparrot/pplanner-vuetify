<template>
  <v-container fluid>
    <pp-profile-card :user="user"></pp-profile-card>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'

@Component({
  middleware: 'auth',
})
export default class PageProfile extends Vue {

  public user?: User

  async asyncData (ctx) {
    const res = await ctx.$axios.$get('/auth/user_data')
    return { user: res.data }
  }

}
</script>
