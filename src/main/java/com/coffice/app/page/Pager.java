package com.coffice.app.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pager {

	// 가져올 개수
	private Long page;
	// 한 페이지에 출력할 페이지 번호의 개수
	private Long perBlock;
	// 현재 페이지
	private Long nowPage;
	// 시작번호
	private Long startNum;

	private Long start;
	private Long end;

	private Boolean endCheck = false;

	private String search;
	private String kind;

	public String getSearch() {
		if (this.search == null) {
			this.search = "";
		}

		return search;
	}

	public String getKind() {
		if (this.kind == null) {
			this.kind = "k1";
		}

		return kind;
	}

	public Long getNowPage() {
		if (this.nowPage == null || this.nowPage < 1) {
			this.nowPage = 1L;
		}

		return nowPage;
	}

	public Long getPage() {
		if (this.page == null) {
			this.page = 10L;
		}

		return page;
	}

	public Long getStartNum() {
		if (this.startNum == null || this.startNum < 0) {
			this.startNum = 0L;
		}

		return startNum;
	}

	public Long getPerBlock() {
		if (this.perBlock == null) {
			this.perBlock = 5L;

		}

		return perBlock;
	}

	public void make() {
		this.startNum = (getNowPage() - 1) * getPage();
	}

	public void makeNum(Long totalCount) {

		// 1. totalpage 계산
		Long totalPage = totalCount / getPage();

		if (totalCount % getPage() != 0) {
			totalPage++;
		}

		// 2. totalBlock 계산
		Long totalBlock = totalPage / getPerBlock();
		if (totalPage % getPerBlock() != 0) {
			totalBlock++;
		}

		// 3. page 번호로 현재 Block 번호
		Long culBlock = this.getNowPage() / getPerBlock();
		if (this.getNowPage() % getPerBlock() != 0) {
			culBlock++;
		}

		// 4. Block번호를 이용해서 시작, 끝 번호 계산
		this.start = (culBlock - 1) * getPerBlock() + 1;
		this.end = culBlock * getPerBlock();

		// 5. 현재 block이 마지막 block인가?
		if (totalBlock == culBlock) {
			this.setEnd(totalPage);
			this.setEndCheck(true);
		}
	}
}
