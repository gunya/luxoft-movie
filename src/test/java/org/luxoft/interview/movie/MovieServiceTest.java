package org.luxoft.interview.movie;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luxoft.interview.movie.domain.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureWireMock
@SpringBootTest
public class MovieServiceTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String[] requestUrlArr = {"/movie/movieDetails/1","/movie/movieDetails/1,2", "/movie/movieDetails/1,2,3"};
    private List<List<ApiResponse>> responseList = new ArrayList<>();
    
    

    @Autowired
    private WebApplicationContext wac;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.options().dynamicPort());

    private String baseUrl;
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(this.wac).build();
        baseUrl = "http://localhost:" + wireMockRule.port();

        stubServices();
    }

    private void stubServices() throws Exception {
    	
    	prepareResonseList();
    	
    	for(int i=0; i<requestUrlArr.length; i++) {
    		stubService(requestUrlArr[i], responseList.get(i));
    	}
    }

	private void prepareResonseList() {
		// TODO Auto-generated method stub
		List<ApiResponse> resp = Arrays.asList(new ApiResponse("\"id\":\"1\",\"name\":\"Disney\" ", 200, 2));
		responseList.add(resp);
		resp = Arrays.asList(new ApiResponse("\"id\":\"1\",\"name\":\"Disney\" ", 200, 2), new ApiResponse("\"id\": \"2\", \"name\": \"Walle\"", 200, 3));
		responseList.add(resp);
		resp = Arrays.asList(new ApiResponse("\"id\":\"1\",\"name\":\"Disney\" ", 200, 2), new ApiResponse("\"id\": \"2\", \"name\": \"Walle\"", 200, 3), new ApiResponse("Failed: Couldn't find the record", 502, 4));
		responseList.add(resp);
	}

	private void stubService(String route, Object value) throws Exception {
        stubFor(get(urlEqualTo(route))
                .willReturn(aResponse()
                        .withFixedDelay(250)
                        .withHeader("Content-Type", "application/json")
                        .withBody(MAPPER.writeValueAsBytes(value))));
    }

    @Test
    public void happyFlow() throws Exception {
    	List<String> reqUrls = Arrays.stream(requestUrlArr).map(s -> baseUrl + s).collect(Collectors.toList());
    	
        MvcResult resultActions = mockMvc.perform(get(reqUrls.get(0))
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(resultActions))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.responses[0].status").value(200))
                .andExpect(jsonPath("$.duration", Matchers.lessThan(750))); //Should be around 500ms.
    }
}
