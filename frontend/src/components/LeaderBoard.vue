<template>
  <section>
    <h1>LeaderBoard</h1>
    <section class="players">
      <Player v-for="playerName in playerNames" v-bind:key="playerName" v-bind:name="playerName"/>
    </section>
  </section>
</template>

<script>
import Player from './Player'
import PlayerNames from '../assets/data/users.json'

export default {
  name: 'LeaderBoard',
  components: {
    Player
  },
  data: function () {
    return {
      playerNames: PlayerNames
    }
  },
}
</script>

<style scoped>
  h1 {
    font-weight: 700;
    font-size: 3rem;
    text-transform: uppercase;
    text-align: center;
  }
  .players {
    display: flex;
    flex-direction: row;
  }
</style>
