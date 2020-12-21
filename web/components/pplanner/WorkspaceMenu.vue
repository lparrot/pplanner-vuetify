<template>
  <v-list v-if="project != null" dense nav>
    <v-menu close-on-content-click offset-x>
      <template #activator="{ attrs, on }">
        <v-list-item v-bind="attrs" v-on="on" link>
          <pp-list-item-avatar>
            <v-icon small>mdi-plus</v-icon>
          </pp-list-item-avatar>
          <v-list-item-content>
            <span>Ajouter ...</span>
          </v-list-item-content>
          <v-list-item-action>
            <v-icon small>mdi-chevron-right</v-icon>
          </v-list-item-action>
        </v-list-item>
      </template>

      <v-list dense nav>
        <v-list-item link @click="onCreateNewBlankBoard">
          <pp-list-item-avatar>
            <v-icon small>mdi-monitor-dashboard</v-icon>
          </pp-list-item-avatar>
          <v-list-item-content>Nouveau tableau vierge</v-list-item-content>
        </v-list-item>
        <v-list-item link>
          <pp-list-item-avatar>
            <v-icon small>mdi-folder-plus-outline</v-icon>
          </pp-list-item-avatar>
          <v-list-item-content>Nouveau dossier</v-list-item-content>
        </v-list-item>
      </v-list>
    </v-menu>

    <v-list-item link to="/project/team">
      <pp-list-item-avatar>
        <v-icon small>mdi-account-group-outline</v-icon>
      </pp-list-item-avatar>
      <v-list-item-content>Equipe</v-list-item-content>
    </v-list-item>

    <v-divider></v-divider>

    <v-list dense nav>
      <v-subheader>Tableaux</v-subheader>
      <template v-if="project.boards.length > 0">
        <pp-board-list-item v-for="(board, boardIndex) in project.boards" :key="boardIndex" :active="boardIndex === selectedBoardIndex" :board="board" @on-select-board="onSelectBoard"></pp-board-list-item>
      </template>
      <template v-else>
        <span>Cliquez sur Ajouter... pour créer un nouveau tableau</span>
      </template>
    </v-list>

    <v-dialog v-model="dialogs.createNewBlankBoard" max-width="600px">
      <validation-observer #default="{invalid}" tag="form" @submit.prevent="onSubmitCreateNewBlankBoard">
        <v-card v-if="dialogs.createNewBlankBoard">
          <v-card-title>Création d'un nouveau tableau vierge</v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <validation-provider #default="{invalid, errors}" name="nom du tableau" rules="required">
                    <v-text-field v-model="forms.createNewBlankBoard.name" :error="invalid" :error-messages="errors[0]" filled label="Nom du tableau *"></v-text-field>
                  </validation-provider>
                </v-col>
                <v-col cols="12">
                  <v-radio-group v-model="forms.createNewBlankBoard.visibility" row>
                    <v-radio label="Privé" value="PRIVATE"></v-radio>
                    <v-radio label="Public" value="PUBLIC"></v-radio>
                  </v-radio-group>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn text @click="dialogs.createNewBlankBoard = false">Annuler</v-btn>
            <v-btn :disabled="invalid" text type="submit">Valider</v-btn>
          </v-card-actions>
        </v-card>
      </validation-observer>
    </v-dialog>
  </v-list>
</template>

<script lang="ts">
import {Component, namespace, Vue, Watch} from 'nuxt-property-decorator'

const projectModule = namespace('project')

@Component({})
export default class WorkspaceMenu extends Vue {

  @projectModule.State selectedProject
  @projectModule.State selectedBoard

  forms: any = {
    createNewBlankBoard: {
      name: null,
      visibility: 'PRIVATE',
    },
  }
  dialogs: any = {
    createNewBlankBoard: false,
  }
  project?: any = null

  get selectedBoardIndex() {
    if (this.selectedProject == null || this.selectedBoard == null) {
      return -1
    }
    return this.project?.boards.findIndex(it => it.id === this.selectedBoard.id)
  }

  async getProject() {
    try {
      const res = await this.$axios.$get(`/api/projects/${this.selectedProject.id}?boards=true`)
      this.project = res.data
    } catch (err) {
      await this.$accessor.project.selectProject(null)
      await this.$accessor.project.selectBoard(null)
    }
  }

  onCreateNewBlankBoard() {
    this.dialogs.createNewBlankBoard = true
    this.forms.createNewBlankBoard = {
      name: null,
      visibility: 'PRIVATE',
    }
    this.onLinkClick()
  }

  onLinkClick() {
    this.$emit('on-link-click')
  }

  async onSelectBoard(board) {
    await this.$accessor.project.selectBoard({id: board.id, name: board.name})
    await this.$router.push(`/project/board`)
    this.onLinkClick()
  }

  async onSubmitCreateNewBlankBoard() {
    const res = await this.$axios.$post(`/api/projects/${this.selectedProject.id}/boards`, this.forms.createNewBlankBoard)
    await this.getProject()
    this.dialogs.createNewBlankBoard = false
    await this.onSelectBoard(res.data)
  }

  @Watch('selectedProject', {immediate: true})
  async onSelectProjectChange() {
    await this.getProject()
  }
}
</script>
