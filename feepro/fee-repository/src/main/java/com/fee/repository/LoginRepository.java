package com.fee.repository;


import com.fee.domain.User;
import com.fee.entity.UserEntity;
import com.fee.util.ProUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by pein on 2016/3/1.
 */
@Repository
public class LoginRepository extends BaseRepository {

    private Logger logger = Logger.getLogger(getClass());

    public User login(String name, String password) {
        logger.info("LoginRepository : begin to login");
        Criteria criteria = getSession().createCriteria(UserEntity.class);
        criteria.add(Restrictions.eq("name", name));
        criteria.add(Restrictions.eq("password", password));
        UserEntity result = (UserEntity) criteria.uniqueResult();
        if (result == null) {
            return null;
        }
        return ProUtils.copyProperties(result,User.class);
    }

    public User login1(String name, String password) {
        Query query = getSession().createQuery("from UserEntity as user where user.name = :name and user.password = :password") ;
        query.setString("name", name);
        query.setString("password", password);
        UserEntity result = (UserEntity) query.uniqueResult();
        if (result == null) {
            return null;
        }
        return ProUtils.copyProperties(result,User.class);
    }

}
