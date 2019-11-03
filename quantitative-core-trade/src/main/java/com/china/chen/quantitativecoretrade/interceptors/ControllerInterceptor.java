package com.china.chen.quantitativecoretrade.interceptors;

import com.china.chen.quantitativecoretrade.response.common.MessageException;
import com.china.chen.quantitativecoretrade.response.common.ResultBean;
import com.china.chen.quantitativecoretrade.response.common.ResultCode;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerInterceptor {
    @Pointcut("execution(public com.china.chen.quantitativecoretrade.response.common.ResultBean *(..))")
    public void resultBean() {
    }

    @Around("resultBean()")
    private ResultBean<?> handlerException(ProceedingJoinPoint pjp) {

        ResultBean<?> result;
        Object[] objects = pjp.getArgs();
        try{
            // 执行
            result = (ResultBean<?>) pjp.proceed(objects);
            return result;
        } catch (Throwable e) {
            // 多语言错误处理
            if (e instanceof MessageException) {
                MessageException message = (MessageException) e;
                result = ResultBean.code(message.getCode(), message.getMessage());
            }else{
                result = ResultBean.fail(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMsg());
            }
            return result;
        }
    }
}
