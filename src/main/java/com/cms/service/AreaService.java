/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cms.service;

import com.cms.dto.AreaDTO;
import com.vfw5.base.pojo.ConditionBean;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.WebParam;
import com.vfw5.base.dto.ResultDTO;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 05-May-15 4:28 PM
 */
@WebService(targetNamespace = "http://service.cms.com")
public interface AreaService {

    @WebMethod(operationName = "getListAreaDTO")
    @WebResult(name = "areaDTO", targetNamespace = "http://service.cms.com")
    public List<AreaDTO> getListAreaDTO(@WebParam(name = "areaDTO") AreaDTO areaDTO, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);

    //
    @WebMethod(operationName = "updateArea")
    @WebResult(name = "message", targetNamespace = "http://service.cms.com")
    public String updateArea(@WebParam(name = "areaDTO") AreaDTO areaDTO);

    //
    @WebMethod(operationName = "deleteArea")
    @WebResult(name = "message", targetNamespace = "http://service.cms.com")
    public String deleteArea(@WebParam(name = "areaDTOId") Long id);

    //
    @WebMethod(operationName = "deleteListArea")
    @WebResult(name = "message", targetNamespace = "http://service.cms.com")
    public String deleteListArea(@WebParam(name = "areaListDTO") List<AreaDTO> areaListDTO);

    //
    @WebMethod(operationName = "findAreaById")
    @WebResult(name = "area", targetNamespace = "http://service.cms.com")
    public AreaDTO findAreaById(@WebParam(name = "areaDTOId") Long id);

    //
    @WebMethod(operationName = "insertArea")
    @WebResult(name = "resultDTO", targetNamespace = "http://service.cms.com")
    public ResultDTO insertArea(@WebParam(name = "areaDTO") AreaDTO areaDTO);

    //
    @WebMethod(operationName = "insertOrUpdateListArea")
    @WebResult(name = "insertListArea", targetNamespace = "http://service.cms.com")
    public String insertOrUpdateListArea(@WebParam(name = "areaDTO") List<AreaDTO> areaDTO);

    //
    @WebMethod(operationName = "getSequenseArea")
    @WebResult(name = "getSequense", targetNamespace = "http://service.cms.com")
    public List<String> getSequenseArea(@WebParam(name = "sequenseName") String seqName, @WebParam(name = "Size") int... size);

    //    
    @WebMethod(operationName = "getListAreaByCondition")
    @WebResult(name = "AreaDTO", targetNamespace = "http://service.cms.com")
    public List<AreaDTO> getListAreaByCondition(@WebParam(name = "lstCondition") List<ConditionBean> lstCondition, @WebParam(name = "rowStart") int rowStart, @WebParam(name = "maxRow") int maxRow, @WebParam(name = "sortType") String sortType, @WebParam(name = "sortFieldList") String sortFieldList);
}
