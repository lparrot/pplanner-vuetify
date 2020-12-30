<template>
  <v-container dark>
    <template v-if="error.statusCode === 404">
      <h1>Page introuvable</h1>
    </template>

    <template v-else>
      <h1>Erreur technique</h1>

      <p>{{ error.message }}</p>

      <template v-if="error.data != null">
        <div class="font-weight-bold">
          <span>{{ error.data.error }}</span>
          <template v-if="error.data.detail">[ {{ error.data.detail.class }} ]</template>
        </div>

        <v-divider class="my-4"></v-divider>

        <template v-if="error.data.detail">
          <div v-for="(detail, detailIndex) in error.data.detail.trace" :key="detailIndex" :class="{'font-weight-bold': detail.startsWith('fr.lauparr')}">{{ detail }}</div>
        </template>

      </template>
    </template>

    <NuxtLink to="/">
      Page d'accueil
    </NuxtLink>
  </v-container>
</template>

<script lang="ts">
import {Component, Prop, Vue} from 'nuxt-property-decorator'

interface NuxtError {
  data?: any
  message?: string
  statusCode?: number
}

@Component({})
export default class LayoutError extends Vue {
  @Prop() readonly error?: NuxtError

  head() {
    const title = this.error?.statusCode === 404 ? 'Page introuvable' : 'Erreur technique'
    return {
      title,
    }
  }
}
</script>

<style scoped>
h1 {
  font-size: 20px;
}
</style>
