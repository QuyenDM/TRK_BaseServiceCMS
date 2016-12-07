

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.cms.dao;
import com.vfw5.base.dao.BaseFWDAOImpl;
import com.cms.model.AppParams;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
* @author TruongBX3
* @version 1.0
* @since 16-Apr-15 11:11 AM
*/
@Repository("appParamsDAO")
public class AppParamsDAO extends BaseFWDAOImpl<AppParams, Long> {

    public AppParamsDAO() {
        this.model= new AppParams();
    }

    public AppParamsDAO(Session session) {
        this.session = session;
    }
}

