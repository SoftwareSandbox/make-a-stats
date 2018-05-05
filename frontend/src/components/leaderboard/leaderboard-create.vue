<template>
    <form @submit="validateForm($event) && createLeaderboard()">
        <h2>Leaderboard</h2>
        <base-input v-model="name" is-required placeholder="Name" ref="name" default-error-message="Name should not be empty"/>
        <h2>Players</h2>
        <base-input-list :list="playerNames" is-required placeholder="Player name" ref="playerNames" default-error-message="Player name should not be empty"/>
        <base-button is-primary name="Create Leaderboard"/>
    </form>
</template>

<script>
export default {
  name: 'leaderboard-create',
  data() {
    return {
      name: '',
      playerNames: ['']
    }
  },
  methods: {
    validateForm(event) {
      this.$refs.name.validate(event);
      this.$refs.playerNames.validate(event);
      return !event.defaultPrevented;
    },
    createLeaderboard() {
      this.$http.post(`leaderboard`, {
        name: this.name.trim(),
        playerNames: this.playerNames.map(name => name.trim())
      }).then(response => {
        const location = response.headers.get('location');
        const leaderboardPathIndex = location.indexOf("/leaderboard/") + 1;
        const leaderboardPath = location.substr(leaderboardPathIndex, location.length);
        this.$router.push(leaderboardPath);
      });
    }
  }
}
</script>

<style lang="sass" scoped>
form
  display: flex
  flex-direction: column

  > h2
    color: #303030
    font-weight: 700
    font-size: 0.7rem
    text-transform: uppercase
</style>
