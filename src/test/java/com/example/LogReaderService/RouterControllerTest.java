package com.example.LogReaderService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.LogReaderService.Services.LogService;

@WebMvcTest(RouterController.class)
class RouterControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private LogService mockLogService;

	@Test
	void getLog_happyCase() throws Exception {

		final List<String> testSample = new ArrayList<String>();
		testSample.add("installed on today.");

		Mockito.when(
			mockLogService.readFile("/var/logs/install.log", "installed", 10))
		.thenReturn(testSample);

		mockMvc.perform(MockMvcRequestBuilders.get("/getlog")
		.param("filename", "install.log")
		.param("searchword", "installed")
		.param("limit", "10").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

}
