package com.practice.db;

import com.practice.user.UserDB;
import com.practice.user.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/10/15
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DataService {

    private UserMapper userMapper;
    private PlatformTransactionManager transactionManager;

    @Resource(name="userMapper")
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Resource(name="transactionManager")
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }


    public String talk() {
         final TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
       /*return template.execute(new TransactionCallback<String>() {
            @Override
            public String doInTransaction(TransactionStatus status) {     */
                template.execute(new TransactionCallback<String>() {
                    @Override
                    public String doInTransaction(TransactionStatus status) {
                        try {
                            UserDB user = new UserDB();
                            //user.setUserId(5002L);
                            user.setName("user_100");
                            userMapper.insert(user);
                            //status.setRollbackOnly();
                            throw new RuntimeException("");
                        } catch (Exception e) {
                            status.setRollbackOnly();
                        }
                        return  "";
                        //return user.getUserId() + user.getName() + user.getBirthday();
                    }
                });
                /*
                UserDB user = new UserDB();
                user.setUserId(103L);
                user.setName("user_101");
                userMapper.insert(user);
                status.setRollbackOnly();
                return null;
            }
         });*/
        return null;
    }
}
