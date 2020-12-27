import { Component, Prop, Vue } from "nuxt-property-decorator";

@Component
export class stateMixin extends Vue {
  @Prop(Object) module: any

  async saveState () {
    let state = Object.keys(this.$data).filter(it => !it.startsWith('pr_')).reduce((obj, key) => {
      obj[key] = this.$data[key]
      return obj
    }, {})

    state = JSON.stringify(state)

    await this.$axios.$patch(`/boards/modules/${ this.module.id }`, { state })
  }
}
