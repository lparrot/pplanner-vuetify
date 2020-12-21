<template>
  <v-card v-if="value != null" class="text-center" color="green" dark>
    <v-card-text>
      <v-sheet color="rgba(0, 0, 0, .12)">
        <v-sparkline :value="value" color="rgba(255, 255, 255, .7)" height="100" padding="24" smooth stroke-linecap="round">
          <template v-slot:label="item">
            ${{ item.value }}
          </template>
        </v-sparkline>
      </v-sheet>
    </v-card-text>

    <v-card-text>
      <div class="display-1 font-weight-thin">
        Sales Last 24h
      </div>
    </v-card-text>

    <v-divider></v-divider>

    <v-card-actions class="justify-center">
      <v-btn block text>
        Go to Report
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script lang="ts">
import {Component, Vue} from 'nuxt-property-decorator'
import {stateMixin} from '~/mixins/state.mixin'

function getRandomInt(min: number, max: number): number {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min)) + min;
}

@Component({
  mixins: [stateMixin],
  data() {
    return {
      ...this.$props.module.state,
    }
  },
})
export default class PPAnalytics extends Vue {

  value: any = []

  mounted() {
    this.onInit()
  }

  onInit() {
    if (this.value.length === 0) {
      this.value = []
      for (let i = 0; i < 10; i++) {
        this.value.push(getRandomInt(100, 900))
      }
    }
  }
}
</script>

<style scoped>

</style>
