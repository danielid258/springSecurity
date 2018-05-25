package com.daniel.security.web.handler;

import com.daniel.security.exception.UserNotExistException;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

/**
 * on 5/25/2018.
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * 所有其他controller中抛出的 Us erNotExistException 都将被本方法捕获并处理
     *
     * @return
     */
    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> handleUserNotExistException(UserNotExistException e) {
        System.out.println("ControllerExceptionHandler handleUserNotExistException execute : user not exist,id= " + e.getId());

        Map<String, Object> map = new HashedMap();
        map.put("id", e.getId());
        map.put("message", e.getMessage());
        return map;
    }
}
