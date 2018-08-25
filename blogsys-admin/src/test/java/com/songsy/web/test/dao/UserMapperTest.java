/**
 * FileName:     UserMapperTest.java
 *
 * @Description: TODO
 * All rights Reserved, Designed By ALIBABA.COM
 * Copyright:    Copyright(C) 1998-2015
 * Company       ALIBABA
 * @author: asua
 * @version V1.0
 * Createdate:         2017年7月16日 下午12:58:33
 * <p>
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2017年7月16日       CQCN         1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
package com.songsy.web.test.dao;

import com.songsy.admin.dao.UserMapper;
import com.songsy.admin.entity.User;
import com.songsy.web.test.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @title:
 * @description:
 * @author asua
 * @date 2017年7月16日
 */
public class UserMapperTest extends BaseTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper mapper;

    @Test
    public void findAll() throws Exception {
        User user = mapper.selectByPrimaryKey(1);
        logger.info("logger............");
        System.out.println(user);
    }


}
