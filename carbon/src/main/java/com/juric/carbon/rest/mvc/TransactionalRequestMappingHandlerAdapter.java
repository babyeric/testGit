package com.juric.carbon.rest.mvc;

import com.practice.exception.TransactionalException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/30/15
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionalRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    private PlatformTransactionManager transactionManager;

    @Resource(name="transactionManager")
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    protected ModelAndView handleInternal(final HttpServletRequest request,
                                          final HttpServletResponse response, final HandlerMethod handlerMethod) throws Exception {
        final TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        ModelAndView modelAndView;
        try {
            modelAndView = template.execute(new TransactionCallback<ModelAndView>() {
                @Override
                public ModelAndView doInTransaction(TransactionStatus status) {
                    try {
                        return superHandleInternal(request, response, handlerMethod);
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        throw new TransactionalException(e);
                    }
                }
            });
        } catch (TransactionalException e) {
            throw (Exception)e.getCause();
        }

        return modelAndView;
    }

    private ModelAndView superHandleInternal(HttpServletRequest request,
                                              HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        return super.handleInternal(request, response, handlerMethod);
    }

}
