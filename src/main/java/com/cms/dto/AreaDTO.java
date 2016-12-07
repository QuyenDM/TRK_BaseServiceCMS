/**
 * @(#)AreaForm.java , Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.cms.dto;

import com.vfw5.base.dto.BaseFWDTO;
import com.cms.model.Area;
import com.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 05-May-15 4:28 PM
 */
@XmlRootElement(name = "Area")
public class AreaDTO extends BaseFWDTO<Area> {

    //Fields
    private String id;
    private String parentId;
    private String code;
    private String name;
    private String type;
    private String status;
    private String path;
    private String provinceId;
    private String provinceCode;
    private String provinceName;
    private String districtId;
    private String districtCode;
    private String districtName;
    private String wardId;
    private String wardCode;
    private String wardName;
    private String description;
    private static long changedTime = 0;

    //Constructor
    public AreaDTO() {
        setDefaultSortField("code");
    }

    public AreaDTO(String id, String parentId, String code, String name, String type, String status, String path, String provinceId, String provinceCode, String provinceName, String districtId, String districtCode, String districtName, String wardId, String wardCode, String wardName, String description) {
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
    //Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardName() {
        return wardName;
    }

    @Override
    public Area toModel() {
        Area model = new Area(
                !StringUtils.validString(id) ? null
                        : Long.valueOf(id),
                !StringUtils.validString(parentId) ? null
                        : Long.valueOf(parentId),
                code,
                name,
                type,
                status,
                path,
                !StringUtils.validString(provinceId) ? null
                        : Long.valueOf(provinceId),
                provinceCode,
                provinceName,
                !StringUtils.validString(districtId) ? null
                        : Long.valueOf(districtId),
                districtCode,
                districtName,
                !StringUtils.validString(wardId) ? null
                        : Long.valueOf(wardId),
                wardCode,
                wardName,
                description);
        model.setUserNameLogging(userNameLogging);
        return model;
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getCode().toString();
    }

    @Override
    public long getChangedTime() {
        return AreaDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        AreaDTO.changedTime = changedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
