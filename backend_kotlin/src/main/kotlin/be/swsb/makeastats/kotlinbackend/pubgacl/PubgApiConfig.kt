package be.swsb.makeastats.kotlinbackend.pubgacl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class PubgApiConfig(@Value("\${pubgapi.baseurl}") val baseUrl: BaseUrl,
                         @Value("\${pubgapi.apikey}")  val apiKey: ApiKey) {
}

typealias BaseUrl= String
typealias ApiKey = String
