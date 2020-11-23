<template>
  <div>
    <div class="container px-5 py-24 mx-auto">
      <div class="flex flex-col text-center w-full mb-20">
        <h1 class="sm:text-3xl text-2xl font-medium title-font mb-4 text-gray-900">Equipe du projet</h1>
        <p class="lg:w-2/3 mx-auto leading-relaxed text-base">Ici vous pourrez gérer votre équipe en ajoutant, modifiant ou supprimant un ou plusieurs membres.</p>
      </div>
      <div class="flex flex-wrap -m-2">
        <TeamMember v-for="(user, memberIndex) in members" :key="memberIndex" :user="user">
          <button class="btn-action" @click="onButtonModalEditClick(user)">
            <i class="fas fa-edit"></i>
          </button>
          <button class="btn-action">
            <i class="fas fa-trash"></i>
          </button>
        </TeamMember>
      </div>
    </div>

    <Modal ref="modal_edit" close-on-backdrop size="6xl" title="Modification du membre">
      <template v-if="user != null">
        <div class="flex flex-col py-2">
          <div class="text-sm leading-3 text-gray-700 font-bold w-full">Nom du membre</div>
          <div class="text-xs text-gray-600 w-full">{{ user.name }}</div>
        </div>
        <div class="flex flex-col py-2">
          <div class="text-sm leading-3 text-gray-700 font-bold w-full">Place dans l'équipe</div>
          <div class="text-xs text-gray-600 w-full">{{ user.job }}</div>
        </div>
      </template>
    </Modal>
  </div>
</template>

<script>
import Modal from '@/components/Modal'
import TeamMember from '@/components/TeamMember'

export default {
  components: { TeamMember, Modal },

  data () {
    return {
      user: null,
      members: [
        { name: 'Henry Letham', job: 'CTO', avatar: 'https://randomuser.me/api/portraits/men/1.jpg' },
        { name: 'Miss Hyun Corwin', job: 'Banking Liaison', avatar: 'https://randomuser.me/api/portraits/women/2.jpg' },
        { name: 'Fredericka Bogisich', job: 'Legal Officer', avatar: 'https://randomuser.me/api/portraits/women/3.jpg' },
        { name: 'Ivory Wolf V', job: 'National Banking Technician', avatar: 'https://randomuser.me/api/portraits/men/4.jpg' },
        { name: 'Kirstin Kub', job: 'International Accounting Administrator', avatar: 'https://randomuser.me/api/portraits/women/5.jpg' },
        { name: 'Grady Braun', job: 'Corporate Engineer', avatar: 'https://randomuser.me/api/portraits/women/6.jpg' },
      ],
    }
  },

  methods: {
    onButtonModalEditClick (user) {
      this.user = user
      this.$refs.modal_edit.show()
    },
  },
}
</script>
