package be.swsb.makeastats.testcdc;

import be.swsb.makeastats.MakeAStatsApplication;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = MakeAStatsApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpBaseTest {

    @Inject
    private WebApplicationContext webApplicationContext;

    @Before
    public void before() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }
}