module PUBG.Call exposing (..)

import Http exposing (Request)
import PUBG.API.Player exposing (playerDecoder, Player, matchIdsFromPlayers, matchIdsPerPlayers)
import PUBG.API.Match exposing (matchDecoder, Match)
import PUBG.API.Common exposing (..)
import Task exposing (..)


getPlayerMatches : Task String (List ( Player, List Match ))
getPlayerMatches =
    getPlayers
        |> Task.map (unwrap >> matchIdsPerPlayers)
        |> Task.andThen getMatchesPerPlayer


getPlayers : Task String (Wrapper Player)
getPlayers =
    fetchPlayers
        |> toHttpTask


getMatchesPerPlayer : List ( Player, List MatchId ) -> Task String (List ( Player, List Match ))
getMatchesPerPlayer matchIdsGroupedPerPlayer =
    matchIdsGroupedPerPlayer
        |> List.map getMatchesOfPlayer
        |> Task.sequence


getMatchesOfPlayer : ( Player, List MatchId ) -> Task String ( Player, List Match )
getMatchesOfPlayer ( player, matchIds ) =
    getMatches matchIds
        |> Task.map (\ms -> ( player, ms ))


getMatches : List MatchId -> Task String (List Match)
getMatches matchIds =
    List.map getMatch matchIds
        |> Task.sequence


getMatch : MatchId -> Task String Match
getMatch matchId =
    fetchMatch matchId
        |> toHttpTask


fetchPlayers : Http.Request (Wrapper Player)
fetchPlayers =
    Http.get ("http://localhost:3333/pubg-stub/players") (wrap playerDecoder)


fetchMatch : MatchId -> Http.Request Match
fetchMatch matchId =
    Http.get ("http://localhost:3333/pubg-stub/matches/" ++ matchId) matchDecoder



-- http://tech.allo-media.net/learning/elm/2018/02/05/chaining-http-requests-in-elm.html


toHttpTask : Http.Request a -> Task String a
toHttpTask request =
    request
        |> Http.toTask
        |> Task.mapError toString
