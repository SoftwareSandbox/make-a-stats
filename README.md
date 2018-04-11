# Make a Stats
Application to view all kinds of PUBG related statistics for the Make A Stand discord gamers.

## Architecture
### Frontend
Statically generated site with [hugo](https://gohugo.io), containing 1 page with an embedded [VueJS](https://vuejs.org) component to consume and render a REST API, made accessible via AWS Lambda.

### Backend
[AWS Lambda](https://aws.amazon.com/lambda/) function(s) that consume the PUBG API, and might cache data on AWS RDS or whatever.

### Flow
![](docs/flow.png)