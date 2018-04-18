# Make a Stats
Application to view all kinds of PUBG related statistics for the Make A Stand discord gamers.

## Architecture
### Frontend
Statically generated site with [hugo](https://gohugo.io), containing 1 page with an embedded [VueJS](https://vuejs.org) component to consume and render a REST API, made accessible via AWS Lambda.

### Backend
[AWS Lambda](https://aws.amazon.com/lambda/) function(s) that consume the PUBG API, and might cache data on AWS RDS or whatever.

### Backend Java
A (temporary?) java backend to consume the PUBG API.

### Flow
![](docs/flow.png)

## Contributing
### Frontend
Install frontend dependencies and run application locally:
```sh
cd make-a-stats/frontend
npm install
npm run serve
```

### Backend
Loosely basing this code on https://github.com/ktonon/elm-serverless-demo

#### Running locally

```bash
cd make-a-stats/backend
npm install
npm start
```
#### Things to know
[src/Stats/API.elm](src/Stats/API.elm) contains the source code that is loaded via the [src/Stats/api.js](src/Stats/api.js) JS bridge.

The JS Bridge is loaded via the [Serverless](https://serverless.com/) JS package.

#### Deploying from local
Setup `AWS_REGION`, `AWS_ACCESS_KEY_ID`, and `AWS_SECRET_ACCESS_KEY` in your environment (`aws configure`).

Make sure the `AWS_ACCESS_KEY_ID` maps to a user with [restricted privileges](https://github.com/serverless/serverless/issues/1439) that are sufficient for Serverless.

```bash
cd make-a-stats/backend
npm run deploy
```

You can also choose your aws profile to deploy with:

```bash
cd make-a-stats/backend
npm run deploy -- --profile <your local aws profile>
```

Read more about this configuration on the [Serverless docs site](https://serverless.com/framework/docs/providers/aws/cli-reference/config-credentials/).

### Backend Java
Build code:
```sh
cd make-a-stats/backend_java
./gradlew clean build
```

Setup local machine to connect to the pubg developer api:
- Place the property `development=<dev-api-key>` in a `.key/pubgapi.key` file in your user's home directory where `<dev-api-key>` is your api key retrieved from https://developer.playbattlegrounds.com
    - `cd ~`
    - `mkdir .key`
    - `nano .key/pubgapi.key`
    - add `development=<dev-api-key>`

Start application locally:
- Run main MakeAStatsApplication.java

Navigate to http://localhost:8888/backend/player/<player-name>  
NOTE: Currently, the request is very slow as we have to wait 6 seconds between each request to the pubg api. For one player with 20 matches played since 14 days, we have to make 1 player request and 20 match requests meaning we will have to wait 1 * 6 + 20 * 6 = 126 seconds. If 2 players are requested at the same time, errors will occur because of too many requests.
