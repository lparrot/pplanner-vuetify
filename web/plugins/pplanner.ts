import Vue from 'vue'

export let EventBus: Vue

export default async (ctx, inject) => {
  const service = {}

  EventBus = new Vue()

  inject('pplanner', service)
  inject('eventBus', EventBus)
}
