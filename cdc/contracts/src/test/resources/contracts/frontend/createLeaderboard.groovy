package frontend

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/api/leaderboard'
        body([
                'name'       : anyNonBlankString(),
                'playerNames': [
                        anyNonBlankString()
                ]
        ])
        headers {
            contentType("application/json")
        }
    }
    response {
        status 201
        headers {
            header 'location', value(
                    p(regex('.+/api/leaderboard/[0-9A-Za-z]+$')),
                    c('{{{ request.requestLine.baseUrl }}}/api/leaderboard/3kTMd4'))
        }
    }
}