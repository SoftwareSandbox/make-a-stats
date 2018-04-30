module PUBG.API.MatchTests exposing (..)

import Expect exposing (Expectation)
import Test exposing (..)
import Json.Decode exposing (decodeString)


-- our code

import PUBG.API.Match exposing (..)


-- Test data

import Fixtures.Match exposing (..)


pubgAPI : Test
pubgAPI =
    describe "Decoding PUBG API"
        [ test "we can decode a Match" <|
            \_ ->
                decodeString matchDecoder matchResponse
                    |> Result.map (.data >> .id)
                    |> Expect.equal (Ok "9cb69d4e-19a1-441a-a367-846023499350")
        ]
