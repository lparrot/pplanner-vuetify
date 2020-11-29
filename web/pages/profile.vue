<template>
  <section>
    <div>Profil de l'utilisateur {{ $auth.user.fullname }}</div>
  </section>
</template>

<script>
export default {
  middleware: 'auth',
}
</script>

<style scoped>

</style>
