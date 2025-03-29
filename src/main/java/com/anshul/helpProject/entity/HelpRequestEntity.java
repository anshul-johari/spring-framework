package com.anshul.helpProject.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requestsMaster")
public class HelpRequestEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "requestCode")
	private String requestCode;
	
	@Column(name = "discription")
	private String discription;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "createdBy")
	private String createdBy;
	
	@Column(name = "createdOn")
	private LocalDateTime createdOn;
	
	@Column(name = "updatedBy")
	private String updatedBy;
	
	@Column(name = "updatedOn")
	private LocalDateTime updatedOn;
	
	@Column(name = "isDeleted")
	private Boolean isDeleted;

}
