# Flow
## New Leaderboard gets created in an empty database
Sch3lp wants to compare himself to the people he plays with and 

* **creates a Leaderboard** called _MakeAStand_, 
* **with players** _Sch3lp_, _Jooones_, _Daxude_, _hahawin_, _Evsx_, _ImpulseBE_ and _sch3lm_

A Leaderboard with name _MakeAStand_ is created, 
and a newly generated hashcoded LeaderboardId is given: _vX9uiP_
7 players are created with names _Sch3lp_, _Jooones_, _Daxude_, _hahawin_, _Evsx_, _ImpulseBE_ and _sch3lm_

All those players are assigned to the _MakeAStand_ Leaderboard.

For every player, match info is being retrieved via the PUBG API.

He gets redirected to a url with a hashcoded LeaderboardId: _vX9uiP_, where he can see his Leaderboard with the given playernames.

Since not all match info has been retrieved for all the players yet, let's say only for _Sch3lp_ and _Jooones_, it will look like this:

| Place | Player | Kills | Matches played | Average |  Last Update   |
| ---  | ---    | ---   | ---            | ---     | ---       |
| 1 | Sch3lp | 12 | 2 | 6 | 2018/06/21 18:06 |
| 2 | Jooones | 20 | 1 | 20 | 2018/06/21 18:06 |
| 3 | Daxude | 0 | 0 | 0 | Updating |
| 4 | hahawin | 0 | 0 | 0 | Updating |
| 5 | Evsx | 0 | 0 | 0 | Updating |
| 6 | ImpulseBE | 0 | 0 | 0 | Updating |
| 7 | sch3lm | 0 | 0 | 0 | Updating |

## Another Leaderboard gets created
Evsx decides to try out the app and creates a Leaderboard just for himself.

* **creates a Leaderboard** called _SoloSquad_,
* **with players** _Evsx_

A Leaderboard with name _SoloSquad_ is created, 
and a newly generated hashcoded LeaderboardId is given: _FyFaEN_

1 player is attempted to be created with name _Evsx_, but this player is already known by the application (already in db).

Player _Evsx_ is assigned to the _SoloSquad_ Leaderboard.

For _Evsx_, match info is being retrieved via the PUBG API.

He gets redirected to a url with a hashcoded LeaderboardId: _FyFaEN_, where he can see his Leaderboard with just himself in it.

Since his match info in the meanwhile already has been retrieved with the creation of the _MakeAStand_ Leaderboard,
his leaderboard will look like this:

| Place | Player | Kills | Matches played | Average |  Last Update   |
| ---  | ---    | ---   | ---            | ---     | ---       |
| 1 | Evsx | 15 | 3 | 5 | 2018/06/21 18:07 (Updating) |

## An existing Leaderboard is requested
Sch3lp refreshes his page to see if things are updated yet.

All match info is done and the page now looks like this:

| Place | Player | Kills | Matches played | Average |  Last Update   |
| ---  | ---    | ---   | ---            | ---     | ---       |
| 1 | Sch3lp | 12 | 2 | 6 | 2018/06/21 18:06 |
| 2 | Jooones | 20 | 1 | 20 | 2018/06/21 18:06 |
| 3 | Daxude | 50 | 10 | 5 | 2018/06/21 18:07 |
| 4 | hahawin | 18 | 2 | 9 | 2018/06/21 18:07 |
| 5 | Evsx | 15 | 3 | 5 | 2018/06/21 18:07 |
| 6 | ImpulseBE | 0 | 10 | 0 | 2018/06/21 18:07 |
| 7 | sch3lm | 16 | 4 | 4 | 2018/06/21 18:08 |

