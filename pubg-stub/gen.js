const _ = require('lodash');
const uuid = require('uuid/v4');

const {players, playerTemplate} = require('./players.js');
const {matches, matchTemplate, participantTemplate} = require('./matches.js');

/**
 * Generates Players and Matches to provide to the pubg-stub server
 * @param {Array} matchesRange range defining the random amount of matches to generate
 * @param {Array} killsRange range defining the random amount of kills to generate
 * @param {Array} playerNames playerNames, max. 4
 */
function gen([minMatchRange, maxMatchRange], [minKillsRange, maxKillsRange], ...playerNames) {
    if (playerNames.length > 4) throw new Error("can't have more than 4 players in a team");
    
    let players = playerNames
                    .map((playerName) => createPlayer(uuid(), playerName));
    let playerIds = players.map(player => player.id);

    let matches = _.range(minMatchRange, random(minMatchRange, maxMatchRange), 1)
                    .map((unused) => uuid())
                    .map((matchId) => createMatch(matchId, playerIds, [minKillsRange, maxKillsRange]));
    
    let matchIds = matches.map(m => m.data.id);

    assignGeneratedMatchIdsToGeneratedPlayers(players, matchIds);

    let wrappedPlayers = {
        data: players,
        links: { self: "https://api.playbattlegrounds.com/shards/pc-eu/players?filter[playerNames]=Jooones" },
        meta: {}
    };
    return {players: wrappedPlayers, matches: matches};
}

function createPlayer(playerId, playerName){
    let player = _.cloneDeep(playerTemplate);
    player.id = "account."+playerId;
    player.attributes.name = playerName;
    return player;
}

function createMatch(matchId, playerIds, [minKills, maxKills]) {
    let match = _.cloneDeep(matchTemplate);
    match.data.id = matchId;
    
    let participantIds = playerIds.map(playerId => uuid());
    let participants = participantIds.map(pid => {return {type : "participant", id : pid};} );
    match.included[0].relationships.participants.data = participants;

    _.zip(playerIds, participantIds)
        .map(([playerId, pid]) => createParticipant(pid, playerId, random(minKills, maxKills)));

    return match;
}

function createParticipant(pid, playerId, randomKills) {
    let participant = _.cloneDeep(participantTemplate);

    participant.id = pid;
    participant.attributes.stats.playerId = playerId;
    participant.attributes.stats.kills = randomKills;

    return participant;
}

function assignGeneratedMatchIdsToGeneratedPlayers(players, matchIds){
    players.forEach(p => p.relationships.matches.data = matchIds.map(mid => {return {type:"match",id:mid};}));
}

function random(min, max) {
    _.random(min, max, false);
}

module.exports = { gen: gen }