import Vue from "vue";

export const stateMixin = Vue.extend({
  props: {
    module: {
      type: Object,
    },
  },

  watch: {
    $data: {
      handler: async function (val) {
        let state = Object.keys(val).filter(it => !it.startsWith('pr_')).reduce((obj, key) => {
          obj[key] = val[key];
          return obj;
        }, {});

        state = JSON.stringify(state)

        await this.$axios.$patch(`/api/boards/modules/${this.module.id}`, {state})
      },
      deep: true,
    },
  },
})
