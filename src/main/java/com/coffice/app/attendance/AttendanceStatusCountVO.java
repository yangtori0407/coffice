package com.coffice.app.attendance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceStatusCountVO {

	    private String status; // 근태 상태: 정상근무, 조퇴, 결근 등
	    private int count;     // 각 상태별 횟수
	
}
