<template>
  <v-container v-if="board != null" fluid>
    <v-card dense flat>
      <v-card-text>
        <!-- Nom -->
        <v-layout align-content-center>
          <v-edit-dialog :return-value.sync="board.visibility" cancel-text="Annuler" large save-text="Valider" @save="handleSaveBoard">
            <pp-visibility-icon :visibility="board.visibility" class="mr-2"></pp-visibility-icon>
            <template v-slot:input>
              <v-radio-group v-model="board.visibility" row>
                <v-radio label="Privé" value="PRIVATE"></v-radio>
                <v-radio label="Public" value="PUBLIC"></v-radio>
              </v-radio-group>
            </template>
          </v-edit-dialog>
          <v-edit-dialog :return-value.sync="board.name" cancel-text="Annuler" large save-text="Valider" @save="handleSaveBoard">
            <div class="text-h4" v-text="board.name"></div>
            <template v-slot:input>
              <v-text-field v-model.trim="board.name" :rules="[required]" autofocus label="Nom" single-line></v-text-field>
            </template>
          </v-edit-dialog>

          <div class="ml-5">
            <v-btn color="red" dark depressed icon small text @click="handleDeleteBoard(board)">
              <v-icon dark>mdi-trash-can-outline</v-icon>
            </v-btn>
          </div>
        </v-layout>

        <!-- Description -->
        <v-layout align-content-center>
          <v-edit-dialog :return-value.sync="board.description" cancel-text="Annuler" large save-text="Valider" @save="handleSaveBoard">
            <p class="mt-2 subtitle-1" v-text="board.description != null ? board.description : 'Cliquez ici pour ajouter une description à votre tableau'"></p>
            <template v-slot:input>
              <v-textarea v-model="board.description" :rules="[required]" autofocus class="ma-4" label="Description"></v-textarea>
            </template>
          </v-edit-dialog>
        </v-layout>

        <!-- Options -->
        <v-btn color="green" text @click="handleShowDialogNewModule">Ajouter un module</v-btn>

        <!-- Liste des modules -->
        <template v-if="boardModules != null">
          <v-row v-for="(boardModule, boardModuleIndex) in boardModules" :key="boardModuleIndex" class="d-flex justify-center">
            <v-col cols="12" md="10">
              <v-layout align-content-center>
                <p class="text-h4">{{ boardModule.name }}</p>
                <div class="ml-5">
                  <v-btn color="red" dark depressed icon small text @click="handleDeleteModule(boardModule)">
                    <v-icon dark>mdi-trash-can-outline</v-icon>
                  </v-btn>
                </div>
              </v-layout>
              <component :is="boardModule.type" :module="boardModule"></component>
            </v-col>
          </v-row>
        </template>

      </v-card-text>
    </v-card>

    <v-dialog v-model="dialogs.addModule" :width="$vuetify.breakpoint.mdAndDown ? '100vw': '60vw'">
      <validation-observer #default="{invalid}">
        <v-card tile>
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
            <validation-provider #default="{invalid, errors}" name="nom du module" rules="required">
              <v-text-field v-model.trim="selectedModuleToAdd.name" :error="invalid" :error-messages="errors[0]" label="Nom du module"></v-text-field>
            </validation-provider>

            <validation-provider #default="{invalid, errors}" name="module" rules="required">
              <input :value="selectedModuleToAdd.module" type="hidden">
            </validation-provider>
            <p class="subtitle-2">Selectionnez un module ci-dessous</p>
          </v-card-text>

          <v-card-text style="height: 50vh; overflow-y: auto">
            <v-item-group v-model="selectedModuleToAdd.module">
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
            </v-item-group>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="red" text @click="dialogs.addModule = false">Annuler</v-btn>
            <v-btn :disabled="invalid" color="green" text @click="handleSubmitNewModule">Valider</v-btn>
          </v-card-actions>
        </v-card>
      </validation-observer>
    </v-dialog>
  </v-container>
