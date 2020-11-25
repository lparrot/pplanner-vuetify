import { ModalProvider } from '@/providers/modal'
import gravatar from 'gravatar'

export default async (ctx, inject) => {
  inject('modal', new ModalProvider(ctx.app))
  inject('gravatar', gravatar)
}
