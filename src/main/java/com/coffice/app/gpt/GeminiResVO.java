package com.coffice.app.gpt;

import java.util.List;

import lombok.Data;

@Data
public class GeminiResVO {
	private List<Candidate> candidates;

    @Data
    public static class Candidate {
        private Content content;
        private String finishReason;
    }

    @Data
    public static class Content {
        private List<Parts> parts;
        private String role;
    }

    @Data
    public static class Parts {
        private String text;
    }
}
