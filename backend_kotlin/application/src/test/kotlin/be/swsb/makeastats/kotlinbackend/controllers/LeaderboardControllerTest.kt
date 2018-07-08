package be.swsb.makeastats.kotlinbackend.controllers

import be.swsb.makeastats.kotlinbackend.controllers.util.ObjectMapperFactory
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.CreateLeaderBoardCmd
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.Leaderboard
import be.swsb.makeastats.kotlinbackend.domain.leaderboard.LeaderboardTestBuilder.Companion.aLeaderboardWithoutPlayers
import be.swsb.makeastats.kotlinbackend.services.LeaderboardService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*
import org.assertj.core.api.Assertions as Ass

@RunWith(SpringRunner::class)
class LeaderboardControllerTest {

    private val leaderboardService: LeaderboardService = mock()

    private lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        val controller = LeaderboardController(leaderboardService)
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(MappingJackson2HttpMessageConverter(ObjectMapperFactory.instance()))
                .build()
    }

    @Test
    fun getLeaderboard_ServiceDidNotFindLeaderboardForGivenId_Returns404() {
        whenever(leaderboardService.getById("1")).thenReturn(null)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/leaderboard/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().`is`(404))
    }

    @Test
    fun getLeaderboard_ServiceDidFindLeaderboardForGivenId_Returns200() {
        val leaderboard = aLeaderboardWithoutPlayers(UUID.randomUUID(), "OvOTxT", "shroodSquad")
        whenever(leaderboardService.getById("OvOTxT")).thenReturn(leaderboard)

        val contentAsString: String = mockMvc.perform(MockMvcRequestBuilders.get("/api/leaderboard/{id}", "OvOTxT"))
                .andExpect(MockMvcResultMatchers.status().`is`(200))
                .andReturn().getResponse().getContentAsString()

        Ass.assertThat(ObjectMapperFactory.json(contentAsString, Leaderboard::class.java)).isEqualTo(leaderboard)
    }

    @Test
    fun createLeaderboard_ReturnsCreatedLeaderboard_WithLeaderboardIdInLocation() {
        val createLeaderBoardCmd = CreateLeaderBoardCmd("shroudSquad", setOf("shroud", "chad"))
        val leaderboard = aLeaderboardWithoutPlayers(UUID.randomUUID(), "OvOTxT", "shroodSquad")
        whenever(leaderboardService.handle(createLeaderBoardCmd)).thenReturn(leaderboard)

        val response = mockMvc
                .perform(MockMvcRequestBuilders
                    .post("/api/leaderboard")
                    .content(ObjectMapperFactory.json(createLeaderBoardCmd))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().`is`(201))
                .andReturn().getResponse()

        Ass.assertThat(response.getHeaderValue("Location")).isEqualTo("http://localhost/api/leaderboard/OvOTxT")
    }
}