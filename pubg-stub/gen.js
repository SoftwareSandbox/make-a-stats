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
    
    console.log(`Generating between ${minMatchRange} and ${maxMatchRange} matches for ${playerNames.join(', ')}`);
    console.log(`They'll each have between ${minKillsRange} and ${maxKillsRange} kills`);

    let players = playerNames
                    .map((playerName) => createPlayer(uuid(), playerName));
    let playerTuples = players.map(player => {
        return {id: player.id, name: player.attributes.name};
    });

    let matches = _.range(minMatchRange, random(minMatchRange, maxMatchRange), 1)
                    .map((unused) => uuid())
                    .map((matchId) => createMatch(matchId, playerTuples, minKillsRange, maxKillsRange));
    
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

function createMatch(matchId, playerTuples, minKills, maxKills) {
    console.log(`minKills: ${minKills}; maxKills: ${maxKills}`);
    let match = _.cloneDeep(matchTemplate);
    match.data.id = matchId;
    
    let participantIds = playerTuples.map(p => uuid());
    let participants = participantIds.map(pid => {
        return {type : "participant", id : pid};
    });
    match.included[0].relationships.participants.data = participants;

    let participantInclusions = _.zip(playerTuples, participantIds)
                    .map(([playerTuple, participantId]) => createParticipant(participantId, playerTuple, random(minKills, maxKills)))
                    .forEach(p => match.included.push(p));
    
    return match;
}

function createParticipant(participantId, playerTuple, randomKills) {
    console.log(`Player with ${playerTuple.id}, should have ${randomKills} kills`);
    let participant = _.cloneDeep(participantTemplate);

    participant.id = participantId;
    participant.attributes.stats.playerId = playerTuple.id;
    participant.attributes.stats.kills = randomKills;
    participant.attributes.stats.name = playerTuple.name;

    return participant;
}

function assignGeneratedMatchIdsToGeneratedPlayers(players, matchIds) {
    players.forEach(player => {
        player.relationships.matches.data = matchIds.map(mid => {
            console.log(`Assigning matchId ${mid} to ${player.attributes.name}`);
            return {type:"match",id:mid};
        });
    });
}

function random(min, max) {
    return _.random(min, max, false);
}

module.exports = { gen: gen }