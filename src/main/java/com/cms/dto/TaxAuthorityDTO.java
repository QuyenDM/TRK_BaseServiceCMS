/**
 * @(#)TaxAuthorityForm.java ,
 * Copyright 2011 Viettel Telecom. All rights reserved
 * VIETTEL PROPRIETARY/CONFIDENTIAL
 */

package com.cms.dto;

import com.cms.model.TaxAuthority;
import com.vfw5.base.dto.BaseFWDTO;
import com.vfw5.base.utils.StringUtils;
import javax.xml.bind.annotation.XmlRootElement;
/**
* @author QuyenDM
* @version 1.0
* @since 8/23/2016 11:13 PM
*/
@XmlRootElement(name = "TaxAuthority")
public class TaxAuthorityDTO extends BaseFWDTO<TaxAuthority>{    
	//Fields
    private String id;
    private String maCqt;
    private String tenCqt;
    private String maTinh;
    private String maQuanHuyen;
    private String status;
    private static long changedTime = 0;
    //Constructor
	public TaxAuthorityDTO() {
		setDefaultSortField("maCqt");	
	}
	public TaxAuthorityDTO(String id, String maCqt, String tenCqt, String maTinh, String maQuanHuyen, String status) {
		this.id = id;
		this.maCqt = maCqt;
		this.tenCqt = tenCqt;
		this.maTinh = maTinh;
		this.maQuanHuyen = maQuanHuyen;
		this.status = status;
	}	
	//Getters and setters
    
	public void setId(String id) {
        this.id = id;
    }
	public String getId() {		
        return id;		
    }
    
	public void setMaCqt(String maCqt) {
        this.maCqt = maCqt;
    }
	public String getMaCqt() {		
        return maCqt;		
    }
    
	public void setTenCqt(String tenCqt) {
        this.tenCqt = tenCqt;
    }
	public String getTenCqt() {		
        return tenCqt;		
    }
    
	public void setMaTinh(String maTinh) {
        this.maTinh = maTinh;
    }
	public String getMaTinh() {		
        return maTinh;		
    }
    
	public void setMaQuanHuyen(String maQuanHuyen) {
        this.maQuanHuyen = maQuanHuyen;
    }
	public String getMaQuanHuyen() {		
        return maQuanHuyen;		
    }
    
	public void setStatus(String status) {
        this.status = status;
    }
	public String getStatus() {		
        return status;		
    }
    
	@Override
	public TaxAuthority toModel() {
		TaxAuthority model = new TaxAuthority(
            !StringUtils.validString(id)?null:
                Long.valueOf(id), 
            maCqt, 
            tenCqt, 
            maTinh, 
            maQuanHuyen, 
            status);
		return model;
	}
	
    @Override
    public Long getFWModelId() {
        return !StringUtils.validString(id)?null:Long.valueOf(id);
    }

    @Override
    public String catchName() {
        return getMaCqt().toString();
    }
    
    @Override
    public long getChangedTime() {
        return TaxAuthorityDTO.changedTime;
    }

    @Override
    public void setChangedTime(long changedTime) {
        TaxAuthorityDTO.changedTime = changedTime;
    }
}

