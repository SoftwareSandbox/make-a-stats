port module Stats.API exposing (..)

import Serverless
import Serverless.Conn exposing (method, respond, route, textBody)
import Serverless.Conn.Request exposing (Method(..))
import UrlParser exposing ((</>), map, oneOf, s, string, top)


{-| This is the route parser demo.

We use a routing function as the endpoint, and provide a route parsing function.

-}
main : Serverless.Program () () Route () ()
main =
    Serverless.httpApi
        { configDecoder = Serverless.noConfig
        , initialModel = ()
        , update = Serverless.noSideEffects
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
router : Conn -> ( Conn, Cmd msg )
router conn =
    case
        ( method conn
        , route conn
        )
    of
        ( GET, Home ) ->
            respond ( 302, textBody "leaderboard" ) conn

        ( GET, Leaderboard ) ->
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

        ( GET, Player name ) ->
            respond ( 200, textBody <| (++) "More detailed stats for player: " name ) conn

        _ ->
            respond ( 405, textBody "Method not allowed" ) conn


{-| For convenience we defined our own Conn with arguments to the type parameters
-}
type alias Conn =
    Serverless.Conn.Conn () () Route ()


port requestPort : Serverless.RequestPort msg


port responsePort : Serverless.ResponsePort msg
