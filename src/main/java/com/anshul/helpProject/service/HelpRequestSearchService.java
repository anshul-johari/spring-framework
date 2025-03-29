package com.anshul.helpProject.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anshul.helpProject.dto.HelpSearchRequest;
import com.anshul.helpProject.dto.HelpSearchResponse;
import com.anshul.helpProject.entity.HelpRequestEntity;
import com.anshul.helpProject.repository.HelpRequestRepository;
import com.anshul.helpProject.util.Constants;
import com.anshul.helpProject.util.HelpSpecificationUtil;
import com.anshul.helpProject.util.SearchResponseData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HelpRequestSearchService {
	
	@Autowired
	private HelpRequestRepository helpRequestRepository;
	
	public ResponseEntity<SearchResponseData<HelpSearchResponse>> searchRequest(HelpSearchRequest helpSearchRequest){
		log.info("Inside HelpRequestSearchSercive >> searchRequest");
		SearchResponseData<HelpSearchResponse> responseData = new SearchResponseData<>();
		ResponseEntity<SearchResponseData<HelpSearchResponse>> responseEntity = null;
		try{
			int batchSize = helpSearchRequest.getPageSize();
			if (helpSearchRequest.getPageSize() <= 0) batchSize = 10;
			String sortBy = helpSearchRequest.getSortBy();
			String sortDirection = helpSearchRequest.getSortDirection();
			if (sortBy == null || sortBy.isEmpty()) sortBy = "title";
		    if (sortDirection == null || sortDirection.isEmpty()) sortDirection = "asc";
		    if(!sortDirection.equals("desc") && !sortDirection.equals("asc")) {
				responseData.setMessage("Enter valid sortDirection asc or desc");
				responseData.setStatus(false);
				return new ResponseEntity<SearchResponseData<HelpSearchResponse>>(responseData, HttpStatus.BAD_REQUEST);
		    }
	        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
			Pageable pageable = PageRequest.of(helpSearchRequest.getPage(), batchSize, sort);
			Page<HelpRequestEntity> pages = null;
			Specification<HelpRequestEntity> spec = Specification.where
					(HelpSpecificationUtil.hasTitle(helpSearchRequest.getTitle())
							.and(HelpSpecificationUtil.hasRequestCode(helpSearchRequest.getRequestCode()))
							.and(HelpSpecificationUtil.hasLocation(helpSearchRequest.getLocation()))
							.and(HelpSpecificationUtil.hasStatus(helpSearchRequest.getStatus()))
							.and(HelpSpecificationUtil.hasIsDeleted(0)));
			pages = helpRequestRepository.findAll(spec, pageable);
			if(pages.getTotalElements() > 0) {
				responseData.setMessage(Constants.SUCCESS_MESSAGE);
				responseData.setStatus(true);
				responseData.setData(convertToHelpSearchResponse(pages));
				responseEntity = new ResponseEntity<SearchResponseData<HelpSearchResponse>>(responseData, HttpStatus.OK);
			} else {
				responseData.setMessage(Constants.DATA_NOT_FOUND_MESSAGE);
				responseData.setStatus(false);
				responseData.setData(convertToHelpSearchResponse(pages));
				responseData.setResource(new ArrayList<>());
				responseEntity = new ResponseEntity<SearchResponseData<HelpSearchResponse>>(responseData, HttpStatus.OK);
			}
			
		}catch (Exception e) {
			log.info("Inside  InsuranceSearchSercive >> searchInsurance >> exception {}",e);
			responseData.setMessage(Constants.ERROR);
			responseData.setStatus(false);
			responseData.setError(e.getMessage());
			responseEntity = new ResponseEntity<SearchResponseData<HelpSearchResponse>>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		return responseEntity;
	}
	
	
	
	public Page<HelpSearchResponse> convertToHelpSearchResponse(Page<HelpRequestEntity> usersPage) {
	    return usersPage.map(product -> new HelpSearchResponse(
	    		product.getId(),
	    		product.getTitle(),
	    		product.getRequestCode(),     
	    		product.getDiscription(),
	    		product.getLocation(),
	    		product.getStatus()
	    ));
	}	

}
