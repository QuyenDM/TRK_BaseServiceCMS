/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cms.dao;

import com.cms.dto.TaxAuthorityDTO;
import com.cms.dto.TaxAuthorityDTO;
import com.cms.model.TaxAuthority;
import com.vfw5.base.dao.BaseFWDAOImpl;
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
 * @since 8/23/2016 11:13 PM
 */
@Repository("taxAuthorityDAO")
public class TaxAuthorityDAO extends BaseFWDAOImpl<TaxAuthority, Long> {

    public TaxAuthorityDAO() {
        this.model = new TaxAuthority();
    }

    public TaxAuthorityDAO(Session session) {
        this.session = session;
    }

    public List<TaxAuthorityDTO> getListProvinces() {
        List<TaxAuthorityDTO> lstTaxAuthorities;
//        List params = new ArrayList();
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("       SELECT a.MA_CQT maCqt, ");
        sqlQuery.append("         a.TEN_CQT tenCqt, ");
        sqlQuery.append("         a.MA_QUAN_HUYEN maQuanHuyen, ");
        sqlQuery.append("         a.MA_TINH maTinh, ");
        sqlQuery.append("         a.STATUS status ");
        sqlQuery.append("       FROM tax_authority a ");
        sqlQuery.append("       WHERE SUBSTR(a.MA_CQT,4,2) ='00' ");

        try {
            SQLQuery query = getSession().createSQLQuery(sqlQuery.toString());
            //Thuc hien chuyen du lieu lay ve thanh thanh doi tuong            
            query.setResultTransformer(Transformers.aliasToBean(TaxAuthorityDTO.class));
            query.addScalar("maCqt", new StringType());
            query.addScalar("tenCqt", new StringType());
            query.addScalar("maQuanHuyen", new StringType());
            query.addScalar("maTinh", new StringType());
            query.addScalar("status", new StringType());

            //Truyen cac tham so truyen vao de thuc hien tim kiem
//            for (int i = 0; i < params.size(); i++) {
//                query.setParameter(i, params.get(i));
//            }
            //Day du lieu ra danh sach doi tuong
            lstTaxAuthorities = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            lstTaxAuthorities = null;
        }
        return lstTaxAuthorities;
    }
}
