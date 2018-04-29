module PUBG.Call exposing (..)

import Http exposing (Request)
import PUBG.API.Player exposing (playerDecoder, Player, matchIdsFromPlayers)
import PUBG.API.Match exposing (matchDecoder, Match)
import PUBG.API.Common exposing (..)
import Task exposing (..)


getPlayers : Http.Request (Wrapper Player)
getPlayers =
    Http.get ("http://localhost:3333/pubg-stub/players") (wrap playerDecoder)


getMatch : MatchId -> Http.Request Match
getMatch matchId =
    Http.get ("http://localhost:3333/pubg-stub/matches/" ++ matchId) matchDecoder


fetchPlayers : Task String (Wrapper Player)
fetchPlayers =
    getPlayers
        |> toHttpTask


fetchMatch : MatchId -> Task String Match
fetchMatch matchId =
    getMatch matchId
        |> toHttpTask


fetchMatches : List MatchId -> List (Task String Match)
fetchMatches matchIds =
    List.map fetchMatch matchIds



-- something like this?
-- fetchPlayerMatches =
--     fetchPlayers
--         |> Task.map (unwrap >> matchIdsFromPlayers)
--         |> Task.andThen fetchMatches
-- http://tech.allo-media.net/learning/elm/2018/02/05/chaining-http-requests-in-elm.html


toHttpTask : Http.Request a -> Task String a
toHttpTask request =
    request
        |> Http.toTask
        |> Task.mapError toString
