<template>
  <div>
    <template v-if="error.statusCode === 404">
      <div class="absolute top-0 left-0 h-screen w-screen bg-yellow-400 flex justify-center content-center flex-wrap z-50">
        <div class="font-sans text-white text-center">
          <div class="text-error inline-block">{{ error.statusCode }}</div>
          <div class="text-4xl">Page introuvable</div>

          <slot name="prev-link">
            <div class="text-lg mt-8">
              <div class="cursor-pointer underline" @click="$router.go(-1)">Revenir à la page précédente</div>
            </div>
          </slot>
        </div>
      </div>
    </template>

    <template v-if="error.statusCode === 500">
      <div class="absolute top-0 left-0 h-screen w-screen bg-red-400 flex justify-center content-center flex-wrap z-50">
        <div class="font-sans text-white text-center">
          <div class="text-error inline-block">{{ error.statusCode }}</div>
          <div class="text-4xl">Erreur technique</div>

          <div v-if="message != null" class="text-sm mt-4">{{ message }}</div>

          <div v-if="detail.trace != null" class="text-sm mt-4 cursor-pointer" @click="$refs.modalTrace.show()">Voir la trace</div>

          <slot name="prev-link">
            <div class="text-lg mt-8">
              <div class="cursor-pointer underline" @click="$router.go(-1)">Revenir à la page précédente</div>
            </div>
          </slot>
        </div>
      </div>

      <Modal v-if="detail.trace != null" ref="modalTrace" close-on-backdrop size="6xl" title="Trace de l'erreur">
        <div class="text-xs text-left">
          <div class="mb-4">{{ detail.class }}</div>
          <div v-for="(traceLine, traceLineIndex) in detail.trace" :key="traceLineIndex">{{ traceLine }}</div>
        </div>
      </Modal>

    </template>

    <template v-else>
      <div class="absolute top-0 left-0 h-screen w-screen bg-yellow-400 flex justify-center content-center flex-wrap z-50">
        <div class="font-sans text-white text-center">
          <div class="text-error inline-block">{{ error.statusCode }}</div>

          <slot name="prev-link">
            <div class="text-lg mt-8">
              <div class="cursor-pointer underline" @click="$router.go(-1)">Revenir à la page précédente</div>
            </div>
          </slot>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import Modal from '@/components/Modal'

export default {
  components: { Modal },

  layout: 'empty',

  props: {
    error: {
      type: Object,
      default: null,
    },
  },

  head () {
    const title = this.error.statusCode === 404 ? 'Page introuvable' : 'Une erreur est survenue'
    return {
      title,
    }
  },

  async fetch () {
    if (this.error.statusCode === 500 && this.error.response == null) {
      await this.$router.push('/')
    }
  },

  computed: {
    responseData () {
      return this.error.response && this.error.response.data
    },

    message () {
      if (this.responseData && this.responseData.error) {
        return this.responseData.error
      }
      return this.error.message
    },

    detail () {
      if (this.responseData) {
        return this.responseData.detail
      }
    },
  },
}
</script>

<style scoped>
.text-error {
  font-size: 10rem;
}
</style>
