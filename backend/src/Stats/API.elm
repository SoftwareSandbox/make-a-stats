port module Stats.API exposing (..)

import Serverless
import Serverless.Conn exposing (method, respond, route, textBody)
import Serverless.Conn.Request exposing (Method(..))
import UrlParser exposing ((</>), map, oneOf, s, string, top)
import Http exposing (Request)
import Platform.Cmd as PCmd exposing (Cmd)


-- our deps

import PUBG.Call exposing (..)
import PUBG.API.Player exposing (..)
import PUBG.API.Common exposing (..)


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
                    , map Player (s "player" </> string)
                    ]

        -- Entry point for new connections.
        , endpoint = router
        }


update : Msg -> Conn -> ( Conn, Cmd Msg )
update msg conn =
    case msg of
        NoOp ->
            ( conn, Cmd.none )

        PlayersFetched result ->
            respond ( 200, textBody """
            [
                {
                    "playerId": "account.2b95c68272fd467db565f5134277993b",
                    "name": "Jooones",
                    "kills": "8"
                },
                {
                    "playerId": "account.2b95c68272fd467db565f5134277993a",
                    "name": "Hahawin",
                    "kills": "5"
                },
                {
                    "playerId": "account.2b95c68272fd467db565f5134277993c",
                    "name": "Daxude",
                    "kills": "6"
                },
                {
                    "playerId": "account.2b95c68272fd467db565f5134277993f",
                    "name": "Sch3lp",
                    "kills": "0"
                }
            ]
            """ ) conn


type Msg
    = NoOp
    | PlayersFetched (Result Http.Error (Wrapper Player))


{-| Routes are represented using an Elm type.
-}
type Route
    = Home
    | Leaderboard
    | Player String


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
            ( conn, fetchPlayers )

        ( GET, Player name ) ->
            respond ( 200, textBody <| (++) "More detailed stats for player: " name ) conn

        _ ->
            respond ( 405, textBody "Method not allowed" ) conn


fetchPlayers : Cmd Msg
fetchPlayers =
    Http.send PlayersFetched getPlayers


{-| For convenience we defined our own Conn with arguments to the type parameters
-}
type alias Conn =
    Serverless.Conn.Conn () () Route ()


port requestPort : Serverless.RequestPort msg


port responsePort : Serverless.ResponsePort msg
