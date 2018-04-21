module All exposing (..)

import Expect exposing (Expectation)
import Test exposing (..)
import Json.Decode exposing (decodeString)
import PUBG.Player exposing (..)
import Fixtures.Player exposing (..)


suite : Test
suite =
    describe "Player"
        [ test "can decode a Player from a PUBG response" <|
            \_ ->
                decodeString playerDecoder playerResponse
                    |> Result.toMaybe
                    |> Maybe.map .id
                    |> Expect.equal (Just "1")
        ]
