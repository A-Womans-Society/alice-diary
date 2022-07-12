package com.alice.project.domain;

public enum Gender {
	MALE("Male"), FEMALE("Female"), UNKNOWN("Unknown");
	
	public String genderType;
	
	Gender(String genderType) {
		this.genderType = genderType;
	}
	
	public String getGenderType() {
		return genderType;
	}
}
