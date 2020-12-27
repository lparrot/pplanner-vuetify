import { Component, PropSync, Vue } from "nuxt-property-decorator";

@Component
export class tableFieldCreatorMixin extends Vue {
  @PropSync('item') itemSync !: any
}
