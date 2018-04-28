<template>
    <section class="create-leaderboard">
        <section class="container">
            <h2>Leaderboard</h2>
            <base-input v-model="name" :placeholder="'Leaderboard'"/>
            <h2>Players</h2>
            <section v-for="(playerName, index) in playerNames" v-bind:key="index" class="player-names">
                <base-input v-model="playerNames[index]" :placeholder="'Player'"/>
                <base-button v-if="index !== 0"
                             :icon="'fa-trash-alt'"
                             :onClick="deletePlayerName(index)"/>
            </section>
            <section class="actions">
                <base-button :name="'Add Player'"
                             :onClick="addPlayerName"/>
                <base-button :name="'Create Leaderboard'"
                             :primary="true"
                             :onClick="createLeaderboard"/>
            </section>
        </section>
    </section>
</template>

<script>
    import BaseInput from './shared/BaseInput';
    import BaseButton from './shared/BaseButton';

    export default {
        name: 'leaderboard-create',
        components: {
            BaseButton, BaseInput
        },
        data: function () {
            return {
                name: '',
                playerNames: ['']
            }
        },
        computed: {
            uniquePlayerNames: function () {
                let playerNames = this.playerNames;
                const filterEmptyNames = playerNames
                    .map(name => name.trim())
                    .filter(name => name !== '');
                return [...new Set(filterEmptyNames)];
            },
        },
        methods: {
            addPlayerName() {
                this.playerNames.push('');
            },
            deletePlayerName(index) {
                return () => this.playerNames.splice(index, 1);
            },
            createLeaderboard() {
                this.$http.post(`leaderboard`, {
                    name: this.name,
                    playerNames: this.uniquePlayerNames
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

<style scoped>
    .create-leaderboard {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .create-leaderboard > .container {
        display: flex;
        flex-direction: column;
        width: 500px;
    }

    h2 {
        color: #303030;
        font-weight: 700;
        font-size: 0.7rem;
        text-transform: uppercase;
    }

    .player-names {
        flex: 1;
        display: flex;
        flex-direction: row;
        margin: 0 0 5px;
    }

    .player-names > input {
        flex: 1;
    }

    .player-names > button {
        border-left: 0;
    }

    .actions {
        display: flex;
        flex-direction: row;
        align-items: center;
    }

    .actions > button {
        flex: 1;
        margin-top: 5px;
    }

    .actions > button:first-child {
        margin-right: 2px;
    }

    .actions > button:last-child {
        margin-left: 2px;
    }
</style>
