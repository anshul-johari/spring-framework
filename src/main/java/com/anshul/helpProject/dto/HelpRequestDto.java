package com.anshul.helpProject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpRequestDto {
	
	@NotBlank(message = "Title is mandatory.")
	private String title;
	
	@NotBlank(message = "Request Code is mandatory.")
	private String requestCode;
	
	@NotBlank(message = "Discription is mandatory.")
	private String discription;
	
	private String location;
	
	@NotBlank(message = "Status is mandatory.")
	private String status;

}
