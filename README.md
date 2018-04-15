# Make a Stats
Application to view all kinds of PUBG related statistics for the Make A Stand discord gamers.

## Architecture
### Frontend
Statically generated site with [hugo](https://gohugo.io), containing 1 page with an embedded [VueJS](https://vuejs.org) component to consume and render a REST API, made accessible via AWS Lambda.

### Backend
[AWS Lambda](https://aws.amazon.com/lambda/) function(s) that consume the PUBG API, and might cache data on AWS RDS or whatever.

### Flow
![](docs/flow.png)

## Contributing
### Frontend
Install frontend dependencies:
```sh
npm install
```

Initialize frontend application locally:
```sh
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