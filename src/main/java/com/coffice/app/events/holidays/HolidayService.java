package com.coffice.app.events.holidays;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HolidayService {
	
	@Value("${calendar.api.key}")
	private String apiKey;
	
	@Autowired
	private HolidayDAO holidayDAO;
	
	public List<HolidayVO> getHolidays() throws Exception {
		return holidayDAO.getHolidays();
	}
	
	public int addHoliday(int year) throws Exception {
		List<HolidayVO> list = this.holidaySetting(year);
		int result = 0;
		for(HolidayVO vo : list) {
			result = result + holidayDAO.addHoliday(vo);
		}
		return result;
	}
	
	public List<HolidayVO> holidaySetting(int year) throws Exception {
		
		WebClient webClient = WebClient.builder()
									   .baseUrl("http://apis.data.go.kr")
									   .build();
		String response = webClient.get()
												.uri(uriBuilder -> 
														uriBuilder.path("/B090041/openapi/service/SpcdeInfoService/getRestDeInfo")
																  .queryParam("serviceKey", apiKey)
																  .queryParam("numOfRows", 100)
																  .queryParam("_type", "json")
																  .queryParam("solYear", year)
																  .build())
												.retrieve()
												.bodyToMono(String.class)
												.block();
		
		log.info("response : {}", response);
		String result = response.substring(response.toString().indexOf("items")+15, response.toString().indexOf("numOf")-3);
		log.info("result : {}", result);
		log.info("{}", result);
		
		ObjectMapper mapper = new ObjectMapper();
		List<HolidayVO> list = mapper.readValue(result, new TypeReference<List<HolidayVO>>() {});
		log.info("list : {}", list);
		return list;
	}

}
