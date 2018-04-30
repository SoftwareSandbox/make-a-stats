<template>
    <section>
        <h2>Leaderboard</h2>
        <base-input is-required v-model="name" placeholder="Name"/>
        <h2>Players</h2>
        <base-input-list :list="playerNames" placeholder="Player name"/>
        <base-button is-primary name="Create Leaderboard" @click="createLeaderboard"/>
    </section>
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
    addPlayerName() {
      this.playerNames.push('');
    },
    deletePlayerName(index) {
      return this.playerNames.splice(index, 1);
    },
    isLastPlayer(index) {
      return index === this.playerNames.length - 1;
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
section
  display: flex
  flex-direction: column

  > h2
    color: #303030
    font-weight: 700
    font-size: 0.7rem
    text-transform: uppercase
</style>
