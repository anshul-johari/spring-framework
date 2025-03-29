package com.anshul.helpProject.util;


import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseData<T> {
    private Object resource;
    private Boolean status;
    private String message;
    private Page<T> data;
    private String error;

}
