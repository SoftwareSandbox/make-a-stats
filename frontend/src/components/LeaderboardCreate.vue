<template>
    <form @submit="validateForm($event) && createLeaderboard()">
        <h2>Leaderboard</h2>
        <base-input v-model="name" is-required placeholder="Name" ref="name" default-error-message="Leaderboard name is required"/>
        <h2>Players</h2>
        <base-input-list :list="playerNames" is-required placeholder="Player name" ref="playerNames" default-error-message="Player name is required"/>
        <base-button is-primary name="Create Leaderboard"/>
    </form>
</template>

<script>
import BaseInput from './shared/BaseInput';
import BaseInputList from './shared/BaseInputList';
import BaseButton from './shared/BaseButton';

export default {
  name: 'leaderboard-create',
  components: {
    BaseButton, BaseInput, BaseInputList
  },
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
    },
    createLeaderboard() {
      this.$http.post(`leaderboard`, {
        name: this.name,
        playerNames: this.playerNames
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
