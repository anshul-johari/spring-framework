package com.anshul.helpProject.util;

import org.springframework.data.jpa.domain.Specification;

import com.anshul.helpProject.entity.HelpRequestEntity;

public class HelpSpecificationUtil {
	
	public static Specification<HelpRequestEntity> hasTitle(String title) {
		return (root, query, criteriaBuilder) -> title == null || title.isEmpty() ? null 
				: criteriaBuilder.like(root.get("title"), "%" + title + "%");
	}
	
	public static Specification<HelpRequestEntity> hasRequestCode(String requestCode) {
		return (root, query, criteriaBuilder) -> requestCode == null || requestCode.isEmpty() ? null 
				: criteriaBuilder.like(root.get("requestCode"), "%" + requestCode + "%");
	}
	
	public static Specification<HelpRequestEntity> hasLocation(String location) {
		return (root, query, criteriaBuilder) -> location == null || location.isEmpty() ? null 
				: criteriaBuilder.like(root.get("location"), "%" + location + "%");
	}
	
	public static Specification<HelpRequestEntity> hasStatus(String status) {
		return (root, query, criteriaBuilder) -> status == null || status.isEmpty() ? null 
				: criteriaBuilder.like(root.get("status"), "%" + status + "%");
	}
	
	public static Specification<HelpRequestEntity> hasIsDeleted(Integer isDeleted) {
		return (root, query, criteriaBuilder) -> {
	        if (isDeleted == null) return criteriaBuilder.conjunction();
	        Boolean isDeletedBoolean = (isDeleted == 1);
	        return criteriaBuilder.equal(root.get("isDeleted"), isDeletedBoolean);
	    };
	}

}
