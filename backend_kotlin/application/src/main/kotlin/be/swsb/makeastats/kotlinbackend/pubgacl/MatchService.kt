package be.swsb.makeastats.kotlinbackend.pubgacl

import be.swsb.makeastats.kotlinbackend.pubgacl.model.Match
import be.swsb.makeastats.kotlinbackend.pubgacl.model.PubgApiWrapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.result.Result
import io.reactivex.Single
import org.springframework.stereotype.Service
import java.io.Reader

@Service
class MatchService(val pubgApiConfig: PubgApiConfig,
                   val objectMapper: ObjectMapper) {

    fun findMatchById(matchId: String): Single<Result<PubgApiWrapper<Match>, FuelError>> {
        return Fuel.get("${pubgApiConfig.baseUrl}/matches/${matchId}")
                .header(mapOf(
                "Authorization" to "Bearer: ${pubgApiConfig.apiKey}",
                "Accept" to "application/vnd.api+json"
        )).rx_object(MatchDeserializer(objectMapper))
    }

    class MatchDeserializer(private val objectMapper: ObjectMapper): ResponseDeserializable<PubgApiWrapper<Match>> {
        override fun deserialize(reader: Reader): PubgApiWrapper<Match> {
            return objectMapper.readValue(reader)
        }
    }
}