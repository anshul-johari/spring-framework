package com.anshul.helpProject.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
	
	    private Object resource;
	    private String status;
	    private String message;
	    private Object data;
	    private String error;

}
