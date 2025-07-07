package com.coffice.app.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class CalendarEventDTO {
	
	private String id;
    private String title;
    private String start; // ISO-8601
    private String end;
    private Boolean allDay;
    private String color;
    private Boolean editable;
    private Map<String, Object> extendedProps = new HashMap<>();

    // 반복일정용
    private Map<String, Object> rrule;
    private List<String> exdate;
    private String duration;
    private String groupId;

}
