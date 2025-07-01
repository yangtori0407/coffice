package com.coffice.app.events.holidays;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableScheduling
public class HolidayService {
	
	@Value("${calendar.api.key}")
	private String apiKey;
	
	@Autowired
	private HolidayDAO holidayDAO;
	
	public List<HolidayVO> getHolidays() throws Exception {
		return holidayDAO.getHolidays();
	}
	
	@Scheduled(cron = "0 0 0 * * *")
	public int updateHoliday() throws Exception {
		Calendar calendar = Calendar.getInstance();
		int c = calendar.get(Calendar.YEAR)-1;
		int result = 0;
		result = holidayDAO.deleteAll();
		log.info("deleteHolidays : {}", result);
		result = 0;
		for(int i = 0; i < 3; i++) {
			result = result + this.addHoliday(c+i);
		}
		log.info("updateHolidays : {}", result);
		
		return result;
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
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		List<HolidayVO> list = mapper.readValue(result, new TypeReference<List<HolidayVO>>() {});
		log.info("list : {}", list);
		
		// 날짜 변환 (예: YYYYMMDD → LocalDate)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		for (HolidayVO vo : list) {
		    LocalDate date = LocalDate.parse(vo.getLocdate(), formatter);
		}
		
		return list;
	}

}
