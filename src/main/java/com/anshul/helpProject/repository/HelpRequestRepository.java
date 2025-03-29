package com.anshul.helpProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.anshul.helpProject.entity.HelpRequestEntity;
import java.util.Optional;


public interface HelpRequestRepository extends JpaRepository<HelpRequestEntity, Integer> , JpaSpecificationExecutor<HelpRequestEntity>{

	Optional<HelpRequestEntity> findByRequestCode(String requestCode);
}
