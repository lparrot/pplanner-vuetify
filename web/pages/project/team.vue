<template>
  <div>
    <div class="container px-5 py-24 mx-auto">
      <div class="flex flex-col text-center w-full mb-20">
        <h1 class="sm:text-3xl text-2xl font-medium title-font mb-4 text-gray-900">Equipe du projet {{ selectedProject.name }}</h1>
        <p class="lg:w-2/3 mx-auto leading-relaxed text-base">Ici vous pourrez gérer votre équipe en ajoutant, modifiant ou supprimant un ou plusieurs membres.</p>
      </div>
      <div class="flex flex-wrap -m-2">
        <TeamMember v-for="(member, memberIndex) in memberList" :key="memberIndex" :member="member">
          <button class="btn-action" @click="onButtonModalEditClick(member)">
            <i class="fas fa-pen"></i>
          </button>
          <button class="btn-action">
            <i class="fas fa-trash"></i>
          </button>
        </TeamMember>
      </div>
    </div>

    <Modal ref="modal_edit" close-on-backdrop size="6xl" title="Modification du membre">
      <template v-if="member != null">
        <div class="flex flex-col py-2">
          <div class="text-sm leading-3 text-gray-700 font-bold w-full">Nom du membre</div>
          <div class="text-xs text-gray-600 w-full">{{ member.fullname }}</div>
        </div>
        <div class="flex flex-col py-2">
          <div class="text-sm leading-3 text-gray-700 font-bold w-full">Place dans l'équipe</div>
          <div class="text-xs text-gray-600 w-full">{{ member.job }}</div>
        </div>
      </template>
    </Modal>
  </div>
</template>

<script>
import Modal from '@/components/Modal'
import TeamMember from '@/components/TeamMember'
import { mapState } from 'vuex'

export default {
  components: { TeamMember, Modal },

  data () {
    return {
      member: null,
    }
  },

  computed: {
    ...mapState({
      selectedProject: state => state.project.selectedProject,
      memberList: state => state.project.memberList,
    }),
  },

  async asyncData (ctx) {
    await ctx.store.dispatch('project/findTeam')
  },

  methods: {
    onButtonModalEditClick (member) {
      this.member = member
      this.$refs.modal_edit.show()
    },
  },
}
</script>
