package com.app.master.core.service.impl;

import com.app.hibernate.framework.dao.IDao;
import com.app.hibernate.framework.service.impl.ServiceImpl;
import com.app.master.core.service.DaoService;
import com.app.master.core.service.${domainName?cap_first}Service;
import com.app.master.po.T${domainName};
import com.app.master.vo.V${domainName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${description}
 * @time	${date}
 */
@Service("${domainName}Service")
public class ${domainName?cap_first}ServiceImpl extends ServiceImpl<T${domainName}, V${domainName}> implements ${domainName?cap_first}Service {

    @Autowired
    private IDao<T${domainName}> ${domainName}Dao;

}
