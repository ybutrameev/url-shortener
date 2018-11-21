import com.example.lv.Application;
import com.example.lv.repository.UrlRepository;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@EnableWebMvc
@AutoConfigureMockMvc
public class ApiTest {
    private static final String badUrl = "e.c";
    private static final String shortenUri = "/api/shorten";
    private static final String badIdUri = "/api/get/&--==+";
    private static final String customId = "custom";
    private static final String badCustomId = "/adl9--+--2=";
    private static final String blacklistName = "blacklist";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UrlRepository urlRepository;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shortenUrlAndRedirect() throws Exception {
        String inputJson = "{\"url\": \"example.com\"}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(shortenUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonOb = new JSONObject(content);
        String shortUrl = jsonOb.getString("shortUrl");

        mvcResult = mvc.perform(MockMvcRequestBuilders.get(shortUrl)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(302, status);

        String redirectedUrl = mvcResult.getResponse().getRedirectedUrl();
        assertEquals(redirectedUrl, "http://example.com");
    }

    @Test
    public void shortenUrlWithBadUrl() throws Exception {
        String inputJson = "{\"url\": \"" + badUrl + "\"}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(shortenUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void shortenUrlWithBadCustomId() throws Exception {
        String inputJson = "{\"url\": \"example.com\", \"customId\": \"" + badCustomId + "\"}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(shortenUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void shortenUrlWithAlreadyUsedCustomId() throws Exception {
        urlRepository.delete(customId);
        String inputJson = "{\"url\": \"example.com\", \"customId\": \"" + customId + "\"}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(shortenUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        mvcResult = mvc.perform(MockMvcRequestBuilders.post(shortenUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void shortenUrlWithIdInBlacklist() throws Exception {
        urlRepository.addIdInBlacklist(blacklistName);
        String inputJson = "{\"url\": \"example.com\", \"customId\": \"" + blacklistName + "\"}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(shortenUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);

        urlRepository.removeIdFromBlacklist("blacklist");
    }

    @Test
    public void shortenBadUrl() throws Exception {
        String badUrlJson = "{\"url\": \"&-475748\"}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(shortenUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(badUrlJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void useBadId() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(badIdUri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }
}