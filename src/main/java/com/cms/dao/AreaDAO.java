

/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.cms.dao;
import com.vfw5.base.dao.BaseFWDAOImpl;
import com.cms.model.Area;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
* @author TruongBX3
* @version 1.0
* @since 05-May-15 4:28 PM
*/
@Repository("areaDAO")
public class AreaDAO extends BaseFWDAOImpl<Area, Long> {

    public AreaDAO() {
        this.model= new Area();
    }

    public AreaDAO(Session session) {
        this.session = session;
    }
}

