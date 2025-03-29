package com.anshul.helpProject.controller;

import java.util.ArrayList;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anshul.helpProject.dto.HelpRequestDto;
import com.anshul.helpProject.dto.HelpSearchRequest;
import com.anshul.helpProject.dto.HelpSearchResponse;
import com.anshul.helpProject.service.HelpRequestSearchService;
import com.anshul.helpProject.service.HelpRequestService;
import com.anshul.helpProject.util.Constants;
import com.anshul.helpProject.util.ResponseData;
import com.anshul.helpProject.util.SearchResponseData;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/")
@Slf4j
public class HelpRequestController {
	
	@Autowired
	private HelpRequestService helpRequestService;
	
	@Autowired
	private HelpRequestSearchService helpRequestSearchService;
	
	
	@PostMapping("helpRequest")
	@Operation(summary = "Create Help Request", description = "This endpoint creates a Help Request if already not exist using Code. All the field of request body is required.")
	public ResponseEntity<ResponseData> createHelpRequest(@Valid @RequestBody HelpRequestDto request, BindingResult bindingResult) {
		log.info("Inside HelpRequestController >> createHelpRequest");
		ResponseData responseData = new ResponseData();
		ResponseEntity<ResponseData> responseEntity = null;

		if (bindingResult.hasErrors()) {
			StringJoiner errorString = new StringJoiner(", ");
			bindingResult.getAllErrors().forEach(error -> {
			    errorString.add(error.getDefaultMessage());
			});
			responseData.setMessage(Constants.VALIDATION_ERROR_MESSAGE);
			responseData.setStatus(Constants.VALIDATION_ERROR);
			responseData.setData(new ArrayList<>());
			responseData.setError(errorString.toString());
			responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.BAD_REQUEST);

		} else {

			responseEntity = helpRequestService.saveHelpRequest(request);
		}

		return responseEntity;

	}
	
	
	@PutMapping("helpRequest/{requestId}")
	@Operation(summary = "Update Help Request", description = "This endpoint updates a Help Request if already exist. All the field of request body is required.")
	public ResponseEntity<ResponseData> updateHelpRequest(@PathVariable("requestId") @RequestParam (required = true) Integer requestId, @Valid @RequestBody HelpRequestDto request, BindingResult bindingResult) {
		log.info("Inside HelpRequestController >> updateHelpRequest ");
		ResponseData responseData = new ResponseData();
		ResponseEntity<ResponseData> responseEntity = null;

		if (bindingResult.hasErrors()) {
			StringJoiner errorString = new StringJoiner(", ");
			bindingResult.getAllErrors().forEach(error -> {
			    errorString.add(error.getDefaultMessage());
			});
			responseData.setMessage(Constants.VALIDATION_ERROR_MESSAGE);
			responseData.setStatus(Constants.VALIDATION_ERROR);
			responseData.setData(new ArrayList<>());
			responseData.setError(errorString.toString());
			responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.BAD_REQUEST);

		} else {

			responseEntity = helpRequestService.updateHelpRequest(request , requestId);
		}

		return responseEntity;

	}
	
	
	@GetMapping("helpRequest/{requestId}")
	@Operation(summary = "view Help Request", description = "This endpoint views a Help Request if already exist. All the field of request body is required.")
	public ResponseEntity<ResponseData> viewHelpRequest(@PathVariable("requestId") @RequestParam (required = true) Integer requestId) {
		log.info("Inside HelpRequestController >> viewHelpRequest ");
		ResponseData response = new ResponseData();
		ResponseEntity<ResponseData> responseEntity = null;

		responseEntity = helpRequestService.viewHelpRequest(requestId);

		return responseEntity;

	}
	
	
	@DeleteMapping("helpRequest/{requestId}")
	@Operation(summary = "delete Help Request", description = "This endpoint shows it deletes a Help Request if already exist. All the field of request body is required.")
	public ResponseEntity<ResponseData> deleteHelpRequest(@PathVariable("requestId") @RequestParam(required = true) Integer requestId) {
		log.info("Inside HelpRequestController >> deleteHelpRequest");
		ResponseData response = new ResponseData();
		ResponseEntity<ResponseData> responseEntity = null;

		responseEntity = helpRequestService.deleteHelpRequest(requestId);

		return responseEntity;

	}
	
	
	@GetMapping("helpRequests")
	@Operation(summary = "Get All Help Requests", description = "This endpoint shows All Help Requests.")
	public ResponseEntity<ResponseData> getAllHelpRequests() {
		log.info("Inside HelpRequestController >> getAllHelpRequests ");
		ResponseData response = new ResponseData();
		ResponseEntity<ResponseData> responseEntity = null;

		responseEntity = helpRequestService.getAllRequests();

		return responseEntity;

	}
	
	@PostMapping("/helpSearch")
	public ResponseEntity<SearchResponseData<HelpSearchResponse>> searchHelpRequest(
			@Valid @RequestBody HelpSearchRequest helpSearchRequest, BindingResult bindingResult) {
		log.info("Inside HelpRequestController >> searchHelpRequest ");
		SearchResponseData<HelpSearchResponse> response = new SearchResponseData<>();
		ResponseEntity<SearchResponseData<HelpSearchResponse>> responseEntity = null;

		if (bindingResult.hasErrors()) {
			StringBuilder errorString = new StringBuilder();
			bindingResult.getAllErrors().forEach(error -> {
				errorString.append(error.getDefaultMessage()).append(" ");
			});

			response.setMessage(Constants.VALIDATION_ERROR_MESSAGE);
			response.setStatus(false);
			response.setError(errorString.toString());
			responseEntity = new ResponseEntity<SearchResponseData<HelpSearchResponse>>(response, HttpStatus.BAD_REQUEST);
		} else {
			responseEntity = helpRequestSearchService.searchRequest(helpSearchRequest);
		}

		return responseEntity;
	}

}
