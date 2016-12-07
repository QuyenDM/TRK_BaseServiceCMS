package com.cms.dao;

import com.cms.dto.CategoryListDTO;
import com.cms.model.CategoryList;
import com.vfw5.base.dao.BaseFWDAOImpl;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * @author QuyenDM
 * @version 1.0
 * @since 8/19/2016 12:12 AM
 */
@Repository("categoryListDAO")
public class CategoryListDAO extends BaseFWDAOImpl<CategoryList, Long> {

    public CategoryListDAO() {
        this.model = new CategoryList();
    }

    public CategoryListDAO(Session session) {
        this.session = session;
    }

    public List<CategoryListDTO> getCategoryListWithQuanlity() {
        StringBuilder sb = new StringBuilder();
        sb.append("    SELECT cl.code, ");
        sb.append("      cl.name, ");
        sb.append("      cl.RECEIVED_DATE, ");
        sb.append("      cl.END_DATE, ");
        sb.append("      cl.DESCRIPTION, ");
        sb.append("      cl.CREATOR, ");
        sb.append("      COUNT(*) AS cust_quanlity ");
        sb.append("    FROM CATEGORY_LIST cl ");
        sb.append("    JOIN TERM_INFORMATION ti ");
        sb.append("    ON cl.ID = ti.MINE_NAME ");
        sb.append("    GROUP BY cl.code, ");
        sb.append("      cl.name, ");
        sb.append("      cl.RECEIVED_DATE, ");
        sb.append("      cl.END_DATE, ");
        sb.append("      cl.DESCRIPTION, ");
        sb.append("      cl.CREATOR");
        return null;
    }

    public void updateQuanlityForCategoryList(String categoryId, int quanlitySuccess) {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE CATEGORY_LIST a SET a.CUST_QUANTITY = (select count(distinct ti.tax_code) from term_information ti where ti.MINE_NAME = a.id) WHERE a.id= :idx0 ");
        try {
            SQLQuery query = getSession().createSQLQuery(sb.toString());
            query.setParameter("idx0", categoryId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
