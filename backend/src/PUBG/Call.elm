module PUBG.Call exposing (..)

import Http exposing (Request)
import PUBG.Player exposing (..)


getPlayers : Http.Request Player
getPlayers =
    Http.get ("http://localhost:3000/pubg-stub/player") (playerDecoder)


type Msg
    = FoundPlayer PlayerId


player : Cmd PlayerId
player =
    Http.send (Result.toMaybe >> Maybe.map getPlayerId >> Maybe.withDefault ("0")) getPlayers


getPlayerId : Player -> PlayerId
getPlayerId player =
    .id player
