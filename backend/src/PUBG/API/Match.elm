module PUBG.API.Match exposing (..)

import PUBG.API.Common exposing (..)


{- e.g. https://api.playbattlegrounds.com/shards/pc-eu/matches/9cb69d4e-19a1-441a-a367-846023499350 -}


type alias RosterId =
    String


type alias RosterLite =
    { id : RosterId }


type alias GameMode =
    String


type alias Match =
    { data :
        { matchType : String
        , id : MatchId
        , attributes :
            { createdAt : String
            , duration : Int
            , gameMode : GameMode
            , mapName : String
            }
        , relationships :
            { rosters :
                { data : List RosterLite }
            }
        }
    , included : List MatchInclusion
    }


type MatchInclusionAttributes
    = RosterAttr Roster
    | ParticipantAttr Participant


type alias MatchInclusion =
    { matchInclusionType : MatchInclusionType
    , matchInclusionId : MatchInclusionId
    , attributes : MatchInclusionAttributes
    }


type alias RosterType =
    String


type alias ParticipantType =
    String


type MatchInclusionType
    = RosterType
    | ParticipantType


type alias MatchInclusionId =
    String


type alias Rank =
    Int


type alias TeamId =
    Int


type alias Roster =
    { id : RosterId
    , attributes :
        { shardId : String
        , stats :
            { rank : Rank
            , teamId : TeamId
            }
        , won : Bool
        }
    , relationships :
        { participants :
            { data : List ParticipantLite }
        }
    }


type alias ParticipantLite =
    { id : ParticipantId }


type alias ParticipantId =
    String


type alias Participant =
    { id : ParticipantId
    , attributes :
        { actor : String
        , shardId : String
        , stats : ParticipantStats
        }
    }


type alias ParticipantStats =
    { dbnos : Int
    , assists : Int
    , boosts : Int
    , damageDealt : Float
    , deathType : String
    , headshotKills : Int
    , heals : Int
    , killPlace : Int
    , killPoints : Int
    , killPointsDelta : Float
    , killStreaks : Int
    , kills : Int
    , lastKillPoints : Int
    , lastWinPoints : Int
    , longestKill : Int
    , mostDamage : Float
    , name : String
    , playerId : PlayerId
    , revives : Int
    , rideDistance : Float
    , roadKills : Int
    , teamKills : Int
    , timeSurvived : Int
    , vehicleDestroys : Int
    , walkDistance : Float
    , weaponsAcquired : Int
    , winPlace : Int
    , winPoints : Int
    , winPointsDelta : Float
    }
