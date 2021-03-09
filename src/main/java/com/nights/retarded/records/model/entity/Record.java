package com.nights.retarded.records.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "Record")
@Table(name = "records_record")
@Data
public class Record {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String recordId;

	private String noteId;

	private String typeId;

	private BigDecimal money;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date dt;

	private String description;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDt;

}