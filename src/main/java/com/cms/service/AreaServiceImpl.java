/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cms.service;

import com.cms.dto.AreaDTO;
import com.cms.model.Department;
import com.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import javax.jws.WebService;
import java.util.ArrayList;
import com.vfw5.base.pojo.ConditionBean;
import com.vfw5.base.utils.ParamUtils;
import com.vfw5.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.vfw5.base.dto.ResultDTO;
import com.vfw5.base.utils.DataUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 05-May-15 4:28 PM
 */
//@net.anotheria.moskito.aop.annotation.Monitor
@WebService(endpointInterface = "com.cms.service.AreaService")
public class AreaServiceImpl implements AreaService {

    @Autowired
    BaseFWServiceInterface areaBusiness;

    @Override
    public String updateArea(AreaDTO areaDTO) {
        return areaBusiness.update(areaDTO);
    }
//    @Override
//    public String updateArea(AreaDTO areaDTO) {
//        return areaBusiness.update(areaDTO);
//    }

    @Override
    public String deleteArea(Long id) {
        return areaBusiness.delete(id);
    }
//    @Override
//    public String deleteArea(Long id) {
//        return areaBusiness.delete(id);
//    }

    @Override
    public String deleteListArea(List<AreaDTO> areaListDTO) {
        return areaBusiness.delete(areaListDTO);
    }
//    @Override
//    public String deleteListArea(List<AreaDTO> areaListDTO) {
//        return areaBusiness.delete(areaListDTO);
//    }

    @Override
    public AreaDTO findAreaById(Long id) {
        if (id != null && id > 0) {
            return (AreaDTO) areaBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<AreaDTO> getListAreaDTO(AreaDTO areaDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (areaDTO != null) {
            return areaBusiness.search(areaDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

//    @Override
//    public ResultDTO insertArea(AreaDTO areaDTO) {
//        return areaBusiness.createObject(areaDTO);
//    }
    @Override
    public ResultDTO insertArea(AreaDTO areaDTO) {
        List<AreaDTO> lstDepartmentDTOs = new ArrayList<>();
        ResultDTO resultDTO = new ResultDTO();
        Class<?> c = areaDTO.getClass();
        HashMap<String, String> hasmap = new HashMap<>();
        Department department = new Department();
        String[] lstColumnUnique = department.getUniqueColumn();
        for (int i = 0; i < lstColumnUnique.length; i++) {
            try {
                Method method = c.getMethod(DataUtil.getGetterOfColumn(lstColumnUnique[i]));
                try {
                    hasmap.put(lstColumnUnique[i], (String) method.invoke(areaDTO));
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DepartmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(DepartmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(DepartmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(DepartmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(DepartmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Boolean check = areaBusiness.isDuplicate(null, hasmap);
            if (!check) {
                resultDTO = areaBusiness.createObject(areaDTO);
//                resultDTO.setKey(ParamUtils.SUCCESS);
            } else {
                resultDTO.setKey("ERROR_EXISTED");
            }
        } catch (Exception e) {
            resultDTO.setKey("ERROR_SYSTEM");
        }
        return resultDTO;
    }

    @Override
    public String insertOrUpdateListArea(List<AreaDTO> areaDTO) {
        return areaBusiness.insertList(areaDTO);
    }

    @Override
    public List<String> getSequenseArea(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return areaBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<AreaDTO> getListAreaByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<AreaDTO> lstArea = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER_DOUBLE)) {
                con.setType(ParamUtils.NUMBER_DOUBLE);
            } else {
                String value = "";
                if (con.getOperator().equalsIgnoreCase(ParamUtils.NAME_LIKE)) {
                    value = StringUtils.formatLike(con.getValue());
                } else {
                    value = con.getValue();
                }
                con.setValue(value.toLowerCase());
                con.setField(StringUtils.formatFunction("lower", con.getField()));
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));
        }

        lstArea = areaBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstArea;
    }

}
