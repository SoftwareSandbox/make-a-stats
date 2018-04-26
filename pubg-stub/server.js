const express = require('express');
const _ = require('lodash');
const path = require('path');
const app = express();

const {players} = require('./players');
const {matches} = require('./matches');

///pubg-stub/players?filter[playerNames]=Jooones
app.get('/pubg-stub/players', (req, res) => {
    let playerNames = req.query.filter.playerNames.split(',');
    console.log(`Returning players ${playerNames}`);
    res.set('Access-Control-Allow-Origin', '*');
    let playersWithName = players.data.filter((player) => playerNames.indexOf(player.attributes.name) > -1);
    let result = {data:playersWithName};
    playersWithName.length > 0 ? res.json(result) : res.sendStatus(404);
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

