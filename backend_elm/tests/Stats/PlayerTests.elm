module Stats.PlayerTests exposing (..)

import Expect exposing (Expectation)
import Test exposing (..)


-- our code

import Stats.Player exposing (..)


encodingToJSON : Test
encodingToJSON =
    let
        schelpStats =
            PlayerStats "Sch3lp" 12 6 2

        daxudeStats =
            PlayerStats "Daxude" 15 3 5

        jonesStats =
            PlayerStats "Jones" 5 10 0.5

        hahawinStats =
            PlayerStats "Hahawin" 14 7 2

        leaderboard =
            [ schelpStats, daxudeStats, jonesStats, hahawinStats ]
    in
        describe "Encoding to JSON"
            [ test "we can encode a PlayerStats" <|
                \_ ->
                    encodePlayerStat schelpStats
                        |> toJson
                        |> Expect.equal
                            """{
    "player": "Sch3lp",
    "totalKills": 12,
    "amountOfMatchesPlayed": 6,
    "killsPerMatch": 2
}"""
            , test "we can encode a LeaderBoard" <|
                \_ ->
                    encodeLeaderboard leaderboard
                        |> toJson
                        |> Expect.equal
                            """[
    {
        "player": "Sch3lp",
        "totalKills": 12,
        "amountOfMatchesPlayed": 6,
        "killsPerMatch": 2
    },
    {
        "player": "Daxude",
        "totalKills": 15,
        "amountOfMatchesPlayed": 3,
        "killsPerMatch": 5
    },
    {
        "player": "Jones",
        "totalKills": 5,
        "amountOfMatchesPlayed": 10,
        "killsPerMatch": 0.5
    },
    {
        "player": "Hahawin",
        "totalKills": 14,
        "amountOfMatchesPlayed": 7,
        "killsPerMatch": 2
    }
]"""
            ]
