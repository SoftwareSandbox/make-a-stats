port module Stats.API exposing (..)

import Serverless
import Serverless.Conn exposing (method, respond, route, textBody, jsonBody)
import Serverless.Conn.Request exposing (Method(..))
import UrlParser exposing ((</>), map, oneOf, s, string, top)
import Task exposing (attempt)
import Platform.Cmd as PCmd exposing (Cmd)
import Round exposing (..)


-- our deps

import PUBG.Call exposing (..)
import PUBG.API.Player as PUBGPlayer exposing (Player)
import PUBG.API.Match as PUBGMatch exposing (Match)
import Stats.Player exposing (..)


{-| This is the route parser demo.

We use a routing function as the endpoint, and provide a route parsing function.

-}
main : Serverless.Program () () Route () Msg
main =
    Serverless.httpApi
        { configDecoder = Serverless.noConfig
        , initialModel = ()
        , update = update
        , interop = Serverless.noInterop
        , requestPort = requestPort
        , responsePort = responsePort

        -- Parses the request path and query string into Elm data.
        -- If parsing fails, a 404 is automatically sent.
        , parseRoute =
            UrlParser.parseString <|
                oneOf
                    [ map Home top
                    , map Leaderboard (s "leaderboard")
                    , map PlayerRoute (s "player" </> string)
                    ]

        -- Entry point for new connections.
        , endpoint = router
        }


update : Msg -> Conn -> ( Conn, Cmd Msg )
update msg conn =
    case msg of
        NoOp ->
            ( conn, Cmd.none )

        PlayerMatchesFetched result ->
            case result of
                Ok playerMatches ->
                    let
                        jsonResult =
                            mapToLeaderboard playerMatches
                                |> encodeLeaderboard
                                |> jsonBody
                    in
                        respond ( 200, jsonResult ) conn

                Err httpError ->
                    respond ( 500, textBody <| "shit gone haywire\n" ++ (toString httpError) ) conn


mapToLeaderboard : List ( PUBGPlayer.Player, List PUBGMatch.Match ) -> LeaderBoard
mapToLeaderboard playerMatches =
    playerMatches
        |> List.map mapToPlayerStats


mapToPlayerStats : ( PUBGPlayer.Player, List PUBGMatch.Match ) -> PlayerStats
mapToPlayerStats ( player, matches ) =
    let
        playerName =
            player.attributes.name

        matchesPlayed =
            List.length player.relationships.matches.data

        tk =
            PUBGMatch.totalKills player.id matches

        kpm =
            Round.roundNum 2 <| (toFloat tk) / (toFloat matchesPlayed)
    in
        { player = playerName
        , totalKills = tk
        , amountOfMatchesPlayed = matchesPlayed
        , killsPerMatch = kpm
        }


type Msg
    = NoOp
    | PlayerMatchesFetched (Result String (List ( PUBGPlayer.Player, List PUBGMatch.Match )))


{-| Routes are represented using an Elm type.
-}
type Route
    = Home
    | Leaderboard
    | PlayerRoute String


{-| Just a big "case of" on the request method and route.

Remember that route is the request path and query string, already parsed into
nice Elm data, courtesy of the parseRoute function provided above.

-}
router : Conn -> ( Conn, Cmd Msg )
router conn =
    case
        ( method conn
        , route conn
        )
    of
        ( GET, Home ) ->
            respond ( 302, textBody "leaderboard" ) conn

        ( GET, Leaderboard ) ->
            conn ! [ createLeaderboard ]

        ( GET, PlayerRoute name ) ->
            respond ( 200, textBody <| (++) "More detailed stats for player: " name ) conn

        _ ->
            respond ( 405, textBody "Method not allowed" ) conn


createLeaderboard : Cmd Msg
createLeaderboard =
    Task.attempt PlayerMatchesFetched getPlayerMatches


{-| For convenience we defined our own Conn with arguments to the type parameters
-}
type alias Conn =
    Serverless.Conn.Conn () () Route ()


port requestPort : Serverless.RequestPort msg


port responsePort : Serverless.ResponsePort msg
