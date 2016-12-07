/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cms.dao;

import com.cms.dto.CustomerDTO;
import com.cms.dto.CustomerUserInfoDTO;
import com.vfw5.base.dao.BaseFWDAOImpl;
import com.cms.model.Customer;
import com.vfw5.base.dto.ResultDTO;
import com.vfw5.base.pojo.ConditionBean;
import com.vfw5.base.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author TruongBX3
 * @version 1.0
 * @since 16-Apr-15 11:55 AM
 */
@Repository("customerDAO")
public class CustomerDAO extends BaseFWDAOImpl<Customer, Long> {

    public CustomerDAO() {
        this.model = new Customer();
    }

    public CustomerDAO(Session session) {
        this.session = session;
    }

    public List<Customer> saveOrUpdateReturnErrors(List<Customer> lstCus) {
        List<Customer> lstCustomer = new ArrayList<>();
        Customer cs = null;
        Session ss = sessionFactory.openSession();
        Transaction tx = ss.beginTransaction();
        try {
            for (Customer lstCustomer1 : lstCus) {

                ss.save(lstCustomer1);
                cs = lstCustomer1;
                ss.flush();

            }
            tx.commit();
        } catch (HibernateException he) {
            log.error(he.getMessage(), he);
            lstCustomer.add(cs);

            return lstCustomer;
        } finally {
            tx.rollback();
            ss.close();
        }

        return lstCustomer;
    }

