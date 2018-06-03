package be.swsb.makeastats.testcdc

import be.swsb.makeastats.kotlinbackend.MakeAStatsApplication
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.context.WebApplicationContext
import javax.inject.Inject

@RunWith(SpringRunner::class)
@SpringBootTest(
        classes = arrayOf(MakeAStatsApplication::class),
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
open class HttpBaseTest {

    @Inject
    private val webApplicationContext: WebApplicationContext? = null

    @Before
    fun before() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext)
    }
}