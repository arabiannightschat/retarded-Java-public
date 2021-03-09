package com.nights.retarded.records.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "RecordsUserType")
@Table(name = "records_user_type")
@Data
public class RecordsUserType  {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String userTypeId;

	@JsonIgnore
	private String openId;

	private String typeId;

	private Integer ix;


}