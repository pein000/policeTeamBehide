package com.fee.service;


import com.fee.repository.LoginRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pein on 2016/3/1.
 */
@Service
public class LoginService {

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private LoginRepository loginRepository;

    public boolean login() {
        logger.info("LoginService : begin to login from LoginService");
        return loginRepository.login();
    }
}
