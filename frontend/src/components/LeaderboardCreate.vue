<template>
    <section class="leaderboard-create">
        <h2>Leaderboard</h2>
        <base-input v-model="name" :required="true" :placeholder="'Name'" ref="name"/>

        <h2>Players</h2>
        <section v-for="(_, index) in playerNames" :key="index" class="player-name">
            <base-input v-model="playerNames[index]" :required="true" :placeholder="'Player name'" ref="playerNames"/>
            <base-button v-if="index !== playerNames.length - 1" :icon="'fa-trash-alt'" :onClick="deletePlayerName(index)"/>
            <base-button v-if="index === playerNames.length - 1" :icon="'fa-plus'" :onClick="addPlayerName"/>
        </section>

        <base-button :name="'Create Leaderboard'" :primary="true" :onClick="createLeaderboard"/>
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
        methods: {
            addPlayerName() {
                this.playerNames.push('');
            },
            deletePlayerName(index) {
                return () => this.playerNames.splice(index, 1);
            },
            createLeaderboard() {
                if (this.isFormValid()) {
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
            },
            isFormValid() {
                const isNameValid = this.$refs.name.validate();
                const arePlayerNamesValid = this.$refs.playerNames
                    .map(component => component.validate())
                    .reduce((a, b) => a && b);
                return isNameValid && arePlayerNamesValid;
            }
        }
    }
</script>

<style lang="sass" scoped>
    .leaderboard-create
        display: flex
        flex-direction: column

        > h2
            color: #303030
            font-weight: 700
            font-size: 0.7rem
            text-transform: uppercase

        > button
            flex: 1
            margin-top: 5px

        .player-name
            flex: 1
            display: flex
            flex-direction: row
            margin: 0 0 5px

            button
                border-left: 0
</style>
