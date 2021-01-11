<template>
  <v-card flat>
    <v-card-text>
      <slot :add-item="handleAddItem" :item="item">
        <v-text-field v-model="item" filled type="text">
          <template #append-outer>
            <v-btn color="green" text @click="handleAddItem">Ajouter</v-btn>
          </template>
        </v-text-field>
      </slot>

      <v-divider class="my-4"></v-divider>

      <template v-if="model != null && model.length > 0">
        <div v-for="(item, itemIndex) in model" :key="itemIndex">
          <slot :item="item" name="item">{{ item }}</slot>
        </div>
      </template>

      <template v-else>Il n'y a aucun élément</template>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
import {Component, Prop, Ref, Vue} from 'nuxt-property-decorator'
import {VModel} from 'vue-property-decorator'

@Component
export default class ItemCollector extends Vue {
  @Ref('validator') validator!: any
  @Prop() addBefore!: boolean
  @Prop({type: Function}) beforeCreate: Function
  @VModel() model: any[]

  public item: any = '';

  created() {
    // Initialisation du composant
    this.model = this.model ?? []

    if (this.beforeCreate != null) {
      this.beforeCreate(this)
    }
  }

  handleAddItem() {
    if (this.item != null && this.item !== '') {
      this.model[this.addBefore ? 'unshift' : 'push'](this.item)
    }
  }
}
</script>

<style scoped>

</style>
