import Vue from 'vue'
import Button from '../components/Button'
import Modal from '../components/Modal'

export class ModalProvider {

  constructor () {
    this.instances = []
  }

  message (message, title = null, closeOnBackdrop = true) {
    const instance = this._createModalWithMessage(message, title, closeOnBackdrop)
    instance.show()
  }

  confirm (message, title, buttons, closeOnBackdrop = true) {
    const instance = this._createModalWithMessage(message, title, closeOnBackdrop, (instance) => {

      const containerNode = instance.$createElement('div', { class: { 'flex justify-end': true } })

      containerNode.children = buttons.map(button => {
        return instance.$createElement(
          Button,
          {
            class: { 'ml-1': true },
            on: {
              click: button.action != null ? () => button.action(instance) : '',
            },
          },
          button.label,
        )
      })

      instance.$slots.footer = [containerNode]
    })
    instance.show()
  }

  _init () {
    this.modalContainer = document.createElement('div')
    document.body.appendChild(this.modalContainer)

    const ComponentClass = Vue.extend(Modal)
    const instance = new ComponentClass()

    instance.index = this.instances.length
    this.instances.push(instance)
    return instance
  }

  _createModalWithMessage (message, title, closeOnBackdrop, handler) {
    const instance = this._init()

    // Ajout du titre
    if (title != null) {
      instance.title = title
    }

    // Défini si la modal peut être fermée lors du clic sur le backdrop
    instance.closeOnBackdrop = closeOnBackdrop

    instance.$on('on-hide', () => {
      this.instances = this.instances.filter(i => i.index !== instance.index)
      instance.$destroy()
      instance.$el.remove()
    })

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

    if (handler != null) {
      handler(instance)
    }

    this._mountModal(instance)

    return instance
  }

  _mountModal (instance) {
    instance.$mount(this.modalContainer)
  }
}
