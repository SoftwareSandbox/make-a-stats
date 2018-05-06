module PUBG.API.CommonTests exposing (..)

import Expect exposing (Expectation)
import Test exposing (..)
import Json.Decode exposing (decodeString, Decoder, string, list)
import Json.Decode.Pipeline exposing (decode, required)


-- our code

import PUBG.API.Common exposing (..)


liono =
    """{"name":"Lion-O","role":"hero"}"""


thundercats =
    """
{ "data": [
    {"name":"Snarf","role":"pet"},
    """
        ++ liono
        ++ """
]}
"""


pubgAPI : Test
pubgAPI =
    describe "Decoding PUBG API"
        [ test "we can decode a data object" <|
            \_ ->
                let
                    decodedLiono =
                        decodeString thundercatDecoder liono
                in
                    decodedLiono
                        |> Expect.equal
                            (Ok (Thundercat "Lion-O" "hero"))
        , test "we can decode some list of objects wrapped in a data object" <|
            \_ ->
                let
                    decodedCats =
                        decodeString (listDataDecoder thundercatDecoder) thundercats

                    expectedThundercats =
                        (Ok
                            { data =
                                [ Thundercat "Snarf" "pet"
                                , Thundercat "Lion-O" "hero"
                                ]
                            }
                        )
                in
                    decodedCats
                        |> Expect.equal
                            expectedThundercats
        ]


type alias Thundercat =
    { name : String, role : String }


thundercatDecoder : Decoder Thundercat
thundercatDecoder =
    decode Thundercat
        |> required "name" string
        |> required "role" string
