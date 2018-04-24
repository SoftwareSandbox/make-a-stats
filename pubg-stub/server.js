const express = require('express');
const _ = require('lodash');
const path = require('path');
const app = express();

const {players} = require('./players');
const {matches} = require('./matches');

//?filter[playernames]=jooones
app.get('/pubg-stub/player', (req, res) => {
    let playernames = req.query.filter.playernames.split(',');
    console.log(`Returning players ${playernames}`);
    res.set('Access-Control-Allow-Origin', '*');
    let playersWithName = players.data.filter((player) => playernames.indexOf(player.attributes.name.toLowerCase()) > -1);
    let result = Object.assign(players,{data:playersWithName});
    console.log(`result = ${JSON.stringify(result)}`);
    // playersWithName.length > 0 ? res.json(result) : res.status(404).render();
    res.json(result);
});

app.get('/pubg-stub/matches/:id', (req, res) => {
    let id = req.params.id;
    console.log(`Returning match for id: ${id}`);
    res.set('Access-Control-Allow-Origin', '*');
    let matchesWithId = matches.filter((match) => match.data.id == id);
    matchesWithId.length > 0 ? res.json(matchesWithId[0]) : res.status(404).render();
});

app.listen(3333, function() {
    console.log('API listening on http://localhost:3333/pubg-stub')
});

