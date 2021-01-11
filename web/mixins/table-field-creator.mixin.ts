import {Component, PropSync, Vue} from "nuxt-property-decorator";

@Component
export class tableFieldCreatorMixin extends Vue {
  @PropSync('item') itemSync !: any

  created() {
    this.$set(this.itemSync, 'props', this.itemSync.props || {step: 1})
  }
}