    /**
     * QuyenDM getCustomerUserInfoDTO
     *
     * @param userCode
     * @return
     */
    public CustomerUserInfoDTO getCustUserInforDTO(String userCode) {
        //Doi tuong tra ve
        CustomerUserInfoDTO CustUserInforDTO = new CustomerUserInfoDTO();
        StringBuilder sql = new StringBuilder();
        //Cau lenh truy van du lieu        
        sql.append("SELECT a.cust_id custId, a.code custCode,a.name custName, ");
        sql.append("       a.cust_type custType,b.code userCode,b.name userName, ");
        sql.append("       b.cust_user_type custUserType, b.email userEmail, b.tel_number userTelNumber ");
        sql.append("FROM   customer a, customer_user b ");
        sql.append("WHERE b.cust_id = a.cust_id ");
        sql.append("AND a.status <> 6 AND b.status = 1 ");
        sql.append("AND LOWER(b.code) = LOWER(?)");
        //Su dung SQLQuery tao cau truy van
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Transformers.aliasToBean(CustomerUserInfoDTO.class));
        query.addScalar("custId", new StringType());
        query.addScalar("custCode", new StringType());
        query.addScalar("custName", new StringType());
        query.addScalar("custType", new StringType());
        query.addScalar("userCode", new StringType());
        query.addScalar("userName", new StringType());
        query.addScalar("custUserType", new StringType());
        query.addScalar("userEmail", new StringType());
        query.addScalar("userTelNumber", new StringType());
        //Truyen tham so vao cau query
        query.setParameter(0, userCode);
        List<CustomerUserInfoDTO> listCustUserInforDTO = query.list();
        //Neu danh sach tra ve co du lieu thi gan doi tuong tra ve        
        if (listCustUserInforDTO != null && listCustUserInforDTO.size() > 0) {
            CustUserInforDTO = listCustUserInforDTO.get(0);
        }
        return CustUserInforDTO;
    }

    //Tim kiem khach hang join voi trang thai khach hang va so dien thoai khach hang
    public List<CustomerDTO> searchCustomers(CustomerDTO customer, int maxResult) {
        List<CustomerDTO> lstCustomers;
        List params = new ArrayList();
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("       SELECT DISTINCT a.TAX_CODE taxCode, ");
        sqlQuery.append("               a.NAME name, ");
        sqlQuery.append("               a.AGENCY agency, ");
        sqlQuery.append("               a.TEL_NUMBER telNumber, ");
        sqlQuery.append("               a.EMAIL email, ");
        sqlQuery.append("               a.OFFICE_ADDRESS officeAddress, ");
        sqlQuery.append("               a.TAX_AUTHORITY taxAuthority, ");
        sqlQuery.append("               a.MINE_NAME mineName, ");
        sqlQuery.append("               e.notes notes, ");
        sqlQuery.append("               to_char(e.create_date,'dd/MM/yyyy HH24:Mi:ss') createDate, ");
        sqlQuery.append("               e.status status ");
        sqlQuery.append("       FROM CUSTOMER a ");
        //Neu truong trang thai duoc dien vao thi them dieu kien        
        if (!DataUtil.isStringNullOrEmpty(customer.getStatus())
                || !DataUtil.isStringNullOrEmpty(customer.getStaffId())) {
            sqlQuery.append("            LEFT JOIN Customer_status b ");
            sqlQuery.append("                ON b.TAX_CODE = a.TAX_CODE ");
        }
        //Neu truong email duoc dien vao thi them dieu kien        
        if (!DataUtil.isStringNullOrEmpty(customer.getEmail())
                || !DataUtil.isStringNullOrEmpty(customer.getTelNumber())) {
            sqlQuery.append("            LEFT JOIN CUSTOMER_CONTACT c ");
            sqlQuery.append("                ON c.TAX_CODE = a.TAX_CODE ");
        }
        //Neu truong so dien thoai duoc dien vao thi them dieu kien        
        if (!DataUtil.isStringNullOrEmpty(customer.getTelNumber())
                || !DataUtil.isStringNullOrEmpty(customer.getEmail())
                || !DataUtil.isStringNullOrEmpty(customer.getProvider())
                || !DataUtil.isStringNullOrEmpty(customer.getMineName())) {
            sqlQuery.append("            LEFT JOIN TERM_INFORMATION d ");
            sqlQuery.append("                ON d.TAX_CODE = a.TAX_CODE ");
        }
        //Neu truong ngay thuc hien goi thi join them bang customer_care_history
//        if (!DataUtil.isStringNullOrEmpty(customer.getCustCareHistoryCreatedDate())) {
        sqlQuery.append("            LEFT JOIN ");
        sqlQuery.append("            (SELECT  f.tax_code tax_code, ");
        sqlQuery.append("  		      f.notes notes, ");
        sqlQuery.append("  		      f.status status, ");
        sqlQuery.append("  		      f.CREATE_DATE create_date ");
        sqlQuery.append("             FROM CUSTOMER_CARE_HISTORY f ");
        sqlQuery.append("  	      JOIN ");
        sqlQuery.append("  		  (SELECT h.tax_code taxCode, ");
        sqlQuery.append("  		          MAX(h.CREATE_DATE) maxDate ");
        sqlQuery.append("  		   FROM CUSTOMER_CARE_HISTORY h");
        sqlQuery.append("  		   GROUP BY h.tax_code ");
        sqlQuery.append("  		  ) g ");
        sqlQuery.append("             ON     g.taxCode    = f.tax_code ");
        sqlQuery.append("  		 AND g.maxDate   = f.create_date ");
        sqlQuery.append("             )  e ");
        sqlQuery.append("                ON e.TAX_CODE = a.TAX_CODE ");
//        }
        sqlQuery.append("       WHERE 1=1 ");
        //MST
        if (!DataUtil.isStringNullOrEmpty(customer.getTaxCode())) {
            sqlQuery.append("           AND lower(a.TAX_CODE) LIKE lower(?) ");
            params.add("%" + customer.getTaxCode() + "%");
        }
        //Staff id
        if (!DataUtil.isStringNullOrEmpty(customer.getStaffId())) {
            sqlQuery.append("           AND b.STAFF_ID = ? ");
            params.add(customer.getStaffId());
        }
        //Ten cong ty
        if (!DataUtil.isStringNullOrEmpty(customer.getName())) {
            sqlQuery.append("           AND lower(a.NAME) LIKE lower(?) ");
            params.add("%" + customer.getName() + "%");
        }
        //Dia chi dang ky kinh doanh
        if (!DataUtil.isStringNullOrEmpty(customer.getOfficeAddress())) {
            sqlQuery.append("           AND lower(a.OFFICE_ADDRESS) LIKE lower(?) ");
            params.add("%" + customer.getOfficeAddress() + "%");
        }
        //Danh sach khai thac
        if (!DataUtil.isStringNullOrEmpty(customer.getMineName())) {
            sqlQuery.append("           AND d.MINE_NAME = ? ");
            params.add(customer.getMineName());
        }
        //Nha cung cap
        if (!DataUtil.isStringNullOrEmpty(customer.getProvider())) {
            sqlQuery.append("           AND lower(d.PROVIDER) LIKE lower(?) ");
            params.add("%" + customer.getProvider() + "%");
        }
        //Trang thai cua bang khach hang - dich vu
        if (!DataUtil.isStringNullOrEmpty(customer.getStatus())) {
            sqlQuery.append("           AND b.STATUS = ? ");
            params.add(customer.getStatus());
        }
        if (!DataUtil.isStringNullOrEmpty(customer.getEmail())) {
            sqlQuery.append("           AND ( (lower(a.EMAIL) like ?)  OR  (lower(c.EMAIL) like ?) OR (lower(d.EMAIL) like ?))");
            params.add("%" + customer.getEmail() + "%");
            params.add("%" + customer.getEmail() + "%");
            params.add("%" + customer.getEmail() + "%");
        }
        //So dien thoai cua bang lich su giao dich
        if (!DataUtil.isStringNullOrEmpty(customer.getTelNumber())) {
            sqlQuery.append("           AND ( (lower(a.TEL_NUMBER) like ?)  OR  (lower(c.TEL_NUMBER) LIKE ? ) OR (lower(d.PHONE) LIKE ? ))");
            params.add("%" + customer.getTelNumber() + "%");
            params.add("%" + customer.getTelNumber() + "%");
            params.add("%" + customer.getTelNumber() + "%");
        }
        //Neu ma tinh duoc dien tim kiem theo ma tinh
        if (!DataUtil.isStringNullOrEmpty(customer.getTaxAuthority())) {
            sqlQuery.append("   AND a.TAX_AUTHORITY = ? ");
            params.add(customer.getTaxAuthority());
        }

        //Them ngay cham soc khach hang
        if (!DataUtil.isStringNullOrEmpty(customer.getCustCareHistoryCreatedDate())) {
            sqlQuery.append("   AND TO_CHAR(e.CREATE_DATE,'dd/MM/yyyy') = ? ");
            params.add(customer.getCustCareHistoryCreatedDate());
        }

        try {
            SQLQuery query = getSession().createSQLQuery(sqlQuery.toString());
            //Thuc hien chuyen du lieu lay ve thanh thanh doi tuong            
            query.setResultTransformer(Transformers.aliasToBean(CustomerDTO.class));
            query.addScalar("taxCode", new StringType());
            query.addScalar("name", new StringType());
            query.addScalar("agency", new StringType());
            query.addScalar("telNumber", new StringType());
            query.addScalar("email", new StringType());
            query.addScalar("officeAddress", new StringType());
            query.addScalar("taxAuthority", new StringType());
            query.addScalar("mineName", new StringType());
            query.addScalar("notes", new StringType());
            query.addScalar("createDate", new StringType());
            query.addScalar("status", new StringType());

            //Truyen cac tham so truyen vao de thuc hien tim kiem
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
            if (maxResult != Integer.MAX_VALUE) {
                query.setMaxResults(maxResult);
            }
            //Day du lieu ra danh sach doi tuong
            lstCustomers = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            lstCustomers = null;
        }
        return lstCustomers;
    }

    public List<CustomerDTO> getListCustomerWithTermInfo(List<ConditionBean> lstConditions) {
        String fromStartTime = null;
        String toStartTime = null;
        String fromEndTime = null;
        String toEndTime = null;
        String fromDateRegister = null;
        String toDateRegister = null;
        String provider = null;
        String service = null;
        String mineName = null;
        String taxAuthority = null;
        String maxSearch = null;

        for (ConditionBean c : lstConditions) {
            if ("service".equalsIgnoreCase(c.getField())) {
                service = c.getValue();
            }
            if ("provider".equalsIgnoreCase(c.getField())) {
                provider = c.getValue();
            }
            if ("taxAuthority".equalsIgnoreCase(c.getField())) {
                taxAuthority = c.getValue();
            }
            if ("mineName".equals(c.getField())) {
                mineName = c.getValue();
            }
            if ("maxSearch".equals(c.getField())) {
                maxSearch = c.getValue();
            }
            if ("startTime".equals(c.getField())) {
                if (String.valueOf(ConditionBean.Operator.NAME_GREATER_EQUAL).equalsIgnoreCase(c.getOperator())) {
                    fromStartTime = c.getValue();
                }
                if (String.valueOf(ConditionBean.Operator.NAME_LESS_EQUAL).equalsIgnoreCase(c.getOperator())) {
                    toStartTime = c.getValue();
                }
            }
            if ("endTime".equals(c.getField())) {
                if (String.valueOf(ConditionBean.Operator.NAME_GREATER_EQUAL).equalsIgnoreCase(c.getOperator())) {
                    fromEndTime = c.getValue();
                }
                if (String.valueOf(ConditionBean.Operator.NAME_LESS_EQUAL).equalsIgnoreCase(c.getOperator())) {
                    toEndTime = c.getValue();
                }
            }
            if ("dateRegister".equals(c.getField())) {
                if (String.valueOf(ConditionBean.Operator.NAME_GREATER_EQUAL).equalsIgnoreCase(c.getOperator())) {
                    fromDateRegister = c.getValue();
                }
                if (String.valueOf(ConditionBean.Operator.NAME_LESS_EQUAL).equalsIgnoreCase(c.getOperator())) {
                    toDateRegister = c.getValue();
                }
            }
        }
        List<CustomerDTO> lstCustomers = null;
        List params = new ArrayList();
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("       SELECT DISTINCT a.TAX_CODE taxCode, ");
        sqlQuery.append("               a.NAME name, ");
        sqlQuery.append("               a.AGENCY agency, ");
        sqlQuery.append("               a.TEL_NUMBER telNumber, ");
        sqlQuery.append("               a.EMAIL email, ");
        sqlQuery.append("               a.STATUS status, ");
        sqlQuery.append("               a.OFFICE_ADDRESS officeAddress, ");
        sqlQuery.append("               a.MINE_NAME mineName, ");
        sqlQuery.append("               d.SERVICE service, ");
        sqlQuery.append("               to_char(d.END_TIME,'dd/MM/yyyy') endTime, ");
        sqlQuery.append("               to_char(d.Start_time,'dd/MM/yyyy') startTime ");
        sqlQuery.append("       FROM CUSTOMER a ");
//        if (!DataUtil.isStringNullOrEmpty(provider)
//                || !DataUtil.isStringNullOrEmpty(fromStartTime)
//                || !DataUtil.isStringNullOrEmpty(toStartTime)
//                || !DataUtil.isStringNullOrEmpty(fromEndTime)
//                || !DataUtil.isStringNullOrEmpty(toEndTime)
//                || !DataUtil.isStringNullOrEmpty(mineName)) {
//        }
        sqlQuery.append("       LEFT JOIN TERM_INFORMATION d ");
        sqlQuery.append("            ON d.TAX_CODE = a.TAX_CODE ");
        sqlQuery.append("       LEFT JOIN CUSTOMER_STATUS b ");
        sqlQuery.append("            ON b.TAX_CODE = a.TAX_CODE ");
        sqlQuery.append("       WHERE 1=1 ");
        sqlQuery.append("       AND b.TAX_CODE IS NULL ");
        if (!DataUtil.isStringNullOrEmpty(taxAuthority)) {
            sqlQuery.append("   AND a.TAX_AUTHORITY = ? ");
            params.add(taxAuthority);
        }
        if (!DataUtil.isStringNullOrEmpty(fromStartTime)) {
            sqlQuery.append("   AND d.START_TIME >= TO_DATE(?,'dd/MM/yyyy') ");
            params.add(fromStartTime);
        }
        if (!DataUtil.isStringNullOrEmpty(toStartTime)) {
            sqlQuery.append("   AND d.START_TIME <= TO_DATE(?,'dd/MM/yyyy') ");
            params.add(toStartTime);
        }
        if (!DataUtil.isStringNullOrEmpty(fromEndTime)) {
            sqlQuery.append("   AND d.END_TIME >= TO_DATE(?,'dd/MM/yyyy') ");
            params.add(fromEndTime);
        }
        if (!DataUtil.isStringNullOrEmpty(toEndTime)) {
            sqlQuery.append("   AND d.END_TIME <= TO_DATE(?,'dd/MM/yyyy') ");
            params.add(toEndTime);
        }
        if (!DataUtil.isStringNullOrEmpty(fromDateRegister)) {
            sqlQuery.append("   AND d.DATE_REGISTER >= TO_DATE(?,'dd/MM/yyyy') ");
            params.add(fromDateRegister);
        }
        if (!DataUtil.isStringNullOrEmpty(toDateRegister)) {
            sqlQuery.append("   AND d.DATE_REGISTER <= TO_DATE(?,'dd/MM/yyyy') ");
            params.add(toDateRegister);
        }
        if (!DataUtil.isStringNullOrEmpty(service)) {
            sqlQuery.append("   AND d.SERVICE = ? ");
            params.add(service);
        }
        if (!DataUtil.isStringNullOrEmpty(mineName)) {
            sqlQuery.append("           AND d.MINE_NAME = ? ");
            params.add(mineName);
        }
        //Nha cung cap
        if (!DataUtil.isStringNullOrEmpty(provider)) {
            sqlQuery.append("           AND lower(d.PROVIDER) LIKE lower(?) ");
            params.add("%" + provider + "%");
        }
        sqlQuery.append("           ORDER BY endTime desc, startTime desc ");

        SQLQuery query;
        try {
            query = getSession().createSQLQuery(sqlQuery.toString());
            //Thuc hien chuyen du lieu lay ve thanh thanh doi tuong            
            query.setResultTransformer(Transformers.aliasToBean(CustomerDTO.class));
            query.addScalar("taxCode", new StringType());
            query.addScalar("name", new StringType());
            query.addScalar("agency", new StringType());
            query.addScalar("telNumber", new StringType());
            query.addScalar("email", new StringType());
            query.addScalar("status", new StringType());
            query.addScalar("officeAddress", new StringType());
            query.addScalar("mineName", new StringType());
            query.addScalar("service", new StringType());
            query.addScalar("startTime", new StringType());
            query.addScalar("endTime", new StringType());

            //Truyen cac tham so truyen vao de thuc hien tim kiem
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }

            //Day du lieu ra danh sach doi tuong
            if (!DataUtil.isStringNullOrEmpty(maxSearch)) {
                if (DataUtil.isInteger(maxSearch)) {
                    query.setMaxResults(Integer.parseInt(maxSearch));
                }
            }
            lstCustomers = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstCustomers;
    }

    public ResultDTO insertOrUpdateListCustomer(List<CustomerDTO> lstCustomers) {
        return null;
    }

    /**
     * Get list customer existed
     *
     * @param lstTaxCodes
     * @return
     */
    public List<CustomerDTO> getCustomerExisted(List<String> lstTaxCodes) {
        List<CustomerDTO> lstCustomers = null;
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("       SELECT DISTINCT a.TAX_CODE taxCode, a.cust_id custId ");
        sqlQuery.append("       FROM CUSTOMER a WHERE 1=1 ");
        if (!DataUtil.isListNullOrEmpty(lstTaxCodes)) {
            sqlQuery.append("       AND   a.TAX_CODE IN ");
            sqlQuery.append("( :idx").append(String.valueOf(0)).append(" )");
            if (lstTaxCodes.size() > 1) {
                for (int index = 1; index < lstTaxCodes.size(); index++) {
                    sqlQuery.append("       OR   a.TAX_CODE IN ");
                    sqlQuery.append("( :idx").append(String.valueOf(index)).append(" )");
                }
            }
            SQLQuery query;
            query = getSession().createSQLQuery(sqlQuery.toString());
            try {
                //Thuc hien chuyen du lieu lay ve thanh thanh doi tuong            
                query.setResultTransformer(Transformers.aliasToBean(CustomerDTO.class));
                query.addScalar("taxCode", new StringType());
                query.addScalar("custId", new StringType());
                for (int index = 0; index < lstTaxCodes.size(); index++) {
                    query.setParameterList("idx" + String.valueOf(index), DataUtil.parseInputListString(lstTaxCodes.get(index)));
                }
                lstCustomers = query.list();

            } catch (Exception e) {
                e.printStackTrace();
                lstCustomers = null;
            }
        }
        return lstCustomers;
    }

}
