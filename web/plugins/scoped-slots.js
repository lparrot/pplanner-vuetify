import Vue from 'vue'

export default () => {
  // Ce morceau de code permet de 'mapper' tous les slots nommés d'un composant parent vers un composant enfant automatiquement.
  // Pour l'utiliser il suffit d'ajouter v-bind="{$scopedSlots}" au niveau de l'appel du composant enfant
  Vue.prototype._b = (function (bind) {
    return function (data, tag, value, asProp, isSync) {
      if (value && value.$scopedSlots) {
        data.scopedSlots = {
          ...data.scopedSlots,
          ...value.$scopedSlots,
        }
        delete value.$scopedSlots
      }
      return bind.apply(this, arguments)
    }
  })(Vue.prototype._b)
}
