package com.coffice.app.ingredients;

import lombok.Data;

@Data
public class History {

	private Integer historyId;
	private boolean receive;
	private Integer historyPrice;
	private String userId;
}
