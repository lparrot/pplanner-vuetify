<template>
	<v-container fluid>Profil utilisateur</v-container>
</template>

<script>
import Vue from 'vue'

export default Vue.extend({
  middleware: 'auth',
})
</script>
