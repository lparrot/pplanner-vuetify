import Toast from '@/components/Toast'
import Vue from 'vue'

export class ToastProvider {

  constructor () {
    this.instances = []
  }

  show (message, options) {
    const instance = this._init()

    instance.$on('on-hide', () => {
      this.instances = this.instances.filter(i => i.index !== instance.index)
      instance.$destroy()
      instance.$el.remove()
    })

    if (options.timeout != null) {
      instance.timeout = options.timeout
    }

    if (options.variant != null) {
      instance.variant = options.variant
    }

    // Création du message dans le slot
    const ContentNode = Vue.extend({
      render (h, context) {
        return h(
          'p',
          { domProps: { innerHTML: message } },
        )
      },
    })

    instance.$slots.default = new ContentNode().$mount()._vnode

    instance.$mount(this.container)

    instance.show()
  }

  _init () {
    this.container = document.createElement('div')
    let toastContainer = document.getElementById('toast-container')
    toastContainer.appendChild(this.container)

    const ComponentClass = Vue.extend(Toast)
    const instance = new ComponentClass()

    instance.index = this.instances.length
    this.instances.push(instance)
    return instance
  }

}
