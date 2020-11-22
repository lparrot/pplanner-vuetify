import { ModalProvider } from '@/providers/modal'

export default async (ctx, inject) => {
  inject('modal', new ModalProvider(ctx.app))
}
