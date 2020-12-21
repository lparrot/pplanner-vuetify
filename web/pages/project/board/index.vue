<template>
  <v-container v-if="board != null" fluid>
    <v-card dense flat>
      <v-card-text>
        <!-- Nom -->
        <v-layout align-content-center>
          <v-edit-dialog :return-value.sync="board.visibility" cancel-text="Annuler" large save-text="Valider" @save="onSave">
            <pp-visibility-icon :visibility="board.visibility" class="mr-2"></pp-visibility-icon>
            <template v-slot:input>
              <v-radio-group v-model="board.visibility" row>
                <v-radio label="Privé" value="PRIVATE"></v-radio>
                <v-radio label="Public" value="PUBLIC"></v-radio>
              </v-radio-group>
            </template>
          </v-edit-dialog>
          <v-edit-dialog :return-value.sync="board.name" cancel-text="Annuler" large save-text="Valider" @save="onSave">
            <div class="text-h4" v-text="board.name"></div>
            <template v-slot:input>
              <v-text-field v-model="board.name" :rules="[required]" autofocus label="Nom" single-line></v-text-field>
            </template>
          </v-edit-dialog>

          <div class="ml-5">
            <v-tooltip bottom>
              <template #activator="{ on, attrs }">
                <v-btn v-bind="attrs" v-on="on" color="red" dark depressed fab x-small>
                  <v-icon dark>mdi-trash-can-outline</v-icon>
                </v-btn>
              </template>
              <span>Supprimer le tableau</span>
            </v-tooltip>
          </div>
        </v-layout>

        <!-- Description -->
        <v-layout align-content-center>
          <v-edit-dialog :return-value.sync="board.description" cancel-text="Annuler" large save-text="Valider" @save="onSave">
            <p class="mt-2 subtitle-1" v-text="board.description != null ? board.description : 'Cliquez ici pour ajouter une description à votre tableau'"></p>
            <template v-slot:input>
              <v-textarea v-model="board.description" :rules="[required]" autofocus class="ma-4" label="Description"></v-textarea>
            </template>
          </v-edit-dialog>
        </v-layout>

        <!-- Options -->
        <v-btn color="green" text @click="onClickAddModule">Ajouter un module</v-btn>

        <!-- Liste des modules -->
        <template v-if="boardModules != null">
          <v-row v-for="(boardModule, boardModuleIndex) in boardModules" :key="boardModuleIndex" class="d-flex justify-center">
            <v-col cols="12" md="10">
              <component :is="boardModule.type" :module="boardModule"></component>
            </v-col>
          </v-row>
        </template>

      </v-card-text>
    </v-card>

    <v-dialog v-model="dialogs.addModule" :width="$vuetify.breakpoint.mdAndDown ? '100vw': '60vw'" scrollable>
      <v-card>
        <v-card-title class="pa-0">
          <v-toolbar flat>
            <v-toolbar-title>Ajout d'un nouveau module au tableau</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn icon text>
              <v-icon class="heading grey--text text--darken-4" @click="dialogs.addModule = false">mdi-close</v-icon>
            </v-btn>
          </v-toolbar>
        </v-card-title>

        <v-card-text>
          <p class="subtitle-2">Selectionnez un module ci-dessous</p>
          <v-item-group v-model="selectedModuleToAdd">
            <v-container>
              <v-row>
                <v-col v-for="(module, moduleIndex) in modules" :key="moduleIndex" cols="12" lg="3" md="4">
                  <v-item #default="{ active, toggle }">
                    <v-card class="pt-1" @click="toggle">
                      <v-img :aspect-ratio="16/9" :src="module.img" contain>
                        <v-expand-transition>
                          <div v-if="active" class="d-flex transition-fast-in-fast-out blue lighten-2 v-card--reveal display-3 white--text fill-height">
                            <v-layout align-center justify-center>
                              <div>Sélectionné</div>
                            </v-layout>
                          </div>
                        </v-expand-transition>
                      </v-img>
                      <v-card-text>
                        <div class="font-weight-light grey--text title mb-2 text-justify">
                          {{ module.label }}
                          <p class="subtitle-2">{{ module.description }}</p>
                        </div>
                      </v-card-text>
                    </v-card>
                  </v-item>
                </v-col>
              </v-row>
            </v-container>
          </v-item-group>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red" text @click="dialogs.addModule = false">Annuler</v-btn>
          <v-btn color="green" text @click="onSubmitAddModule">Valider</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script lang="ts">
import {Component, namespace, Vue, Watch} from 'nuxt-property-decorator'

const projectModule = namespace('project')

@Component
export default class PageProjectBoardIndex extends Vue {

  @projectModule.State selectedBoard

  board?: any = null
  boardEdit: any = {}
  boardModules?: any[] | null = null
  dialogs: any = {
    addModule: false,
  }
  modules: BoardModule[] = [
    {type: 'pp-analytics', img: '/modules/analytics.png', label: 'Analytique', description: `Permet de réaliser des diagrammes d'analyse de données`},
    {type: 'pp-table', img: '/modules/table.png', label: 'Liste dynamique', description: `Permet de réaliser des listes avec des champs dynamiques`},
    {type: 'pp-chat', img: '/modules/chat.png', label: 'Conversation', description: `Permet de réaliser des conversations entre membres du projet`},
    {type: 'pp-kanban', img: '/modules/kanban.png', label: 'Kanban', description: `Permet de réaliser des tableaux de type Kanban`},
    {type: 'pp-org-chart', img: '/modules/org_chart.png', label: 'Organigrammes', description: `Permet de réaliser des organigrammes`},
    {type: 'pp-task-list', img: '/modules/tasklist.png', label: 'Liste de tâches', description: `Permet de réaliser des listes de tâches`},
  ]
  selectedModuleToAdd?: any = null

  required: Function = v => v != null || 'Champ requis'

  async onClickAddModule() {
    this.selectedModuleToAdd = null
    this.dialogs.addModule = true
  }

  async onSave() {
    const {name, description, visibility} = this.board
    await this.$axios.$patch(`/api/boards/${this.board.id}`, {name, description, visibility})
    // TODO : Rafraichir la liste des tableaux
  }

  async onSubmitAddModule() {
    const module = this.modules[this.selectedModuleToAdd]
    if (module != null && this.board != null) {
      const res = await this.$axios.$post(`/api/boards/${this.board.id}/modules`, {type: module.type})
      this.boardModules?.push(res.data)
      this.dialogs.addModule = false
    }
  }

  @Watch('selectedBoard', {immediate: true})
  async onSelectedBoardChange(selectedBoard) {
    try {
      this.$nextTick(async () => {
        this.$nuxt.$loading.start()
      })
      const [resBoard, resModules] = await Promise.all([
        this.$axios.$get(`/api/boards/${selectedBoard.id}`),
        this.$axios.$get(`/api/boards/${selectedBoard.id}/modules`),
      ])
      this.board = resBoard.data
      this.boardModules = resModules.data.map(it => {
        if (it.state != null) {
          it.state = JSON.parse(it.state)
        }
        return it
      })
    } catch (err) {
      console.error(err)
      await this.$accessor.project.selectBoard(null)
      await this.$router.push('/project/dashboard')
    } finally {
      this.$nuxt.$loading.finish()
    }
  }
}
</script>
