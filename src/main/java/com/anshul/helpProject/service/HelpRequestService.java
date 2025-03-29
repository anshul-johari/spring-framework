package com.anshul.helpProject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anshul.helpProject.dto.HelpRequestDto;
import com.anshul.helpProject.entity.HelpRequestEntity;
import com.anshul.helpProject.repository.HelpRequestRepository;
import com.anshul.helpProject.util.Constants;
import com.anshul.helpProject.util.ResponseData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HelpRequestService {

	@Autowired
	private HelpRequestRepository helpRequestRepository;
	
	
	public ResponseEntity<ResponseData> saveHelpRequest(HelpRequestDto helpRequestDto){
		log.info("Inside HelpRequestService >> saveHelpRequest");
		ResponseEntity<ResponseData> responseEntity = null;
		ResponseData responseData = new ResponseData();
		Map<String, Integer> helpRequestData = new HashMap<>();
		
		try {
			
			if(Objects.nonNull(helpRequestDto)) {
				
				Optional<HelpRequestEntity> helpRequestEntityOptional = helpRequestRepository.findByRequestCode(helpRequestDto.getRequestCode());
				
				if(helpRequestEntityOptional.isPresent()) {
					
					responseData.setMessage(Constants.ALREADY_AVAILABLE_CODE);
					responseData.setStatus(Constants.ALREADY_AVAILABLE_STATUS);
					responseData.setData(new ArrayList<>());
					responseData.setResource(new ArrayList<>());
					responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.CONFLICT);
					
				}else {
					
					HelpRequestEntity helpRequestEntity = new HelpRequestEntity();
					
					helpRequestEntity.setTitle(helpRequestDto.getTitle());
					helpRequestEntity.setRequestCode(helpRequestDto.getRequestCode());
					helpRequestEntity.setDiscription(helpRequestDto.getDiscription());
					helpRequestEntity.setLocation(helpRequestDto.getLocation());
					helpRequestEntity.setStatus(helpRequestDto.getStatus());
					
		            
					helpRequestEntity.setIsDeleted(false);
					
					helpRequestEntity.setCreatedBy("DEV");
					helpRequestEntity.setCreatedOn(LocalDateTime.now());
					
					helpRequestRepository.save(helpRequestEntity);
					
					helpRequestData.put("requestId", helpRequestEntity.getId());
					
					responseData.setMessage(Constants.REQUEST_REGISTERED_SUCCESS);
					responseData.setStatus(Constants.SUCCESS);
					responseData.setData(helpRequestData);
					responseData.setResource(new ArrayList<>());
					responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.CREATED);
					
				}
				
			}
			else {
				responseData.setMessage(Constants.DATA_NOT_FOUND_MESSAGE);
				responseData.setStatus(Constants.SUCCESS);
				responseData.setData(new ArrayList<>());
				responseData.setResource(new ArrayList<>());
				responseEntity = new ResponseEntity<ResponseData>(responseData , HttpStatus.BAD_REQUEST);
			}
			
		}catch(Exception e){
			log.info("Inside HelpRequestService >> saveHelpRequest  >> exception {}", e);
			responseData.setMessage(Constants.ERROR_MESSAGE);
			responseData.setStatus(Constants.ERROR);
			responseData.setError(e.getMessage());
			responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return responseEntity;
	}
	

	
	
	
	public ResponseEntity<ResponseData> updateHelpRequest(HelpRequestDto helpRequestDto, Integer requestId){
		log.info("Inside HelpRequestService >> updateHelpRequest");
		ResponseEntity<ResponseData> responseEntity = null;
		ResponseData responseData = new ResponseData();
		Map<String, Integer> helpRequestData = new HashMap<>();
		
		try {
			
			if(Objects.nonNull(helpRequestDto) && Objects.nonNull(requestId)) {
				
				Optional<HelpRequestEntity> helpRequestEntityOptional = helpRequestRepository.findById(requestId);
				
				if(helpRequestEntityOptional.isPresent()) {

					HelpRequestEntity helpRequestEntity = helpRequestEntityOptional.get();
					
					
					if(!helpRequestEntity.getIsDeleted()) {
						
						helpRequestEntity.setTitle(helpRequestDto.getTitle());
						helpRequestEntity.setRequestCode(helpRequestDto.getRequestCode());
						helpRequestEntity.setDiscription(helpRequestDto.getDiscription());
						helpRequestEntity.setLocation(helpRequestDto.getLocation());
						helpRequestEntity.setStatus(helpRequestDto.getStatus());
						
						
						helpRequestEntity.setUpdatedBy("DEV");
						helpRequestEntity.setUpdatedOn(LocalDateTime.now());
						
						helpRequestRepository.save(helpRequestEntity);
						
						helpRequestData.put("requestId", requestId);
						
						responseData.setMessage(Constants.SUCCESS_MESSAGE);
						responseData.setStatus(Constants.SUCCESS);
						responseData.setData(helpRequestData);
						responseData.setResource(new ArrayList<>());
						responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.CREATED);
						
					}else {
						log.info("Inside HelpRequestService >> updateHelpRequest  >> request deleted previouly but not shown.");
						responseData.setMessage(Constants.REQUEST_NOT_PRESENT);
						responseData.setStatus(Constants.DATA_NOT_FOUND_MESSAGE);
						responseData.setData(new ArrayList<>());
						responseData.setResource(new ArrayList<>());
						responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.BAD_REQUEST);
						
					}
					
					
					
				}else {
					responseData.setMessage(Constants.REQUEST_NOT_PRESENT);
					responseData.setStatus(Constants.DATA_NOT_FOUND_MESSAGE);
					responseData.setData(new ArrayList<>());
					responseData.setResource(new ArrayList<>());
					responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.BAD_REQUEST);
				}
				
			}
			else {
				responseData.setMessage(Constants.DATA_NOT_FOUND_MESSAGE);
				responseData.setStatus(Constants.SUCCESS);
				responseData.setData(new ArrayList<>());
				responseData.setResource(new ArrayList<>());
				responseEntity = new ResponseEntity<ResponseData>(responseData , HttpStatus.BAD_REQUEST);
			}
			
		}catch(Exception e){
			log.info("Inside HelpRequestService >> updateHelpRequest  >> exception {}", e);
			responseData.setMessage(Constants.ERROR_MESSAGE);
			responseData.setStatus(Constants.ERROR);
			responseData.setError(e.getMessage());
			responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return responseEntity;
	}
	
	
	
	public ResponseEntity<ResponseData> viewHelpRequest(Integer requestId){
		log.info("Inside HelpRequestService >> viewHelpRequest");
		ResponseEntity<ResponseData> responseEntity = null;
		ResponseData responseData = new ResponseData();
		
		try {
			
			if(Objects.nonNull(requestId)) {
				
				Optional<HelpRequestEntity> helpRequestEntityOptional = helpRequestRepository.findById(requestId);
				
				if(helpRequestEntityOptional.isPresent()) {
					
					HelpRequestEntity helpRequestEntity = helpRequestEntityOptional.get();
					
					if(!helpRequestEntity.getIsDeleted()) {
						
						HelpRequestDto helpRequestDto = new HelpRequestDto();

						helpRequestDto.setTitle(helpRequestEntity.getTitle());
						helpRequestDto.setRequestCode(helpRequestEntity.getRequestCode());
						helpRequestDto.setDiscription(helpRequestEntity.getDiscription());
						helpRequestDto.setLocation(helpRequestEntity.getLocation());
						helpRequestDto.setStatus(helpRequestEntity.getStatus());

						
						responseData.setMessage(Constants.SUCCESS_MESSAGE);
						responseData.setStatus(Constants.SUCCESS);
						responseData.setData(helpRequestDto);
						responseData.setResource(new ArrayList<>());
						responseEntity = new ResponseEntity<>(responseData, HttpStatus.OK);
						
					}else {
						log.info("Inside HelpRequestService >> viewHelpRequest  >> request deleted previouly but not shown.");
						responseData.setMessage(Constants.REQUEST_NOT_PRESENT);
						responseData.setStatus(Constants.DATA_NOT_FOUND_MESSAGE);
						responseData.setData(new ArrayList<>());
						responseData.setResource(new ArrayList<>());
						responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.BAD_REQUEST);
					}
					
					
					
				}else {
					responseData.setMessage(Constants.REQUEST_NOT_PRESENT);
					responseData.setStatus(Constants.DATA_NOT_FOUND_MESSAGE);
					responseData.setData(new ArrayList<>());
					responseData.setResource(new ArrayList<>());
					responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.BAD_REQUEST);
				}
				
			}
			else {
				responseData.setMessage(Constants.DATA_NOT_FOUND_MESSAGE);
				responseData.setStatus(Constants.SUCCESS);
				responseData.setData(new ArrayList<>());
				responseData.setResource(new ArrayList<>());
				responseEntity = new ResponseEntity<ResponseData>(responseData , HttpStatus.BAD_REQUEST);
			}
			
		}catch(Exception e){
			log.info("Inside HelpRequestService >> viewHelpRequest  >> exception {}", e);
			responseData.setMessage(Constants.ERROR_MESSAGE);
			responseData.setStatus(Constants.ERROR);
			responseData.setError(e.getMessage());
			responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return responseEntity;
	}
	
	
	public ResponseEntity<ResponseData> deleteHelpRequest(Integer requestId){
		log.info("Inside HelpRequestService >> deleteHelpRequest");
		ResponseEntity<ResponseData> responseEntity = null;
		ResponseData responseData = new ResponseData();
		
		try {
			
			if(Objects.nonNull(requestId)) {
				
				Optional<HelpRequestEntity> helpRequestEntityOptional = helpRequestRepository.findById(requestId);
				
				if(helpRequestEntityOptional.isPresent()) {
					
					HelpRequestEntity helpRequestEntity = helpRequestEntityOptional.get();
					
					if(!helpRequestEntity.getIsDeleted()) {
						
						helpRequestEntity.setIsDeleted(true);
						helpRequestRepository.save(helpRequestEntity);
						
						Map<String, Integer> deleteResponse = new HashMap<>();
						deleteResponse.put("requestId", requestId);
						
						responseData.setMessage(Constants.DELETE_MESSAGE);
						responseData.setStatus(Constants.SUCCESS);
						responseData.setData(deleteResponse);
						responseData.setResource(new ArrayList<>());
						responseEntity = new ResponseEntity<>(responseData, HttpStatus.OK);
						
					}else {
						responseData.setMessage(Constants.REQUEST_NOT_PRESENT);
						responseData.setStatus(Constants.DATA_NOT_FOUND_MESSAGE);
						responseData.setData(new ArrayList<>());
						responseData.setResource(new ArrayList<>());
						responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.BAD_REQUEST);
					}
					
				}else {
					responseData.setMessage(Constants.REQUEST_NOT_PRESENT);
					responseData.setStatus(Constants.DATA_NOT_FOUND_MESSAGE);
					responseData.setData(new ArrayList<>());
					responseData.setResource(new ArrayList<>());
					responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.BAD_REQUEST);
				}
				
			}
			else {
				responseData.setMessage(Constants.DATA_NOT_FOUND_MESSAGE);
				responseData.setStatus(Constants.SUCCESS);
				responseData.setData(new ArrayList<>());
				responseData.setResource(new ArrayList<>());
				responseEntity = new ResponseEntity<ResponseData>(responseData , HttpStatus.BAD_REQUEST);
			}
			
		}catch(Exception e){
			log.info("Inside HelpRequestService >> deleteHelpRequest  >> exception {}", e);
			responseData.setMessage(Constants.ERROR_MESSAGE);
			responseData.setStatus(Constants.ERROR);
			responseData.setError(e.getMessage());
			responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return responseEntity;
	}
	
	
	
	public ResponseEntity<ResponseData> getAllRequests(){
		log.info("Inside HelpRequestService >> getAllRequests");
		ResponseEntity<ResponseData> responseEntity = null;
		ResponseData responseData = new ResponseData();
		
		try {
			
			List<HelpRequestEntity> helpRequestEntities = helpRequestRepository.findAll();
			
			if(helpRequestEntities.size() > 0) {
				
				List<HelpRequestDto> helpRequestDtos = new ArrayList<>(); 
				
				for(HelpRequestEntity helpRequestEntity : helpRequestEntities) {
					
					if(!helpRequestEntity.getIsDeleted()) {
						
						HelpRequestDto helpRequestDto = new HelpRequestDto();

						helpRequestDto.setTitle(helpRequestEntity.getTitle());
						helpRequestDto.setRequestCode(helpRequestEntity.getRequestCode());
						helpRequestDto.setDiscription(helpRequestEntity.getDiscription());
						helpRequestDto.setLocation(helpRequestEntity.getLocation());
						helpRequestDto.setStatus(helpRequestEntity.getStatus());
						
						helpRequestDtos.add(helpRequestDto);
						
					}
					
					
					
				}
				
				responseData.setMessage(Constants.SUCCESS_MESSAGE);
				responseData.setStatus(Constants.SUCCESS);
				responseData.setData(helpRequestDtos);
				responseData.setResource(new ArrayList<>());
				responseEntity = new ResponseEntity<>(responseData, HttpStatus.OK);
				
			}else {
				responseData.setMessage(Constants.REQUEST_NOT_PRESENT);
				responseData.setStatus(Constants.DATA_NOT_FOUND_MESSAGE);
				responseData.setData(new ArrayList<>());
				responseData.setResource(new ArrayList<>());
				responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.BAD_REQUEST);
			}
			
		}catch(Exception e){
			log.info("Inside HelpRequestService >> getAllRequests  >> exception {}", e);
			responseData.setMessage(Constants.ERROR_MESSAGE);
			responseData.setStatus(Constants.ERROR);
			responseData.setError(e.getMessage());
			responseEntity = new ResponseEntity<ResponseData>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return responseEntity;
	}
	
}
