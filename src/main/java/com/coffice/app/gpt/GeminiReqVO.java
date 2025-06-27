package com.coffice.app.gpt;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiReqVO {

	 private List<Content> contents;

	    public void createGeminiReqDto(String text) {
	        this.contents = new ArrayList<>();
	        Content content = new Content(text);
	        this.contents.add(content);
	    }

	    @Data
	    public static class Content {
	        private List<Part> parts;

	        public Content(String text) {
	            this.parts = new ArrayList<>();
	            this.parts.add(new Part(text));
	        }

	        @Data
	        @NoArgsConstructor
	        @AllArgsConstructor
	        public static class Part {
	            private String text;
	        }
	    }
}
