package com.nights.retarded.records.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "RecordsType")
@Table(name = "records_type")
public class RecordsType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@JsonIgnore
	private String typeId;
	
	@Column(name="name")
	private String name;

	@Column(name="icon")
	private String icon;

	@Column(name="type")
	@JsonIgnore
	private Integer type;

	@Column(name="is_default")
	@JsonIgnore
	private Integer isDefault;

	@Column(name="is_alternative")
	@JsonIgnore
	private Integer isAlternative;
	
	@Column(name="default_ix")
	@JsonIgnore
	private Integer defaultIx;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getIsAlternative() {
        return isAlternative;
    }

    public void setIsAlternative(Integer isAlternative) {
        this.isAlternative = isAlternative;
    }

    public Integer getDefaultIx() {
        return defaultIx;
    }

    public void setDefaultIx(Integer defaultIx) {
        this.defaultIx = defaultIx;
    }
}