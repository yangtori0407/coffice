package com.coffice.app.gpt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

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
		
		String text = prompt+"50자내로 알려줘";
		
		GeminiReqVO request = new GeminiReqVO();
		request.createGeminiReqDto(text);
		try {
		GeminiResVO response = webclient.post()
								.uri(api)
								.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.bodyValue(request)
								.retrieve()
								.onStatus(status -> status.is4xxClientError(), clientResponse -> {
					                if (clientResponse.statusCode() == HttpStatus.TOO_MANY_REQUESTS) {
					                    return Mono.error(new RuntimeException("429: 요청이 너무 많습니다. 잠시 후 다시 시도하세요."));
					                }
					                return Mono.error(new RuntimeException("클라이언트 오류 발생: " + clientResponse.statusCode()));
					            })
								.bodyToMono(GeminiResVO.class)
								.block()
								;
								
		return response.getCandidates().get(0).getContent().getParts().get(0).getText();
		} catch(Exception e) {
			e.printStackTrace();
			return "지금은 gpt를 이용할수 없습니다. 잠시후 다시 시도해주세요.";
		}
	}
	
	public String getQuote(String prompt) {
		String api = geminiurl+geminikey;
		
		String text = prompt+"님에게 맞는 명언을 한줄로 남겨줘";
		
		GeminiReqVO request = new GeminiReqVO();
		request.createGeminiReqDto(text);
		try {
		GeminiResVO response = webclient.post()
								.uri(api)
								.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.bodyValue(request)
								.retrieve()
								.onStatus(status -> status.is4xxClientError(), clientResponse -> {
					                if (clientResponse.statusCode() == HttpStatus.TOO_MANY_REQUESTS) {
					                    return Mono.error(new RuntimeException("429: 요청이 너무 많습니다. 잠시 후 다시 시도하세요."));
					                }
					                return Mono.error(new RuntimeException("클라이언트 오류 발생: " + clientResponse.statusCode()));
					            })
					            .onStatus(status -> status.is4xxClientError(), clientResponse -> {
					                return Mono.error(new RuntimeException("서버 오류 발생: " + clientResponse.statusCode()));
					            })
								.bodyToMono(GeminiResVO.class)
								.block()
								;
								
		return response.getCandidates().get(0).getContent().getParts().get(0).getText();
		}catch(Exception e) {
			return "지금은 명언을 가져올수 없습니다. 잠시후 다시 시도해주세요";
		}
	}
}
