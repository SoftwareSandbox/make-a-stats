module PUBG.API.Match exposing (..)

import Json.Decode exposing (Decoder, decodeValue, int, map, string, list, bool, float, field, succeed)
import Json.Decode.Pipeline exposing (decode, required, resolve, custom)
import PUBG.API.Common exposing (..)


{- e.g. https://api.playbattlegrounds.com/shards/pc-eu/matches/9cb69d4e-19a1-441a-a367-846023499350 -}


totalKills : PlayerId -> List Match -> Int
totalKills playerId matches =
    List.filterMap (killsForPlayer playerId) matches
        |> List.sum


killsForPlayer : PlayerId -> Match -> Maybe Int
killsForPlayer playerId match =
    List.filterMap (participantWith playerId) match.included
        |> List.head
        |> Maybe.map (.attributes >> .stats >> .kills)


participantWith : PlayerId -> MatchInclusion -> Maybe Participant
participantWith playerId incl =
    case incl of
        ParticipantInclusion participant ->
            if participant.attributes.stats.playerId == playerId then
                Just participant
            else
                Nothing

        _ ->
            Nothing


type alias RosterId =
    String


type alias RosterLite =
    { id : RosterId }


type alias GameMode =
    String


type alias Match =
    { data : MatchData
    , included : List MatchInclusion
    }


type alias MatchData =
    { matchType : String
    , id : MatchId
    , attributes : MatchDataAttrs
    , relationships : MatchDataRelationships
    }


type alias MatchDataAttrs =
    { createdAt : String
    , duration : Int
    , gameMode : GameMode
    , mapName : String
    }


type alias MatchDataRelationships =
    { rosters : ListData RosterLite }


type MatchInclusion
    = RosterInclusion Roster
    | ParticipantInclusion Participant
    | UnknownMatchInclusion


type alias RosterType =
    String


type alias ParticipantType =
    String


type alias MatchInclusionId =
    String


type alias Rank =
    Int


type alias TeamId =
    Int


type alias Roster =
    { id : RosterId
    , attributes : RosterAttrs
    , relationships : RosterRelationships
    }


type alias RosterAttrs =
    { shardId : String
    , stats : RosterAttrStats
    , won : String
    }


type alias RosterAttrStats =
    { rank : Rank
    , teamId : TeamId
    }


type alias RosterRelationships =
    { participants :
        { data : List ParticipantLite }
    }


type alias ParticipantLite =
    { id : ParticipantId }


type alias ParticipantId =
    String


type alias Participant =
    { id : ParticipantId
    , attributes : ParticipantAttrs
    }


type alias ParticipantAttrs =
    { actor : String
    , shardId : String
    , stats : ParticipantStats
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



-- decoders


matchDecoder : Decoder Match
matchDecoder =
    decode Match
        |> required "data" matchDataDecoder
        |> required "included" (list matchInclusionDecoder)


matchDataDecoder : Decoder MatchData
matchDataDecoder =
    decode MatchData
        |> required "type" string
        |> required "id" string
        |> required "attributes" matchDataAttrsDecoder
        |> required "relationships" matchDataRelationshipsDecoder


matchDataAttrsDecoder : Decoder MatchDataAttrs
matchDataAttrsDecoder =
    decode MatchDataAttrs
        |> required "createdAt" string
        |> required "duration" int
        |> required "gameMode" string
        |> required "mapName" string


matchDataRelationshipsDecoder : Decoder MatchDataRelationships
matchDataRelationshipsDecoder =
    decode MatchDataRelationships
        |> required "rosters" (listDataDecoder rosterLiteDecoder)


rosterLiteDecoder : Decoder RosterLite
rosterLiteDecoder =
    decode RosterLite
        |> required "id" string


matchInclusionDecoder : Decoder MatchInclusion
matchInclusionDecoder =
    field "type" string |> map matchInclusionAttrsDecoder |> resolve


matchInclusionAttrsDecoder : String -> Decoder MatchInclusion
matchInclusionAttrsDecoder matchInclusionType =
    case matchInclusionType of
        "roster" ->
            map RosterInclusion rosterDecoder

        "participant" ->
            map ParticipantInclusion participantDecoder

        _ ->
            succeed UnknownMatchInclusion


rosterDecoder : Decoder Roster
rosterDecoder =
    decode Roster
        |> required "id" string
        |> required "attributes" rosterAttrsDecoder
        |> required "relationships" rosterRelationshipsDecoder


rosterAttrsDecoder : Decoder RosterAttrs
rosterAttrsDecoder =
    decode RosterAttrs
        |> required "shardId" string
        |> required "stats" rosterAttrStatsDecoder
        |> required "won" string


rosterAttrStatsDecoder : Decoder RosterAttrStats
rosterAttrStatsDecoder =
    decode RosterAttrStats
        |> required "rank" int
        |> required "teamId" int


rosterRelationshipsDecoder : Decoder RosterRelationships
rosterRelationshipsDecoder =
    decode RosterRelationships
        |> required "participants" (listDataDecoder participantLiteDecoder)


participantLiteDecoder : Decoder ParticipantLite
participantLiteDecoder =
    decode ParticipantLite
        |> required "id" string


participantDecoder : Decoder Participant
participantDecoder =
    decode Participant
        |> required "id" string
        |> required "attributes" participantAttrsDecoder


participantAttrsDecoder : Decoder ParticipantAttrs
participantAttrsDecoder =
    decode ParticipantAttrs
        |> required "actor" string
        |> required "shardId" string
        |> required "stats" participantStatsDecoder


participantStatsDecoder : Decoder ParticipantStats
participantStatsDecoder =
    decode ParticipantStats
        |> required "DBNOs" int
        |> required "assists" int
        |> required "boosts" int
        |> required "damageDealt" float
        |> required "deathType" string
        |> required "headshotKills" int
        |> required "heals" int
        |> required "killPlace" int
        |> required "killPoints" int
        |> required "killPointsDelta" float
        |> required "killStreaks" int
        |> required "kills" int
        |> required "lastKillPoints" int
        |> required "lastWinPoints" int
        |> required "longestKill" int
        |> required "mostDamage" float
        |> required "name" string
        |> required "playerId" string
        |> required "revives" int
        |> required "rideDistance" float
        |> required "roadKills" int
        |> required "teamKills" int
        |> required "timeSurvived" int
        |> required "vehicleDestroys" int
        |> required "walkDistance" float
        |> required "weaponsAcquired" int
        |> required "winPlace" int
        |> required "winPoints" int
        |> required "winPointsDelta" float
