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
    margin: 5px;
    border-radius: 4px;
    border: 1px solid hsla(0, 0%, 0%, 0.2);
    box-shadow: 0 2px 4px 0 hsla(0, 0%, 0%, 0.2);
  }
  .player > h2 {
    flex: 1;
    color: hsl(0, 0%, 13%);
    font-weight: 700;
    font-size: 0.7rem;
    margin: 0;
    padding: 10px;
  }
  .statistics {
    flex: 1;
    display: flex;
    flex-direction: row;
    padding: 10px;
    background-color: hsla(0, 0%, 10%, 0.015);
  }
  .statistics > .statistic > span {
    flex: 1;
    color: hsl(0, 0%, 45%);
    font-size: 0.65rem;
    font-weight: 400;
    text-align: center;
  }
  .statistics > .statistic > span:first-child {
    margin-right: 4px;
  }
</style>
