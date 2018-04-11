<template>
  <section>
    <h1>LeaderBoard</h1>
    <section v-for="player in players" v-bind:key="player">
      {{ player.name }} kills: {{ player.kills }}
    </section>
  </section>
</template>

<script>
export default {
  name: 'LeaderBoard',
  props: {
    players: []
  },
  created: function () {
    this.players = [];
    this.$http.get('./users.json').then(playersResponse => {
      var players = playersResponse.body;

      players.forEach(playerName => {
        this.$http.get('./match-response.json').then(matchResponse => {
          var playerParticipants = matchResponse.body.included.filter(p => {
            return p.type === "participant" && p.attributes.stats.name == playerName;
          });
          
          if (playerParticipants.length == 0) {
            return;
          }
          
          this.players.push({
              name: playerName,
              kills: playerParticipants[0].attributes.stats.kills
          });
        });
      });
    });
  }
}
</script>

<style scoped>
</style>
