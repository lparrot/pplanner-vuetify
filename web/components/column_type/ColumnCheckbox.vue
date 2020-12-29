<template>
  <v-simple-checkbox v-model="itemSync" :ripple="false"></v-simple-checkbox>
</template>

<script lang="ts">
import {Component, mixins} from "nuxt-property-decorator";
import {tableColumnMixin} from "~/mixins/table-column.mixin";

@Component
export default class PpColumnCheckbox extends mixins(tableColumnMixin) {

}
</script>

<style scoped>

</style>
