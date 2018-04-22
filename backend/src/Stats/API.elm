port module Stats.API exposing (..)

import Serverless
import Serverless.Conn exposing (method, respond, route, textBody, jsonBody)
import Serverless.Conn.Request exposing (Method(..))
import UrlParser exposing ((</>), map, oneOf, s, string, top)
import Http exposing (Request)
import Platform.Cmd as PCmd exposing (Cmd)
import Json.Encode exposing (encode, object, Value, null)


-- our deps

import PUBG.Call exposing (..)
import PUBG.API.Player as PUBGPlayer exposing (..)
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

        PlayersFetched result ->
            case result of
                Ok playerWrapper ->
                    respond ( 200, jsonBody <| encodePlayers <| mapToPlayers playerWrapper ) conn

                _ ->
                    respond ( 500, textBody "shit gone haywire" ) conn



--TODO: this should become the expected response in our frontend, and should be moved probably


type alias Player =
    {}



--TODO: transform the wrapper into a list of Player representations (expected response)


mapToPlayers : Wrapper PUBGPlayer.Player -> List Player
mapToPlayers wrapper =
    [ {} ]



--TODO: Make an encoder for our representation of a Player (expected response)


encodePlayers : List Player -> Value
encodePlayers players =
    object [ ( "snarf", null ) ]


type Msg
    = NoOp
    | PlayersFetched (Result Http.Error (Wrapper PUBGPlayer.Player))


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
            ( conn, fetchPlayers )

        ( GET, PlayerRoute name ) ->
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
