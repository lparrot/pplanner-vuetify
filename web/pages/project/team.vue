<template>
  <v-container fluid>
    <v-toolbar class="mb-4" dense flat>
      <v-btn color="green" text>
        <v-icon>mdi-plus</v-icon>
        <span>Ajouter un membre</span>
      </v-btn>
    </v-toolbar>

    <v-row dense>
      <v-col v-for="(member, memberIndex) in memberList" :key="memberIndex" class="d-flex flex-row align-self-stretch" cols="12" lg="3">
        <v-card class="w-full" color="grey lighten-4" link @click="handleBeforeShowDialogInfoTeam(member)">
          <v-card-actions>
            <v-list-item>
              <v-list-item-avatar color="grey darken-3">
                <v-avatar class="elevation-6" size="48">
                  <img :src="member.avatar" alt="avatar">
                </v-avatar>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title>{{ member.fullname }}</v-list-item-title>
                <v-list-item-subtitle>{{ member.job }}</v-list-item-subtitle>
              </v-list-item-content>

              <v-list-item-action>
                <v-btn icon>
                  <v-icon class="mx-1 red--text text--lighten-1">mdi-trash-can</v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

    <v-dialog v-model="dialogInfoTeam">
      <v-card>
        <v-card-title>Informations sur {{ selectedMember.fullname }}</v-card-title>
        <v-card-text style="max-height: 70vh; overflow-y: auto">
          <pp-profile-card :member="selectedMember"></pp-profile-card>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="dialogInfoTeam = false">Fermer</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script lang="ts">
import { Component, namespace, Vue } from 'nuxt-property-decorator'

const projectModule = namespace('project')

@Component({
  middleware: 'project',
})
export default class PageProjectEquipe extends Vue {

  @projectModule.State selectedProject?: Project;
  @projectModule.State memberList?: Array<Member>;

  selectedMember?: Member | null = null
  dialogInfoTeam: boolean = false

  async fetch (ctx) {
    await ctx.store.dispatch('project/findTeam')
  }

  async mounted () {
    await this.$root.$on('project-selected', async () => {
      await this.$nuxt.refresh()
    })
  }

  async handleBeforeShowDialogInfoTeam (member) {
    this.selectedMember = member
    this.dialogInfoTeam = true
  }
}
</script>
