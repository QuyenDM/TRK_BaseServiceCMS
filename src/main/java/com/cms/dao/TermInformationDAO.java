/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cms.dao;

import com.vfw5.base.dao.BaseFWDAOImpl;
import com.cms.model.TermInformation;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author QuyenDM
 * @version 1.0
 * @since 7/19/2016 12:01 AM
 */
@Repository("termInformationDAO")
public class TermInformationDAO extends BaseFWDAOImpl<TermInformation, Long> {

    public TermInformationDAO() {
        this.model = new TermInformation();
    }

    public TermInformationDAO(Session session) {
        this.session = session;
    }

    public Map<String, List<String>> getProviderAndTaxAuthorityByCategoryList(String categoryId) {
        StringBuilder sqlProvider = new StringBuilder();
        sqlProvider.append("SELECT DISTINCT lower(a.PROVIDER) ");
        sqlProvider.append("  FROM term_information a ");
        sqlProvider.append("  WHERE a.MINE_NAME = ? ");
        SQLQuery queryProvider = getSession().createSQLQuery(sqlProvider.toString());
        StringBuilder sqlTaxAuthority = new StringBuilder();
        return null;
    }

}
