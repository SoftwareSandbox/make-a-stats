<template>
    <section class="title-bar">
        <h1>Make a Stats</h1>
        <section class="navigation">
            <router-link to="leaderboard">
              New Leaderboard
            </router-link>
        </section>
    </section>
</template>

<script>
    export default {
        name: "the-navigation"
    }
</script>

<style lang="sass" scoped>
.title-bar
  flex: none
  display: flex
  flex-direction: row
  padding: 10px 0
  border-bottom: 1px solid hsla(0, 0%, 0%, 0.2)

  > h1
    flex: 1
    font-size: 1rem
    font-weight: 400
    margin: 10px

  > .navigation
    display: flex
    flex-direction: row
    flex: none
    align-items: center
    padding: 0 5px

    > a
      font-size: 0.65rem
      font-weight: 400
      text-transform: uppercase
      text-decoration: none
      padding: 5px
      border-radius: 2px

      &.active
        background-color: rgba(240, 240, 240, 0.5)

        &:hover
          background-color: rgb(240, 240, 240)
          
      &:hover
        background-color: rgba(240, 240, 240, 0.5)
</style>