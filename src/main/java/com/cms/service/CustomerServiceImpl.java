/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cms.service;

import com.cms.business.service.CustomerBusinessInterface;
import com.cms.dto.CustomerCareHistoryDTO;
import com.cms.dto.CustomerContactDTO;
import com.cms.dto.CustomerDTO;
import com.cms.dto.CustomerInfomationDTO;
import com.cms.dto.CustomerStatusDTO;
import com.cms.dto.CustomerUserInfoDTO;
import com.cms.dto.TermInformationDTO;
import com.cms.model.Customer;
import com.vfw5.base.dto.ResultDTO;
import com.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.HashMap;
import com.vfw5.base.pojo.ConditionBean;
import com.vfw5.base.utils.DataUtil;
import com.vfw5.base.utils.ParamUtils;
import com.vfw5.base.utils.StringUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 16-Apr-15 11:55 AM
 */
@WebService(endpointInterface = "com.cms.service.CustomerService")
//@net.anotheria.moskito.aop.annotation.Monitor
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    BaseFWServiceInterface customerBusiness;
    @Autowired
    BaseFWServiceInterface customerContactBusiness;
    @Autowired
    BaseFWServiceInterface customerCareHistoryBusiness;
    @Autowired
    BaseFWServiceInterface termInformationBusiness;
    @Autowired
    BaseFWServiceInterface customerStatusBusiness;
    @Autowired
    CustomerBusinessInterface customerBusinessInterface;

    @Override
    public String updateCustomer(CustomerDTO customerDTO) {
        return customerBusiness.update(customerDTO);
    }

    @Override
    public String deleteCustomer(Long id) {
        return customerBusiness.delete(id);
    }

    @Override
    public String deleteListCustomer(List<CustomerDTO> customerListDTO) {
        return customerBusiness.delete(customerListDTO);
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        if (id != null && id > 0) {
            return (CustomerDTO) customerBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<CustomerDTO> getListCustomerDTO(CustomerDTO customerDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (customerDTO != null) {
            return customerBusiness.search(customerDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

//    @Override
//    public ResultDTO insertCustomer(CustomerDTO customerDTO) {
//        return customerBusiness.createObject(customerDTO);
//    }
    //
    @Override
    public ResultDTO insertCustomer(CustomerDTO customerDTO) {
        ResultDTO resultDTO = new ResultDTO();
        Class<?> c = customerDTO.getClass();
        HashMap<String, String> hasmap = new HashMap<>();
        Customer customer = new Customer();
        String[] lstColumnUnique = customer.getUniqueColumn();
        for (int i = 0; i < lstColumnUnique.length; i++) {
            try {
                Method method = c.getMethod(DataUtil.getGetterOfColumn(lstColumnUnique[i]));
                try {
                    hasmap.put(lstColumnUnique[i], (String) method.invoke(customerDTO));
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Boolean check = customerBusiness.isDuplicate(null, hasmap);
            if (!check) {
                resultDTO = customerBusiness.createObject(customerDTO);
                resultDTO.setKey(ParamUtils.SUCCESS);
            } else {
                resultDTO.setKey("ERROR_EXISTED");
            }
        } catch (Exception e) {
            resultDTO.setKey("ERROR_SYSTEM");
        }
        return resultDTO;
    }

    //
    @Override
    public String insertOrUpdateListCustomer(List<CustomerDTO> customerDTO) {
        return customerBusiness.insertList(customerDTO);
    }

    @Override
    public List<String> getSequenseCustomer(String seqName, int... size) {
        int number = (size[0] > 0 ? size[0] : 1);

        return customerBusiness.getListSequense(seqName, number);
    }

    @Override
    public List<CustomerDTO> getListCustomerByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<CustomerDTO> lstCustomer = new ArrayList<>();
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
        lstCustomer = customerBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstCustomer;
    }

    @Override
    public List<CustomerDTO> saveOrUpdateAndReturnErrors(List<CustomerDTO> lstCustomer) {
        return customerBusinessInterface.insertOrUpdateReturnRecordErrors(lstCustomer);
    }

    /**
     * QuyenDM Lay thong tin khach hang - user theo username
     *
     * @param userCode
     * @return
     */
    @Override
    public CustomerUserInfoDTO getCustUserInfor(String userCode) {
        return customerBusinessInterface.getCustUserInfo(userCode);
    }

    @Override
    public CustomerInfomationDTO getCustInfo(String taxCode, String staffCode) {
        CustomerContactDTO contactDTO = new CustomerContactDTO(taxCode);
        List<CustomerContactDTO> lstCustomerContactDTOs = customerContactBusiness.search(contactDTO, 0, Integer.MAX_VALUE, "asc", "name");
        CustomerCareHistoryDTO careHistoryDTO = new CustomerCareHistoryDTO(taxCode);
        List<CustomerCareHistoryDTO> lstCustomerCareHistoryDTOs = customerCareHistoryBusiness.search(careHistoryDTO, 0, Integer.MAX_VALUE, "desc", "createDate");
        TermInformationDTO termInformationDTO = new TermInformationDTO(taxCode);
        List<TermInformationDTO> lstTermInformationDTOs = termInformationBusiness.search(termInformationDTO, 0, Integer.MAX_VALUE, "desc", "endTime");
        CustomerStatusDTO customerStatusDTO = new CustomerStatusDTO(taxCode);
        if (!DataUtil.isStringNullOrEmpty(staffCode)) {
            customerStatusDTO.setStaffCode(staffCode);
        }
        List<CustomerStatusDTO> lstCustomerStatusDTOs = customerStatusBusiness.search(customerStatusDTO, 0, Integer.MAX_VALUE, "desc", "lastUpdated");
        return new CustomerInfomationDTO(lstTermInformationDTOs, lstCustomerContactDTOs, lstCustomerCareHistoryDTOs, lstCustomerStatusDTOs);
    }

    @Override
    public List<CustomerDTO> searchCustomers(CustomerDTO customerDTO, int maxResult) {
        return customerBusinessInterface.searchCustomers(customerDTO, maxResult);
    }

    @Override
    public List<CustomerDTO> getListCustomerFromTermInfo(List<ConditionBean> lstConditions) {
        return customerBusinessInterface.getListCustomerFromTermInfo(lstConditions);
    }

    @Override
    public List<CustomerDTO> getCustomerExisted(List<String> taxCodes) {
        return customerBusinessInterface.getCustomerExisted(taxCodes);
    }

}
