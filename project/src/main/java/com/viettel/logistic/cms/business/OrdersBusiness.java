/*
 * Copyright (C) 2015 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.cms.business;

import com.cms.dao.OrdersDAO;
import com.cms.dto.OrdersDTO;
import com.cms.model.Orders;
import com.viettel.vfw5.base.service.BaseFWServiceImpl;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
* @author binhnt22@viettel.com.vn
* @version 1.0
* @since Apr 2015
*/
@Service("ordersBusiness")
@Transactional
public class OrdersBusiness extends BaseFWServiceImpl<OrdersDAO, OrdersDTO, Orders> {
    @Autowired
    OrdersDAO ordersDAO;
    
     public OrdersBusiness() {
        tModel = new Orders();
    }
     
    @Override
    public OrdersDAO gettDAO() {
        return ordersDAO;
    }
     
}
