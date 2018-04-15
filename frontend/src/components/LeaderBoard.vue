<template>
  <section class="leaderboard">
    <h1>LeaderBoard</h1>
    <section class="players">
      <Player v-for="player in playersSortedByKills" v-bind:key="player.playerId" v-bind:player="player"/>
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
  computed: {
      playersSortedByKills: function () {
        let players = this.players;
        return players.sort((p1, p2) => p1.kills < p2.kills);
      }
  },
  created: function () {
    this.$http.get('api/stats/leaderboard')
      .then(response => this.players = response.body);
  }
}
</script>

<style scoped>
  .leaderboard {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  h1 {
    color: #303030;
    font-weight: 700;
    font-size: 2.5rem;
    text-transform: uppercase;
    text-align: center;
  }
  .players {
    display: flex;
    flex-direction: column;
    width: 400px;
  }
  @media screen and (max-width: 400px) {
    .players {
      width: 100%;
    }
  }
</style>
