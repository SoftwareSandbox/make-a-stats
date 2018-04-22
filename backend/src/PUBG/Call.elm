module PUBG.Call exposing (..)

import Http exposing (Request)
import PUBG.API.Player exposing (..)
import PUBG.API.Common exposing (..)


getPlayers : Http.Request (Wrapper Player)
getPlayers =
    Http.get ("http://localhost:3333/pubg-stub/player") (wrap playerDecoder)



-- player : Cmd PlayerId
-- player =
--     Http.send (Result.toMaybe >> Maybe.map .id >> Maybe.withDefault ("0")) getPlayers