</template>

<script lang="ts">
import { Component, namespace, Vue, Watch } from 'nuxt-property-decorator'

const projectModule = namespace('project')

interface BoardModuleSelect {
  type: string
  img: string
  label: string
  description: string
}

@Component
export default class PageProjectBoardIndex extends Vue {

  @projectModule.State selectedBoard

  board?: any = null
  boardEdit: any = {}
  boardModules?: BoardModule[] | null = null
  dialogs: any = {
    addModule: false,
  }
  modules: BoardModuleSelect[] = [
    {
      type: 'pp-analytics',
      img: '/modules/analytics.png',
      label: 'Analytique',
      description: `Permet de réaliser des diagrammes d'analyse de données`
    },
    {
      type: 'pp-table',
      img: '/modules/table.png',
      label: 'Liste dynamique',
      description: `Permet de réaliser des listes avec des champs dynamiques`
    },
    {
      type: 'pp-chat',
      img: '/modules/chat.png',
      label: 'Conversation',
      description: `Permet de réaliser des conversations entre membres du projet`
    },
    {
      type: 'pp-kanban',
      img: '/modules/kanban.png',
      label: 'Kanban',
      description: `Permet de réaliser des tableaux de type Kanban`
    },
    {
      type: 'pp-org-chart',
      img: '/modules/org_chart.png',
      label: 'Organigrammes',
      description: `Permet de réaliser des organigrammes`
    },
    {
      type: 'pp-task-list',
      img: '/modules/tasklist.png',
      label: 'Liste de tâches',
      description: `Permet de réaliser des listes de tâches`
    },
  ]
  selectedModuleToAdd?: any = null

  required: Function = v => v != null || 'Champ requis'

  async handleDeleteBoard (board) {
    const res = await this.$axios.$delete(`/boards/${ board.id }`)
    if (res.success) {
      await this.$accessor.project.selectBoard(null)
      this.$eventBus.$emit('update-boards')
      // TODO : Notification 'Vue supprimée'
    }
  }

  async handleDeleteModule (module) {
    const res = await this.$axios.$delete(`/boards/modules/${ module.id }`)
    if (res.success) {
      this.boardModules = this.boardModules?.filter((it: any) => it.id !== module.id)
      // TODO : Notification 'Module supprimé'
    }
  }

  async handleShowDialogNewModule () {
    this.selectedModuleToAdd = {
      name: null,
      module: null,
    }
    this.dialogs.addModule = true
  }

  async handleSaveBoard () {
    const {
      name,
      description,
      visibility
    } = this.board
    await this.$axios.$patch(`/boards/${ this.board?.id }`, {
      name,
      description,
      visibility
    })
    this.$eventBus.$emit('update-boards')
  }

  async handleSubmitNewModule () {
    const module = this.modules[this.selectedModuleToAdd.module]
    if (module != null && this.board != null) {
      const res = await this.$axios.$post(`/boards/${ this.board?.id }/modules`, {
        type: module.type,
        name: this.selectedModuleToAdd.name
      })
      this.boardModules?.push(res.data)
      this.dialogs.addModule = false
    }
  }

  @Watch('selectedBoard', { immediate: true })
  async handleSelectedBoardChange (selectedBoard) {
    try {
      this.$nextTick(async () => {
        this.$nuxt.$loading.start()
      })
      const [ resBoard, resModules ] = await Promise.all([
        this.$axios.$get(`/boards/${ selectedBoard.id }`),
        this.$axios.$get(`/boards/${ selectedBoard.id }/modules`),
      ])
      this.board = resBoard.data
      this.boardModules = resModules.data.map(it => {
        if (it.state != null) {
          it.state = JSON.parse(it.state)
        }
        return it
      })
    } catch (err) {
      await this.$accessor.project.selectBoard(null)
      await this.$router.push('/project/dashboard')
    } finally {
      this.$nuxt.$loading.finish()
    }
  }
}
</script>
