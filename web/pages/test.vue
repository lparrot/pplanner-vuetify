<template>
  <section>
    <Button class="mx-1" @click="showSimpleModal">Show simple modal</Button>
    <Button class="mx-1" @click="showDynamicSimpleModal">Show dynamic modal</Button>
    <Button class="mx-1" @click="showDynamicConfirmModal">Show dynamic confirm</Button>

    <Modal ref="modal" close-on-backdrop title="Titre de la modal">
      Contenu de la modal
      <template #footer>
        Footer de la modal
      </template>
    </Modal>
  </section>
</template>

<script>
import Modal from '~/components/Modal'
import Button from '~/components/Button'

export default {
  components: { Button, Modal },

  methods: {
    showSimpleModal () {
      this.$refs.modal.show()
    },
    showDynamicSimpleModal () {
      this.$modal.message('Hello from <b>There !!!</b>', 'Mon titre')
    },
    showDynamicConfirmModal () {
      this.$modal.confirm('Etes vous sûr ?', 'Confirmation', [
        {
          label: 'Annuler', action: (modal) => modal.hide(),
        },
        {
          label: 'OK', action: (modal) => {
            this.$modal.message('Merci !')
          },
        }],
      )
    },
  },
}
</script>

<style scoped>

</style>
