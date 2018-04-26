<template>
    <section class="leaderboard">
        <h1>{{ name }}</h1>
        <section class="player-stats">
            <section class="stats">
                <span>Player</span>
                <span>Kills</span>
                <span>Matches</span>
                <span>Avg</span>
            </section>
            <section class="players">
                <PlayerStats v-for="playerStat in playerStatsSortedByKills"
                             v-bind:key="playerStat.player"
                             v-bind:playerStats="playerStat"/>
            </section>
        </section>
    </section>
</template>

<script>
    import PlayerStats from './PlayerStats'

    export default {
        name: 'LeaderBoard',
        components: {
            PlayerStats
        },
        data: function () {
            return {
                name: '',
                playerStats: []
            }
        },
        computed: {
            id: function () {
                return this.$route.params.id;
            },
            playerStatsSortedByKills: function () {
                let playerStats = this.playerStats;
                return playerStats.sort((p1, p2) => this.comparePlayerStats(p2, p1, 'totalKills'));
            }
        },
        created: function () {
            this.$http.get(`leaderboard/${this.id}`)
                .then(response => this.refreshLeaderboard(response.body));
        },
        methods: {
            refreshLeaderboard(leaderboard) {
                this.name = leaderboard.name;
                this.playerStats = leaderboard.playerStats;
            },
            comparePlayerStats(playerStats1, playerStats2, property) {
                const x = playerStats1[property];
                const y = playerStats2[property];
                return x < y ? -1 : x > y ? 1 : 0;
            }
        }
    }
</script>

<style scoped>
    .leaderboard {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    h1 {
        color: #303030;
        font-weight: 700;
        font-size: 2.5rem;
        text-transform: uppercase;
        text-align: center;
    }

    .player-stats {
        display: flex;
        flex-direction: column;
    }

    .players {
        flex-direction: column;
    }

    .stats {
        flex: 1;
        display: flex;
        flex-direction: row;
        align-items: center;
        border-radius: 4px;
        border: 1px solid hsla(0, 0%, 0%, 0.2);
        box-shadow: 0 2px 4px 0 hsla(0, 0%, 0%, 0.2);
        background-color: #ffffff;
    }

    .stats > span {
        flex: 1;
        color: #737373;
        font-weight: 700;
        font-size: 1.5rem;
        text-transform: uppercase;
        text-align: center;
        margin: 5px;
        padding: 10px;
        width: 100px;
    }
</style>
