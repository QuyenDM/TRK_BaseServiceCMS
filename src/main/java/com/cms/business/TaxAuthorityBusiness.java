/**
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cms.business;

import com.cms.business.service.TaxAuthorityBusinessInterface;
import com.cms.model.TaxAuthority;
import com.cms.dao.TaxAuthorityDAO;
import com.cms.dto.TaxAuthorityDTO;
import com.vfw5.base.service.BaseFWServiceImpl;
import java.util.List;
import org.hibernate.Session;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author QuyenDM
 * @version 1.0
 * @since 8/23/2016 11:13 PM
 */
@Service("taxAuthorityBusiness")
@Transactional
public class TaxAuthorityBusiness extends BaseFWServiceImpl<TaxAuthorityDAO, TaxAuthorityDTO, TaxAuthority> implements TaxAuthorityBusinessInterface {

    @Autowired
    private TaxAuthorityDAO taxAuthorityDAO;

    public TaxAuthorityBusiness() {
        tModel = new TaxAuthority();
        tDAO = taxAuthorityDAO;
    }

    @Override
    public TaxAuthorityDAO gettDAO() {
        return taxAuthorityDAO;
    }

    public TaxAuthorityBusiness(Session session) {
        this.session = session;
        tModel = new TaxAuthority();
        tDAO = taxAuthorityDAO;
    }

    public List<TaxAuthorityDTO> getListProvineTaxAuthority() {
        return gettDAO().getListProvinces();
    }
}
