module PUBG.API.PlayerTests exposing (..)

import Expect exposing (Expectation)
import Test exposing (..)
import Json.Decode exposing (decodeString)


-- our code

import PUBG.API.Player exposing (..)
import PUBG.API.Common exposing (..)


-- Test data

import Fixtures.Player exposing (..)


pubgAPI : Test
pubgAPI =
    describe "Decoding PUBG API"
        [ test "we can decode a Player" <|
            \_ ->
                decodeString (wrap playerDecoder) playerResponse
                    |> Result.toMaybe
                    |> Maybe.andThen unwrapFirst
                    |> Maybe.map .id
                    |> Expect.equal (Just "account.2b95c68272fd467db565f5134277993b")
        ]
