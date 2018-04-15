<template>
  <section>
    <h1>LeaderBoard</h1>
    <section class="players">
      <Player v-for="player in players" v-bind:key="player.playerId" v-bind:player="player"/>
    </section>
  </section>
</template>

<script>
import Player from './Player'

export default {
  name: 'LeaderBoard',
  components: {
    Player
  },
  data: function () {
    return {
      players: []
    }
  },
  created: function () {
    this.$http.get('api/stats/leaderboard')
      .then(response => this.players = response.body);
  }
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
