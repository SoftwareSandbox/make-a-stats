const express = require('express');
const _ = require('lodash');
const path = require('path');
const app = express();

const {gen} = require('./gen');
let {players, matches} = gen([1,6],[2,9], "shroud", "chad", "h1z1survivor", "wadu");

//?filter[playernames]=jooones
app.get('/pubg-stub/players', (req, res) => {
    let playerNames = req.query.filter ? req.query.filter.playerNames.split(',') : [];
    if (isEmpty(playerNames)) {
        console.log(`Returning all players`);
    } else {
        console.log(`Returning players ${playerNames}`);
    }
    res.set('Access-Control-Allow-Origin', '*');
    let playersWithName = players.data
        .filter((player) => playerNames.indexOf(player.attributes.name) > -1);
    let result = isEmpty(playerNames) ? players : {...players, ...{data: playersWithName}};
    (isEmpty(playerNames) || !isEmpty(playersWithName)) ? res.json(result) : res.sendStatus(404);
});

app.get('/pubg-stub/matches/:id', (req, res) => {
    let id = req.params.id;
    console.log(`Returning match for id: ${id}`);
    res.set('Access-Control-Allow-Origin', '*');
    let matchesWithId = matches.filter((match) => match.data.id === id);
    !isEmpty(matchesWithId) ? res.json(matchesWithId[0]) : res.sendStatus(404);
});

// ?with[playerNames]=shroud,chad&with[minMaxMatches]=1,4&with[minMaxKills]=2,9
app.get('/pubg-stub/regen', (req, res) => {
    let playerNames = req.query.with ? req.query.with.playerNames.split(',') : [];
    let minMaxMatches = req.query.with ? req.query.with.minMaxMatches.split(',') : [];
    let minMaxKills = req.query.with ? req.query.with.minMaxKills.split(',') : [];
    players = gen(minMaxMatches, minMaxKills, playerNames).players;
    matches = gen(minMaxMatches, minMaxKills, playerNames).matches;
    res.status(200).send("Regenerated matches and players");
});

app.listen(3333, function () {
    console.log('API listening on http://localhost:3333/pubg-stub')
});

function isEmpty(array) {
    return _.isEmpty(array);
}