import { Component, Prop, PropSync, Vue } from "nuxt-property-decorator";

@Component
export class tableColumnMixin extends Vue {
  @PropSync('item') itemSync !: any
  @Prop({ required: true }) field!: any
}
