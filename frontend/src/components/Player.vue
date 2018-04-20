<template>
  <section class="player">
    <h2>{{ player.name }}</h2>
    <section class="statistics">
      <section class="statistic">
        <span>kills</span><span>{{ player.kills }}</span>
      </section>
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
    box-shadow: 0 4px 6px 0 #f2f2f2;
    background-color: #ffffff;
  }
  .player > h2 {
    flex: 1;
    color: #303030;
    font-weight: 700;
    font-size: 0.7rem;
    margin: 0;
  }
  .statistics {
    flex: 1;
    display: flex;
    flex-direction: row;
  }
  .statistics > .statistic > span {
    flex: 1;
    font-size: 0.65rem;
    text-align: center;
    font-style: italic;
  }
  .statistics > .statistic > span:first-child {
    margin-right: 4px;
  }
</style>
