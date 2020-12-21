<template>
  <v-container fluid>Profil utilisateur</v-container>
</template>

<script lang="ts">

import {Component, Vue} from 'nuxt-property-decorator'

@Component({
  middleware: 'auth',
})
export default class PageProfile extends Vue {
}
</script>
