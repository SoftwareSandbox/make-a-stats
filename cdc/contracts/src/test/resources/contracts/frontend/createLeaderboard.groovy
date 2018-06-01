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
    }
    response {
        status 201
        headers {
            header 'location', value(
                    p(regex('.+/leaderboard/[0-9]+$')),
                    c('http://localhost/leaderboard/1'))
        }
    }
}