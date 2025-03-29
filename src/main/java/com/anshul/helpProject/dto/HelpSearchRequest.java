package com.anshul.helpProject.dto;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpSearchRequest {

	private String title;
	private String requestCode;
	private String location;
	private String status;
	private String sortBy;
	private String sortDirection;
	private Integer page = 0;
	@Max(value = 100, message = "Page Size cannot exceed 100")
	private Integer pageSize = 10;
	
}
