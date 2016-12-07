/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cms.dao;

import com.vfw5.base.dao.BaseFWDAOImpl;
import com.cms.model.CustomerStatus;
import com.cms.statistics.dto.StatisticsCategoryListDTO;
import com.vfw5.base.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * @author QuyenDM
 * @version 1.0
 * @since 7/30/2016 5:27 PM
 */
@Repository("customerStatusDAO")
public class CustomerStatusDAO extends BaseFWDAOImpl<CustomerStatus, Long> {

    public CustomerStatusDAO() {
        this.model = new CustomerStatus();
    }

    public CustomerStatusDAO(Session session) {
        this.session = session;
    }

    /**
     * Thong ke trang thai cua khach hang
     *
     * @param staffCode ma nhan vien
     * @param categoryId id cua danh sach
     * @param beginLastUpdated tu ngay cap nhat lan cuoi
     * @param endLastUpdated den ngay cap nhat lan cuoi
     * @return
     */
    public List<StatisticsCategoryListDTO> getStatisticsCategoryListByStaff(
            String staffCode, String categoryId, String beginLastUpdated, String endLastUpdated) {
        List<StatisticsCategoryListDTO> lstStatisticsCategoryList = null;
        StringBuilder sqlQuery = new StringBuilder();
        List params = new ArrayList();
        sqlQuery.append("        SELECT code, ");
        sqlQuery.append("         status1, ");
        sqlQuery.append("         status2, ");
        sqlQuery.append("         status3, ");
        sqlQuery.append("         status4, ");
        sqlQuery.append("         status5, ");
        sqlQuery.append("         status6, ");
        sqlQuery.append("         status7, ");
        sqlQuery.append("         status8, ");
        sqlQuery.append("         status9, ");
        sqlQuery.append("         status10, ");
        sqlQuery.append("         status11, ");
        sqlQuery.append("         status12, ");
        sqlQuery.append("         status13, ");
        sqlQuery.append("         status14 ");
        sqlQuery.append("       FROM ");
        sqlQuery.append("         (SELECT cl.code    AS code, ");
        sqlQuery.append("           cs.status        AS status, ");
        sqlQuery.append("           ti.tax_code ");
        sqlQuery.append("         FROM CUSTOMER_STATUS cs ");
        sqlQuery.append("           JOIN TERM_INFORMATION ti ");
        sqlQuery.append("               ON ti.TAX_CODE = cs.TAX_CODE ");
        sqlQuery.append("                   JOIN category_list cl ");
        sqlQuery.append("                       ON ti.mine_name = cl.id ");
        sqlQuery.append("         WHERE           1         = 1 ");
        if (!DataUtil.isStringNullOrEmpty(categoryId)) {
            sqlQuery.append("         AND ti.MINE_NAME      = ? ");
            params.add(categoryId);
        }
        if (!DataUtil.isStringNullOrEmpty(beginLastUpdated)) {
            sqlQuery.append("         AND cs.LAST_UPDATED  >= to_date( ? ,'dd/MM/yyyy') ");
            params.add(beginLastUpdated);
        }
        if (!DataUtil.isStringNullOrEmpty(endLastUpdated)) {
            sqlQuery.append("         AND cs.LAST_UPDATED  <= to_date( ? ,'dd/MM/yyyy') ");
            params.add(endLastUpdated);
        }
        if (!DataUtil.isStringNullOrEmpty(staffCode)) {
            sqlQuery.append("         AND cs.staff_code    = ? ");
            params.add(staffCode);
        }
        sqlQuery.append("         ) PIVOT (COUNT(DISTINCT tax_code) ");
        sqlQuery.append("           FOR (status) IN ('1' AS status1,'2' AS status2,'3' AS status3,");
        sqlQuery.append("                            '4' AS status4,'5' AS status5,'6' AS status6,");
        sqlQuery.append("                            '7' AS status7,'8' AS status8,'9' AS status9,");
        sqlQuery.append("                            '10' AS status10,'11' AS status11,'12' AS status12,");
        sqlQuery.append("                            '13' AS status13,'14' AS status14)) ");
        sqlQuery.append("         ORDER BY code asc ");

        SQLQuery query;
        try {
            query = getSession().createSQLQuery(sqlQuery.toString());
            //Thuc hien chuyen du lieu lay ve thanh thanh doi tuong            
            query.setResultTransformer(Transformers.aliasToBean(StatisticsCategoryListDTO.class));
            query.addScalar("code", new StringType());
            query.addScalar("status1", new StringType());
            query.addScalar("status2", new StringType());
            query.addScalar("status3", new StringType());
            query.addScalar("status4", new StringType());
            query.addScalar("status5", new StringType());
            query.addScalar("status6", new StringType());
            query.addScalar("status7", new StringType());
            query.addScalar("status8", new StringType());
            query.addScalar("status9", new StringType());
            query.addScalar("status10", new StringType());
            query.addScalar("status11", new StringType());
            query.addScalar("status12", new StringType());
            query.addScalar("status13", new StringType());
            query.addScalar("status14", new StringType());

            //Truyen cac tham so truyen vao de thuc hien tim kiem
            if (!DataUtil.isListNullOrEmpty(params)) {
                for (int i = 0; i < params.size(); i++) {
                    query.setParameter(i, params.get(i));
                }
            }

            lstStatisticsCategoryList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lstStatisticsCategoryList;
    }
}
