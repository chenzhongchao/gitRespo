package com.fise.framework.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fise.base.ErrorCode;
import com.fise.base.Response;
import com.fise.framework.exception.AuthException;
import com.fise.framework.exception.RequestHeaderException;
import com.qq.jutil.j4log.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/** 
 * @author marsman
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-27
 * @desc 用于处理所有的异常情况
 */

@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

    private static Logger logger = Logger.getLogger("exception_advice");

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("缺少请求参数", e);
        return new Response().failure(ErrorCode.ERROR_PRRAM_NOT_COMPLETE_EXCEPTION);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析失败", e);
        return new Response().failure(ErrorCode.ERROR_PARAM_PARSE_JSON_EXCEPTION);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    	logger.error("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        logger.debug("message: " + message);

        Response resp = new Response();
        resp.failure(ErrorCode.ERROR_PARAM_NOT_VALID_EXCEPTION);
        resp.setMsg(ErrorCode.ERROR_PARAM_NOT_VALID_EXCEPTION.getMsg() + message);
        return resp;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Response handleBindException(BindException e) {
        logger.error("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        logger.debug("message: " + message);
        return new Response().failure(ErrorCode.ERROR_PARAM_BIND_EXCEPTION);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleServiceException(ConstraintViolationException e) {
        logger.error("参数违反约束", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        logger.debug("message: " + message);
        return new Response().failure(ErrorCode.ERROR_PARAM_VIOLATION_EXCEPTION);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Response handleValidationException(ValidationException e) {
        logger.error("参数验证失败", e);
        return new Response().failure(ErrorCode.ERROR_PARAM_VALIDATION_EXCEPTION);
    }
    
    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestHeaderException.class)
    public Response handleRequestHeaderException(RequestHeaderException e) {
        logger.error("请求头验证失败", e);
        return new Response().failure(ErrorCode.ERROR_REQUEST_HEADER_ERROR);
    }
    
    /**
     * 401 - Unauthorized
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    public Response handleTokenException(AuthException e) {
        logger.error("access token验证失败", e);
        return new Response().failure(ErrorCode.ERROR_REQUEST_AUTH_FAILED);
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        return new Response().failure(ErrorCode.ERROR_REQUEST_METHOD_NOT_SUPPORTED);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("不支持当前媒体类型", e);
        return new Response().failure(ErrorCode.ERROR_MEDIATYPE_NOT_SUPPORTED);
    }
    
    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        logger.error("通用异常", e);
        return new Response().failure(ErrorCode.ERROR_SYSTEM);
    }
}
