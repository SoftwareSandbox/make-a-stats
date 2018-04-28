<template>
    <section class="player-stats">
        <span>{{ statistics.player }}</span>
        <span>{{ statistics.totalKills }}</span>
        <span>{{ statistics.amountOfMatchesPlayed }}</span>
        <span>{{ statistics.killsPerMatch }}</span>
    </section>
</template>

<script>
    export default {
        name: 'leaderboard-player-stats',
        props: ['statistics'],
    }
</script>

<style lang="sass" scoped>
    .player-stats
        display: flex
        flex-direction: row
        align-items: center
        margin-top: 10px
        border-radius: 4px
        border: 1px solid hsla(0, 0%, 0%, 0.2)
        box-shadow: 0 2px 4px 0 hsla(0, 0%, 0%, 0.2)
        background-color: rgb(255, 255, 255)

        > span
            flex: 1
            color: rgb(115,115,115)
            font-weight: 700
            font-size: 1rem
            text-align: center
            text-transform: uppercase
            margin: 5px
            padding: 10px
</style>
