<template>
  <div>
    <Navbar :menu="menu" :show="$auth.loggedIn">
      <template #title>
        <span class="font-bold text-red-700">PP</span><span class="text-red-500">laner</span>
      </template>

      <template #right>
        <template v-if="!$auth.loggedIn">
          <NavbarItem to="/register">S'inscrire</NavbarItem>
          <NavbarItem to="/login">Se connecter</NavbarItem>
        </template>
        <template v-else>
          <NavbarItem link tag="a" @click="logout">Se déconnecter</NavbarItem>
        </template>
      </template>
    </Navbar>
    <div class="leading-8">
      <nuxt/>
    </div>
    <Notifications/>
  </div>
  <!--  <v-app id="main-app">-->
  <!--    <v-app-bar app clipped-left clipped-right color="blue-grey" dark>-->
  <!--      <v-app-bar-nav-icon @click.stop="drawer = !drawer" v-if="$vuetify.breakpoint.mobile"></v-app-bar-nav-icon>-->
  <!--      <v-btn @click="$router.push('/')" rounded text>-->
  <!--        <v-toolbar-title>PPlanner</v-toolbar-title>-->
  <!--      </v-btn>-->
  <!--      <v-spacer></v-spacer>-->
  <!--      <template v-if="$auth.loggedIn">-->
  <!--        <v-btn icon @click="logout">-->
  <!--          <v-icon>mdi-power</v-icon>-->
  <!--        </v-btn>-->
  <!--      </template>-->
  <!--      <template v-else>-->
  <!--        <v-btn icon @click="$router.push('/login')">-->
  <!--          <v-icon>mdi-login</v-icon>-->
  <!--        </v-btn>-->
  <!--      </template>-->
  <!--    </v-app-bar>-->

  <!--    <v-navigation-drawer ref="drawerRef" :mini-variant.sync="mini" app clipped :expand-on-hover="!$vuetify.breakpoint.mobile" v-model="drawer">-->
  <!--      <v-list v-if="$auth.loggedIn" dense>-->
  <!--        <v-list-item class="px-2">-->
  <!--          <v-list-item-avatar>-->
  <!--            <v-img :src="$auth.user.avatar"></v-img>-->
  <!--          </v-list-item-avatar>-->
  <!--          <v-list-item-content link v-if="!mini">-->
  <!--            <v-list-item-title class="title" v-text="$auth.user.fullname"></v-list-item-title>-->
  <!--            <v-list-item-subtitle v-text="$auth.user.email"></v-list-item-subtitle>-->
  <!--          </v-list-item-content>-->
  <!--        </v-list-item>-->
  <!--      </v-list>-->

  <!--      <v-divider></v-divider>-->

  <!--      <v-list dense>-->
  <!--        <v-list-item to="/">-->
  <!--          <v-list-item-action>-->
  <!--            <v-icon>mdi-home</v-icon>-->
  <!--          </v-list-item-action>-->
  <!--          <v-list-item-content>-->
  <!--            <v-list-item-title>Accueil</v-list-item-title>-->
  <!--          </v-list-item-content>-->
  <!--        </v-list-item>-->
  <!--      </v-list>-->
  <!--    </v-navigation-drawer>-->

  <!--    <v-main>-->
  <!--      <v-container fluid>-->
  <!--        <nuxt/>-->
  <!--      </v-container>-->
  <!--    </v-main>-->

  <!--    <v-footer app class="white&#45;&#45;text" color="blue-grey">-->
  <!--      <span>Vuetify</span>-->
  <!--      <v-spacer></v-spacer>-->
  <!--      <span>&copy; {{ new Date().getFullYear() }}</span>-->
  <!--    </v-footer>-->
  <!--  </v-app>-->
</template>

<script>
import Navbar from '@/components/Navbar'
import NavbarItem from '@/components/NavbarItem'
import Notifications from '@/components/Notifications'

export default {
  components: { NavbarItem, Notifications, Navbar },
  data () {
    return {
      menu: [],
    }
  },
  created () {
    this.menu = [
      { label: 'Accueil', path: '/' },
      {
        label: 'Administration', children: [
          { label: 'Utilisateur', path: '/administration/utilisateur' },
          { label: 'Profil', path: '/administration/profil' },
        ],
      },
      {
        label: 'Configuration', children: [
          { label: 'Application', path: '/configuration/application' },
          { label: 'Sécurité', path: '/configuration/securite' },
        ],
      },
    ]
  },
  methods: {
    async logout () {
      await this.$auth.logout()
      await this.$router.push('/login')
    },
  },
}
</script>
