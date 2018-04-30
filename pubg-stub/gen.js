const _ = require('lodash');

/**
 * Generates Players and Matches to provide to the pubg-stub server
 * @param {Array} matchesRange range defining the amount of matches to generate
 * @param {Array} killsRange range defining the amount of kills to generate
 * @param {Array} players playernames
 */
function gen(matchesRange, killsRange, ...players) {
    let players = [];
    let matches = [];
    return {players: players, matches: matches};
}

module.exports = { gen: gen }