<template>
    <section class="player">
        <span>{{ playerStats.player }}</span>
        <span>{{ playerStats.totalKills }}</span>
        <span>{{ playerStats.amountOfMatchesPlayed }}</span>
        <span>{{ playerStats.killsPerMatch }}</span>
    </section>
</template>

<script>
    export default {
        name: 'PlayerStats',
        props: ['playerStats'],
    }
</script>

<style scoped>
    .player {
        display: flex;
        flex-direction: row;
        align-items: center;
        margin: 10px 0 0 0;
        border-radius: 4px;
        border: 1px solid hsla(0, 0%, 0%, 0.2);
        box-shadow: 0 2px 4px 0 hsla(0, 0%, 0%, 0.2);
        background-color: #ffffff;
    }

    .player > span {
        flex: 1;
        color: #737373;
        font-weight: 700;
        font-size: 1rem;
        text-transform: uppercase;
        text-align: center;
        margin: 5px;
        padding: 10px;
    }
</style>
