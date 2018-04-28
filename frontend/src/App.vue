<template>
  <section class="container">
    <router-view />
  </section>
</template>

<script>
export default {
    name: 'app'
}
</script>

<style lang="sass">
  @import url('https://fonts.googleapis.com/css?family=Open+Sans:400,700')

  body
    display: flex
    flex-direction: column
    align-items: center
    font-weight: 400
    font-family: 'Open Sans', sans-serif
    margin: 0
    background-color: rgba(245, 245, 245, 0.6)

  .container
    width: 500px
    padding: 0 10px

  @media screen and (max-width: 500px)
    .container
      width: calc(100% - 20px)
</style>
