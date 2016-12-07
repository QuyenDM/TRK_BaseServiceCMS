/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cms.business;

import com.vfw5.base.service.BaseFWServiceImpl;
import com.cms.dto.AreaDTO;
import com.cms.model.Area;
import com.cms.dao.AreaDAO;
import org.hibernate.Session;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author TruongBX3
 * @version 1.0
 * @since 05-May-15 4:28 PM
 */
@Service("areaBusiness")
@Transactional
public class AreaBusiness extends BaseFWServiceImpl<AreaDAO, AreaDTO, Area> {
	
    @Autowired
    private AreaDAO areaDAO;

    public AreaBusiness() {
        tModel = new Area();
        tDAO = areaDAO;
    }
    @Override
    public AreaDAO gettDAO() {
        return areaDAO;
    }
    
    public AreaBusiness(Session session) {
        this.session = session;
        tModel = new Area();
        tDAO = areaDAO;
    }
}


