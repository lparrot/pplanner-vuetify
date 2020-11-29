import { ModalProvider } from '@/providers/modal'
import { ToastProvider } from '@/providers/toast'

export default async (ctx, inject) => {
  inject('modal', new ModalProvider(ctx.app))
  inject('toast', new ToastProvider(ctx.app))
}
