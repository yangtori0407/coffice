package com.coffice.app.ingedients;

import java.sql.Date;

import lombok.Data;

@Data
public class IngedientsVO {

	private Integer ingedientsID;
	private String ingedientsName;
	private Integer ingedientsStock;
	private Integer ingedientsPrice;
	private Date ingedientsDate;
	private Integer historyId;
}
