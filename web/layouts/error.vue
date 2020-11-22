<template>
  <div>
    <template v-if="error.statusCode === 404">
      <div class="absolute top-0 left-0 h-screen w-screen bg-yellow-400 flex justify-center content-center flex-wrap z-50">
        <div class="font-sans text-white text-center">
          <div class="text-error inline-block">404</div>
          <div class="text-4xl">Page introuvable</div>

          <slot name="prev-link">
            <div class="text-lg mt-8">
              <div class="cursor-pointer underline" @click="$router.go(-1)">Revenir à la page précédente</div>
            </div>
          </slot>
        </div>
      </div>
    </template>
    <h1 v-else>
      {{ otherError }} </h1>
    <nuxt-link to="/">
      Home page
    </nuxt-link>
  </div>
</template>

<script>
export default {
  layout: 'empty',
  props: {
    error: {
      type: Object,
      default: null,
    },
  },
  data () {
    return {
      pageNotFound: 'Page introuvale',
      otherError: 'Une erreur est survenue',
    }
  },
  head () {
    const title =
      this.error.statusCode === 404 ? this.pageNotFound : this.otherError
    return {
      title,
    }
  },
}
</script>

<style scoped>
.text-error {
  font-size: 10rem;
}
</style>
