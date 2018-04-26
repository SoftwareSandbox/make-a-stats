const express = require('express');
const _ = require('lodash');
const path = require('path');
const app = express();

const {players} = require('./players');
const {matches} = require('./matches');

//?filter[playernames]=jooones
app.get('/pubg-stub/player', (req, res) => {
    let playernames = req.query.filter ? req.query.filter.playernames.split(',') : [];
    if (isEmpty(playernames)) {
        console.log(`Returning all players`);
    } else {
        console.log(`Returning players ${playernames}`);
    }
    res.set('Access-Control-Allow-Origin', '*');
    let playersWithName = players.data
        .filter((player) => playernames.indexOf(player.attributes.name.toLowerCase()) > -1);
    let result = isEmpty(playernames) ? players : {...players,...{data:playersWithName}};
    (isEmpty(playernames) || playersWithName.length) > 0 ? res.json(result) : res.sendStatus(404);
});

app.get('/pubg-stub/matches/:id', (req, res) => {
    let id = req.params.id;
    console.log(`Returning match for id: ${id}`);
    res.set('Access-Control-Allow-Origin', '*');
    let matchesWithId = matches.filter((match) => match.data.id == id);
    matchesWithId.length > 0 ? res.json(matchesWithId[0]) : res.sendStatus(404);
});

app.listen(3333, function() {
    console.log('API listening on http://localhost:3333/pubg-stub')
});

function isEmpty(array){
    return _.isEmpty(array);
}