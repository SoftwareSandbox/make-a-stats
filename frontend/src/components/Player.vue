<template>
  <section class="player">
    <h2>{{ player.name }}</h2>
    <section class="statistics">
      <span>kills</span><span>{{ player.kills }}</span>
    </section>
  </section>
</template>

<script>
export default {
  name: 'Player',
  props: ['player'],
}
</script>

<style scoped>
  .player {
    flex: 1;
    display: flex;
    flex-direction: column;
    padding: 10px;
    margin: 5px;
    border-radius: 5px;
    border: 1px solid #d9d9d9;
    box-shadow: 2px 2px #f2f2f2;
    background-color: #f2f2f2;
  }
  h2 {
    flex: 1;
    font-weight: 700;
    font-size: 1.5rem;
    text-transform: uppercase;
    text-align: center;
    margin: 0;
    padding: 10px;
    border-bottom: 1px solid #d9d9d9;
  }
  .statistics {
    flex: 1;
    display: flex;
    flex-direction: row;
    padding: 10px;
  }
  .statistics > span {
    flex: 1;
    text-align: center;
  }
</style>
