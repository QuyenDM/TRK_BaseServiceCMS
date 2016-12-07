/**
 * @(#)AreaBO.java 05-May-15 4:28 PM, Copyright 2011 Viettel Telecom. All rights
 * reserved VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.cms.model;

import com.vfw5.base.model.BaseFWModel;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.cms.dto.AreaDTO;
import java.util.Date;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 05-May-15 4:28 PM
 */
//@Entity
//@Table(name = "AREA")
public class Area extends BaseFWModel {

    //Fields
    private Long id;
    private Long parentId;
    private String code;
    private String name;
    private String type;
    private String status;
    private String path;
    private Long provinceId;
    private String provinceCode;
    private String provinceName;
    private Long districtId;
    private String districtCode;
    private String districtName;
    private Long wardId;
    private String wardCode;
    private String wardName;
    private String description;
    private Date createDate;

    //Constructors
    public Area() {
        setColId("id");
        setColName("code");
        setUniqueColumn(new String[]{"code"});
    }

    public Area(Long id) {
        this.id = id;
    }

    public Area(Long id, Long parentId, String code, String name, String type, String status, String path, Long provinceId, String provinceCode, String provinceName, Long districtId, String districtCode, String districtName, Long wardId, String wardCode, String wardName, String description) {
        this.id = id;
        this.parentId = parentId;
        this.code = code;
        this.name = name;
        this.type = type;
        this.status = status;
        this.path = path;
        this.provinceId = provinceId;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardCode = wardCode;
        this.wardName = wardName;
        this.description = description;
    }

    //Getters and Setters
    @Id
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence",
            parameters = {
                @Parameter(name = "sequence", value = "AREA_SEQ")
            }
    )
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "PARENT_ID")
    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "CODE", unique = true, nullable = false)
    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "TYPE", nullable = false)
    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Column(name = "STATUS", nullable = false)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Column(name = "PATH")
    public String getPath() {
        return this.path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    @Column(name = "PROVINCE_ID")
    public Long getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(final Long provinceId) {
        this.provinceId = provinceId;
    }

    @Column(name = "PROVINCE_CODE")
    public String getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(final String provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Column(name = "PROVINCE_NAME")
    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(final String provinceName) {
        this.provinceName = provinceName;
    }

    @Column(name = "DISTRICT_ID")
    public Long getDistrictId() {
        return this.districtId;
    }

    public void setDistrictId(final Long districtId) {
        this.districtId = districtId;
    }

    @Column(name = "DISTRICT_CODE")
    public String getDistrictCode() {
        return this.districtCode;
    }

    public void setDistrictCode(final String districtCode) {
        this.districtCode = districtCode;
    }

    @Column(name = "DISTRICT_NAME")
    public String getDistrictName() {
        return this.districtName;
    }

    public void setDistrictName(final String districtName) {
        this.districtName = districtName;
    }

    @Column(name = "WARD_ID")
    public Long getWardId() {
        return this.wardId;
    }

    public void setWardId(final Long wardId) {
        this.wardId = wardId;
    }

    @Column(name = "WARD_CODE")
    public String getWardCode() {
        return this.wardCode;
    }

    public void setWardCode(final String wardCode) {
        this.wardCode = wardCode;
    }

    @Column(name = "WARD_NAME")
    public String getWardName() {
        return this.wardName;
    }

    public void setWardName(final String wardName) {
        this.wardName = wardName;
    }

    @Column(name = "DESCRIPTION", insertable = false, updatable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", insertable = false, updatable = false)
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public AreaDTO toDTO() {
        AreaDTO dto = new AreaDTO(
                id == null ? null : id.toString(), parentId == null ? null : parentId.toString(), code, name, type, status, path, provinceId == null ? null : provinceId.toString(), provinceCode, provinceName, districtId == null ? null : districtId.toString(), districtCode, districtName, wardId == null ? null : wardId.toString(), wardCode, wardName, description
        );
        return dto;
    }
}
