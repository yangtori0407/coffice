package com.coffice.app.gpt;

import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiService {

	private final WebClient webclient;
	@Value("${open.ai.url}")
	private String geminiurl;
	@Value("${open.ai.key}")
	private String geminikey;
	
	public String getDescription(String prompt) {
		String api = geminiurl+geminikey;
		
		String text = prompt+"100자내로 알려줘";
		
		GeminiReqVO request = new GeminiReqVO();
		request.createGeminiReqDto(text);
		
		GeminiResVO response = webclient.post()
								.uri(api)
								.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.bodyValue(request)
								.retrieve()
								.bodyToMono(GeminiResVO.class)
								.block()
								;
								
		return response.getCandidates().get(0).getContent().getParts().get(0).getText();
	}
	
	public String getQuote(String prompt) {
		String api = geminiurl+geminikey;
		
		String text = prompt+"님에게 맞는 명언을 한줄로 남겨줘";
		
		GeminiReqVO request = new GeminiReqVO();
		request.createGeminiReqDto(text);
		
		GeminiResVO response = webclient.post()
								.uri(api)
								.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.bodyValue(request)
								.retrieve()
								.bodyToMono(GeminiResVO.class)
								.block()
								;
								
		return response.getCandidates().get(0).getContent().getParts().get(0).getText();
	}
}
