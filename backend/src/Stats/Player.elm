module Stats.Player exposing (..)

import Json.Encode exposing (encode, object, Value, null, string, int, float, list)


--TODO: this should become the expected response in our frontend, and should be moved probably


type alias LeaderBoard =
    List PlayerStats


type alias PlayerStats =
    { player : String
    , totalKills : Int
    , amountOfMatchesPlayed : Int
    , killsPerMatch : KillsPerMatch
    }


type alias KillsPerMatch =
    Float



--TODO: Make an encoder for our representation of a Player (expected response)


toJson : Value -> String
toJson =
    encode 4


encodeLeaderboard : List PlayerStats -> Value
encodeLeaderboard playerstats =
    list <| List.map encodePlayerStat playerstats


encodePlayerStat : PlayerStats -> Value
encodePlayerStat playerstat =
    object
        [ ( "player", string playerstat.player )
        , ( "totalKills", int playerstat.totalKills )
        , ( "amountOfMatchesPlayed", int playerstat.amountOfMatchesPlayed )
        , ( "killsPerMatch", float playerstat.killsPerMatch )
        ]
