module PUBG.Call exposing (..)

import Http exposing (Request)
import PUBG.API.Player exposing (playerDecoder, Player)
import PUBG.API.Match exposing (matchDecoder, Match)
import PUBG.API.Common exposing (..)


getPlayers : Http.Request (Wrapper Player)
getPlayers =
    Http.get ("http://localhost:3333/pubg-stub/players") (wrap playerDecoder)


getMatch : MatchId -> Http.Request (Wrapper Match)
getMatch matchId =
    Http.get ("http://localhost:3333/pubg-stub/matches/" ++ matchId) (wrap matchDecoder)



--http://tech.allo-media.net/learning/elm/2018/02/05/chaining-http-requests-in-elm.html
-- player : Cmd PlayerId
-- player =
--     Http.send (Result.toMaybe >> Maybe.map .id >> Maybe.withDefault ("0")) getPlayers
