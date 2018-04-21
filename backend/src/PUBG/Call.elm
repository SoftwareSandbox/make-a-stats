module PUBG.Call exposing (..)

import Platform.Cmd.Cmd exposing (..)
import Http exposing (Request)
import PUBG.Player exposing (..)


getPlayers : Http.Request Player
getPlayers =
    Http.get ("http://localhost:3000/pubg-stub/player") (playerDecoder)


player : Cmd PlayerId
player =
    Http.send (Result.toMaybe >> Maybe.map .id >> Maybe.withDefault ("0")) getPlayers
