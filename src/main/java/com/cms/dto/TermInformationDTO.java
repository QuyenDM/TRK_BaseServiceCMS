/**
 * @(#)TermInformationForm.java ,
 * Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */
package com.cms.dto;

import com.vfw5.base.dto.BaseFWDTO;
import com.vfw5.base.utils.DateTimeUtils;
import com.cms.model.TermInformation;
import com.vfw5.base.utils.StringUtils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author QuyenDM
 * @version 1.0
 * @since 7/19/2016 12:01 AM
 */
@XmlRootElement(name = "TermInformation")
public class TermInformationDTO extends BaseFWDTO<TermInformation> {
    //Fields

    private String id;
    private String custId;
    private String taxCode;
    private String startTime;
    private String endTime;
    private String provider;
    private String email;
    private String phone;
    private String service;
    private String mineName;
    private static long changedTime = 0;

    //Constructor
    public TermInformationDTO() {
        setDefaultSortField("startTime");
    }

    public TermInformationDTO(String taxCode) {
        this.taxCode = taxCode;
    }

    public TermInformationDTO(String id, String custId, String taxCode, String startTime, String endTime, String provider, String email, String phone, String service, String mineName) {
        this.id = id;
        this.custId = custId;
        this.taxCode = taxCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.provider = provider;
        this.email = email;
        this.phone = phone;
        this.service = service;
        this.mineName = mineName;
    }

    //Getters and setters
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    @Override
    public TermInformation toModel() {
        try {
            TermInformation model = new TermInformation(
                    !StringUtils.validString(id) ? null
                    : Long.valueOf(id),
                    !StringUtils.validString(custId) ? null
                    : Double.parseDouble(custId), taxCode,
                    !StringUtils.validString(startTime) ? null
                    : DateTimeUtils.convertStringToDate(startTime),
                    !StringUtils.validString(endTime) ? null
                    : DateTimeUtils.convertStringToDate(endTime),
                    provider,
                    email,
                    phone,
                    service,
                    mineName);
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id) ? null : Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getStartTime().toString();
    }

    @Override
    public long getChangedTime() {
        return TermInformationDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        TermInformationDTO.changedTime = changedTime;
    }

    public String getMineName() {
        return mineName;
    }

    public void setMineName(String mineName) {
        this.mineName = mineName;
    }

}
