<template>
  <v-container class="fill-height">
    <div>
      <v-row align="center" class="d-flex flex-column mb-10" dense justify="center">
        <div class="text-h5 text-md-h2 text-justify mb-4">PPlanner - Project Planner</div>
        <div class="text-subtitle-2 text-md-subtitle-1 text-justify">Outil de gestion de projet, pour le pilotage des systèmes, les responsables de réalisation de projet ainsi que les développeurs.</div>
      </v-row>
      <v-row dense justify="center">
        <v-col v-for="(item, itemIndex) in checkItems" :key="itemIndex" class="d-flex flex-row align-self-stretch" cols="12" md="6">
          <v-hover v-slot="{ hover }">
            <v-alert :icon="item.icon" class="check-item w-full" dark text>
              <div>{{ item.label }}</div>
            </v-alert>
          </v-hover>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script>
import Vue from 'vue'

export default Vue.extend({
  data () {
    return {
      checkItems: [
        { label: `Création de backlog et chiffrage du projet`, icon: 'mdi-checkbox-marked-circle-outline' },
        { label: `Création de feuille de route globale et détaillée`, icon: 'mdi-checkbox-marked-circle-outline' },
        { label: `Suivi des itérations`, icon: 'mdi-checkbox-marked-circle-outline' },
        { label: `Gestion des fichiers liés aux projets`, icon: 'mdi-checkbox-marked-circle-outline' },
        { label: `Echanges entre la maitrise d'oeuvre et la maitrise d'ouvrage`, icon: 'mdi-checkbox-marked-circle-outline' },
        { label: `Restitutions graphiques et génération de rapports`, icon: 'mdi-checkbox-marked-circle-outline' },
      ],
    }
  },

  fetch (ctx) {
    if (ctx.$auth.loggedIn && ctx.$accessor.project.selectedProject != null) {
      ctx.redirect('/project/dashboard')
    }
  },
})
</script>

<style>
.check-item {
  transition: ease-in-out 0.1s;
}

.check-item:hover {
  transform-origin: center;
  transform: scale(1.02);
  z-index: 200;
  outline: none;
  border-color: #9ecaed !important;
  box-shadow: 0 0 10px #9ecaed !important;
}
</style>
