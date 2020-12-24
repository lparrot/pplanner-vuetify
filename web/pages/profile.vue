<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col cols="12" md="6">
        <v-card class="mx-auto" tile>
          <v-img height="100%" src="https://cdn.vuetifyjs.com/images/cards/server-room.jpg"></v-img>
          <v-col>
            <v-avatar size="100">
              <v-img :src="user.avatar"></v-img>
            </v-avatar>
          </v-col>
          <v-list-item color="rgba(0, 0, 0, .4)">
            <v-list-item-content>
              <v-list-item-title class="title">{{ user.firstName + ' ' + user.lastName }}</v-list-item-title>
              <v-list-item-subtitle>{{ user.email }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import {Component, Vue} from 'nuxt-property-decorator'

@Component({
  middleware: 'auth',
})
export default class PageProfile extends Vue {

  public user?: User = null

  async asyncData(ctx) {
    const res = await ctx.$axios.$get('/api/security/user_data')
    return {user: res.data}
  }

}
</script>
