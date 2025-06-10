package com.coffice.app.ingredients;

import lombok.Data;

@Data
public class History {

	private Integer historyId;
	private boolean receive;
	private Integer number;
	private String userId;
	private Integer ingredientsID;
}
