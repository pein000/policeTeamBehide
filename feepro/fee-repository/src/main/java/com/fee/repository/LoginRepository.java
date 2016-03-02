package com.fee.repository;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * Created by pein on 2016/3/1.
 */
@Repository
public class LoginRepository {

    private Logger logger = Logger.getLogger(getClass());

    public boolean login() {
        logger.info("LoginRepository : begin to login");
        return  false;
    }
}
