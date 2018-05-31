<template>
  <section class="app">
    <the-navigation/>
    <section class="container">
      <router-view/>
    </section>
  </section>
</template>

<script>
import TheNavigation from "./components/navigation/the-navigation";

export default {
    components: {TheNavigation},
    name: 'app'
}
</script>

<style lang="sass">
@import url('https://fonts.googleapis.com/css?family=Open+Sans:400,700')
@import url('https://fonts.googleapis.com/css?family=Montserrat:300,400,700')

html, body
  display: flex
  flex-direction: column
  align-items: center
  color: rgba(66, 66, 66, 1)
  font-weight: 400
  font-family: 'Montserrat', sans-serif
  margin: 0
  width: 100%
  height: 100%
  background-color: rgba(255, 255, 255, 0.8)

.app
  flex: 1
  display: flex
  flex-direction: column
  width: 1080px

  .container
    flex: 1

  @media screen and (max-width: 1080px)
    width: 100%
</style>