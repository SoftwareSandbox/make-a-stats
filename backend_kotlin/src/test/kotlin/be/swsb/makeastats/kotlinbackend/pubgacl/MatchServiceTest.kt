package be.swsb.makeastats.kotlinbackend.pubgacl

import be.swsb.makeastats.kotlinbackend.objectMapper
import org.assertj.core.api.Assertions
import org.junit.Test

class MatchServiceTest {

    val matchService: MatchService = MatchService(PubgApiConfig("http://localhost:3333/pubg-stub",""), objectMapper())

    @Test
    fun canCallAndMap() {
        val model = matchService.findMatchById("58d1b02c-b331-40d9-bdd8-0cc56f0b030f")
                .test()
                .apply { awaitTerminalEvent() }
                .assertNoErrors()
                .assertValueCount(1)
                .assertComplete()
                .values()[0]

        Assertions.assertThat(model).isNotNull()
    }
}