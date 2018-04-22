module PUBG.Player exposing (..)

import Json.Decode exposing (Decoder, decodeValue, int, map, string, list)
import Json.Decode.Pipeline exposing (decode, required)


type alias PlayerType =
    String


type alias PlayerId =
    String


type alias MatchId =
    String


type alias Name =
    String


type alias DateString =
    String


type alias Player =
    { playerType : PlayerType
    , id : PlayerId

    {- "account.2b95c68272fd467db565f5134277993b" -}
    , attributes : Attributes
    , relationships : Relationships
    }


type alias Attributes =
    {- "2018-04-06T17:31:00Z" -}
    { createdAt : DateString

    {- "2018-04-06T17:31:00Z" -}
    , updatedAt : DateString
    , name : Name
    , patchVersion : String
    , shardId : String
    }


type alias Match =
    { matchType : String
    , id : MatchId
    }


type alias Relationships =
    { matches : MatchesContainer }


type alias MatchesContainer =
    { data : List Match }


playerDecoder : Decoder Player
playerDecoder =
    decode Player
        |> required "type" string
        |> required "id" string
        |> required "attributes" attributesDecoder
        |> required "relationships" relationshipsDecoder


attributesDecoder : Decoder Attributes
attributesDecoder =
    decode Attributes
        |> required "createdAt" string
        |> required "updatedAt" string
        |> required "name" string
        |> required "patchVersion" string
        |> required "shardId" string


relationshipsDecoder : Decoder Relationships
relationshipsDecoder =
    decode Relationships
        |> required "matches" decodeMatchesContainer


decodeMatchesContainer : Decoder MatchesContainer
decodeMatchesContainer =
    decode MatchesContainer
        |> required "data" (list matchDecoder)


matchDecoder : Decoder Match
matchDecoder =
    decode Match
        |> required "type" string
        |> required "id" string
