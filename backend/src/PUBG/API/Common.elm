module PUBG.API.Common exposing (..)

import Json.Decode exposing (Decoder, decodeValue, int, map, string, list)
import Json.Decode.Pipeline exposing (decode, required, optional)


{- e.g. https://api.playbattlegrounds.com/shards/pc-eu/players?filter[playerNames]=Jooones -}


type alias URL =
    String


type alias Links =
    { self : URL }


type alias Meta =
    {}


type alias Wrapper a =
    { data : List a
    , links : Links
    , meta : Meta
    }


unwrap : Wrapper a -> List a
unwrap wrapper =
    .data wrapper


unwrapFirst : Wrapper a -> Maybe a
unwrapFirst wrapper =
    wrapper
        |> .data
        |> List.head


wrap : Decoder a -> Decoder (Wrapper a)
wrap decoder =
    decode Wrapper
        |> required "data" (list decoder)
        |> required "links" linksDecoder
        |> optional "meta" metaDecoder {}


linksDecoder : Decoder Links
linksDecoder =
    decode Links
        |> required "self" string


metaDecoder : Decoder Meta
metaDecoder =
    decode Meta
