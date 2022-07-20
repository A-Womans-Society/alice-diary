package com.alice.project.domain;

import lombok.Getter;

@Getter
public enum ReportReason {
	BAD("비속어/욕설/불쾌한 표현"), LEAK("개인정보유출"), SPAM("스팸홍보/도배글"), ETC("기타");
	// 비속어/욕설/불쾌한 표현. 개인정보유출, 스팸홍보/도배글, 기타
	
	private final String description;
	
	ReportReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
