package com.alice.project.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Alert {
	String word = "";
	String href = "";
	
	public Alert(String word, String href) {
		this.word = word;
		this.href = href;
	}
	
//	public Alert(String word) {
//		this.word = word;
//	}
	
	
}
