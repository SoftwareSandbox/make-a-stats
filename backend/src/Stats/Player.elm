module Stats.Player exposing (..)

--TODO: this should become the expected response in our frontend, and should be moved probably


type alias Player =
    {}


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
