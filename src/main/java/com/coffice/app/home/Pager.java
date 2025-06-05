package com.coffice.app.home;

import lombok.Data;

@Data
public class Pager {
	private String kind;
	private String search;
	
	public String getSearch() {
		if(this.search==null) {
			this.search="";
		}
		return this.search;
	}

}
