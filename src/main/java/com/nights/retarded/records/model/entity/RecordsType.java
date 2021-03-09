package com.nights.retarded.records.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "RecordsType")
@Table(name = "records_type")
@Data
public class RecordsType {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String typeId;

	private String name;

	private String icon;

	private Integer type;

    private Integer commonUse;

	private Integer defaultIx;

}