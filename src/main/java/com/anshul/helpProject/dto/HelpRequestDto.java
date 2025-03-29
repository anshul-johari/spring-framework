package com.anshul.helpProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpRequestDto {
	
	private String title;
	
	private String requestCode;
	
	private String discription;
	
	private String location;
	
	private String status;

}
