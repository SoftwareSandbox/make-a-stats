<template>
  <div id="app">
    <LeaderBoard />
  </div>
</template>

<script>
import LeaderBoard from './components/LeaderBoard.vue'

export default {
  name: 'app',
  components: {
    LeaderBoard
  }
}
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Open+Sans:400,700');

  body {
    font-weight: 400;
    font-family: 'Open Sans', sans-serif;
    background-color: rgba(255, 255, 255, 0.6);
  }
</style>
