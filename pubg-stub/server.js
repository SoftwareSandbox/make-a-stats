const express = require('express');
const _ = require('lodash');
const path = require('path');
const app = express();

const player = initPlayer();

app.get('/pubg-stub/player', (req, res) => {
    let id = req.params.id;
    console.log(`Returning player`);
    res.set('Access-Control-Allow-Origin', '*');
    res.json(player);
})

app.get('/pubg-stub/player/:id', (req, res) => {
    let id = req.params.id;
    console.log(`Returning player for id: ${id}`);
    res.set('Access-Control-Allow-Origin', '*');
    let playersWithId = players.filter((player) => player.id == id);
    playersWithId.length > 0 ? res.json(playersWithId[0]) : res.status(404).render();
})

app.get('/pubg-stub/players/:id', (req, res) => {
    let requestedPlayerId = req.params.id;
    console.log(`Returning player for id: ${requestedPlayerId}`);
    res.set('Access-Control-Allow-Origin', '*');
    let playersWithId = players.filter((player) => player.id == requestedPlayerId);
    playersWithId.length > 0 ? res.json(playersWithId[0]) : res.status(404).render();
})

app.get('/pubg-stub/players', (req, res) => {
    res.set('Access-Control-Allow-Origin', '*');
    res.json(players);
})

app.listen(3333, function() {
    console.log('API listening on http://localhost:3333/pubg-stub')
})

function initPlayer(){
    return {
        data: [
            {
                type: "player",
                id: "account.2b95c68272fd467db565f5134277993b",
                attributes: {
                    createdAt: "2018-04-06T17:31:00Z",
                    name: "Jooones",
                    patchVersion: "",
                    shardId: "pc-eu",
                    stats: null,
                    titleId: "bluehole-pubg",
                    updatedAt: "2018-04-06T17:31:00Z"
                },
                relationships: {
                    assets: {
                        data: []
                    },
                    matches: {
                        data: [
                            {
                                type: "match",
                                id: "9cb69d4e-19a1-441a-a367-846023499350"
                            },
                            {
                                type: "match",
                                id: "f671c855-bf0b-47c3-96eb-0441d85947f4"
                            },
                            {
                                type: "match",
                                id: "70ca08d8-1844-4fb7-85ed-2fbe7cbae60c"
                            },
                            {
                                type: "match",
                                id: "fc7593ed-292a-4216-80ba-37bebd115b89"
                            },
                            {
                                type: "match",
                                id: "3cac7fc6-f06f-41aa-baad-c429dad6a653"
                            },
                            {
                                type: "match",
                                id: "486f1b96-f258-4169-8086-beff292d7550"
                            },
                            {
                                type: "match",
                                id: "7c159973-517d-472a-b430-cf5e5931e608"
                            },
                            {
                                type: "match",
                                id: "b8609651-f477-418b-b1dd-ab6173ef654f"
                            },
                            {
                                type: "match",
                                id: "9b533f53-6417-41d4-b4d3-290f18f5e76b"
                            },
                            {
                                type: "match",
                                id: "cefdb59f-0ef1-4d0f-936a-89e8c5b6dfe1"
                            },
                            {
                                type: "match",
                                id: "28f118dc-3f67-4712-af82-a1a96c3cacfc"
                            },
                            {
                                type: "match",
                                id: "1646766d-e85c-4b4f-b6dc-907eb8979f24"
                            },
                            {
                                type: "match",
                                id: "bec8880a-141e-4065-815c-07f07ef29f5e"
                            },
                            {
                                type: "match",
                                id: "043a1c78-b5de-43f5-9ca0-fae85f426b00"
                            },
                            {
                                type: "match",
                                id: "3fdcf1a4-239f-433a-8e49-988b702d1e94"
                            },
                            {
                                type: "match",
                                id: "85f05489-6840-43d0-902d-2c6c4feb891a"
                            },
                            {
                                type: "match",
                                id: "348087f8-5f39-457b-87a6-1b138ad0b5cf"
                            },
                            {
                                type: "match",
                                id: "3cc18df7-d787-4cf6-a447-a0db95e54fb6"
                            },
                            {
                                type: "match",
                                id: "1b81dde3-c06d-4e11-84d6-1a247a6f87ea"
                            },
                            {
                                type: "match",
                                id: "25dbee9f-e332-4517-b3d8-6cf7d81fb7c7"
                            },
                            {
                                type: "match",
                                id: "5e002bba-cd90-44ef-8850-fb53751876b1"
                            },
                            {
                                type: "match",
                                id: "eef1e334-b71c-4781-975a-4190564df679"
                            },
                            {
                                type: "match",
                                id: "66a62f99-733f-402c-8f05-e7b1376d1175"
                            },
                            {
                                type: "match",
                                id: "28b92a45-ea2b-473a-a977-61f8125a9ba3"
                            },
                            {
                                type: "match",
                                id: "16d14b59-24de-40ca-932a-3708e385f834"
                            },
                            {
                                type: "match",
                                id: "2988144c-106e-44aa-be1e-93365d06262e"
                            },
                            {
                                type: "match",
                                id: "1475d273-96eb-4ec0-bf1b-884fd32d096c"
                            },
                            {
                                type: "match",
                                id: "b6ee5ee0-6eca-4d30-bc07-45b0b87d094f"
                            },
                            {
                                type: "match",
                                id: "cbc9401d-99f2-455e-8f46-5af2057bd7da"
                            },
                            {
                                type: "match",
                                id: "be754e1c-aae3-4131-a043-7567b34bc213"
                            },
                            {
                                type: "match",
                                id: "2733e14c-b364-4b72-8c37-14be2901ddbd"
                            },
                            {
                                type: "match",
                                id: "ca9b1561-202c-4ea3-8e92-e20dd340c2a1"
                            },
                            {
                                type: "match",
                                id: "4f6679df-1cce-4b47-a773-6cc7df73465c"
                            },
                            {
                                type: "match",
                                id: "2c317241-faba-4e8d-a728-c22ef018886f"
                            },
                            {
                                type: "match",
                                id: "ab7032aa-e800-4e72-a4fe-1f14e0a3181c"
                            },
                            {
                                type: "match",
                                id: "840d8e1f-059d-4f8a-9a30-1704f5521bf9"
                            }
                        ]
                    }
                },
                links: {
                    schema: "",
                    self: "https://api.playbattlegrounds.com/shards/pc-eu/players/account.2b95c68272fd467db565f5134277993b"
                }
            }
        ],
        links: {
            self: "https://api.playbattlegrounds.com/shards/pc-eu/players?filter[playerNames]=Jooones"
        },
        meta: {}
    };
}