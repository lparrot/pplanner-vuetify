<template>
  <div>
    <div class="x-5 py-24">
      <div class="text-center mb-20">
        <h1 class="sm:text-3xl text-2xl font-medium text-center title-font text-gray-900 mb-4">PPlanner - Project Planner</h1>
        <p class="text-base leading-relaxed xl:w-2/4 lg:w-3/4 mx-auto">Outil de gestion de projet, pour le pilotage des systèmes, les responsables de réalisation de projet ainsi que les développeurs.</p>
      </div>
      <div class="flex flex-wrap lg:w-4/5 sm:mx-auto sm:mb-2 -mx-2">
        <div v-for="(item, itemIndex) in checkItems" :class="item.action ? 'cursor-pointer' : ''" class="p-2 sm:w-1/2 w-full" @click="item.action ? item.action(itemIndex) : null">
          <div class="bg-gray-200 rounded flex p-4 h-full items-center">
            <div class="text-primary w-8 h-8 flex-shrink-0 mr-4">
              <i :class="item.icon" class="text-2xl"></i>
            </div>
            <span class="title-font font-medium">{{ item.label }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Button from '@/components/Button'
import Modal from '@/components/Modal'

export default {
  components: { Modal, Button },

  auth: false,

  async asyncData (ctx) {
    if (ctx.$auth.loggedIn && ctx.store.state.project.selectedProject != null) {
      console.log('redirect')
      ctx.redirect('/dashboard')
    }
  },

  data () {
    return {
      checkItems: [
        { label: 'Création de backlog et chiffrage du projet', icon: 'far fa-check-circle' },
        { label: 'Création de feuille de route globale et détaillée', icon: 'far fa-check-circle' },
        { label: 'Suivi des itérations', icon: 'far fa-check-circle' },
        { label: 'Gestion des fichiers liés aux projets', icon: 'far fa-check-circle' },
        { label: 'Echanges entre la maitrise d\'oeuvre et la maitrise d\'ouvrage', icon: 'far fa-check-circle' },
        { label: 'Restitutions graphiques et génération de rapports', icon: 'far fa-check-circle' },
      ],
    }
  },
}
</script>
