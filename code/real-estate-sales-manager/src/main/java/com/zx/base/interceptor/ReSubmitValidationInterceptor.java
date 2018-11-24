package com.zx.base.interceptor;

import com.zx.base.annotation.FormReSubmitValidation;
import com.zx.base.model.ReturnModel;
import com.zx.lib.json.JsonUtil;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 表单重复提交拦截器
 *
 * @author Xiafl.
 * @version 2018/02/28
 */
public class ReSubmitValidationInterceptor extends HandlerInterceptorAdapter {

    public static final String SESSION_KEY = "_f_formId_";

    private static final String FORM_FIELD_NAME = "_f_token";

    /**
     * 方法执行之前执行
     *
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(FormReSubmitValidation.class)) {
            return true;
        }
        FormReSubmitValidation annotation = method.getAnnotation(FormReSubmitValidation.class);
        if (annotation == null) {
            return true;
        }
        HttpSession httpSession = request.getSession();
        if (annotation.value()) {
            if (httpSession.getAttribute(SESSION_KEY) == null) {
                handleRepeatSubmit(request, response);
                return false;
            } else {
                Map<String, String> formTokenMap = (LinkedHashMap<String, String>) httpSession.getAttribute(SESSION_KEY);
                String referer = request.getHeader("Referer");
                if (StringUtils.isEmpty(referer)) {
                    handleRepeatSubmit(request, response);
                    return false;
                }
                URI uri = new URI(referer);
                String requestReferer = uri.getPath();
                if (StringUtils.isEmpty(requestReferer)) {
                    handleRepeatSubmit(request, response);
                    return false;
                }
                if (!formTokenMap.containsKey(requestReferer)) {
                    handleRepeatSubmit(request, response);
                    return false;
                }
                String serverToken = formTokenMap.get(requestReferer);
                String clientToken = request.getParameter(FORM_FIELD_NAME);
                if (StringUtils.isEmpty(serverToken) || StringUtils.isEmpty(clientToken)) {
                    unRegisterForm(request, requestReferer);
                    return false;
                }
                if (serverToken.equals(clientToken)) {
                    return true;
                } else {
                    handleRepeatSubmit(request, response);
                    return false;
                }
            }
        } else {
            String token = UUID.randomUUID().toString();
            String requestPath = request.getRequestURI();
            request.setAttribute(FORM_FIELD_NAME, token);
            registerForm(request,requestPath,token);
            return true;
        }
    }

    /**
     * 方法执行完成之后，视图渲染之前执行
     *
     **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getAttribute("_f_token") != null) {
            modelAndView.addObject("_f_token", request.getAttribute("_f_token"));
        }
    }

    /**
     * 处理重复提交或者表单过期
     *
     **/
    private void handleRepeatSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getHeader("x-requested-with") != null && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
            // 对AJAX请求，需要返回统一格式的JSON数据，前台根据返回结果相应给客户
            ReturnModel returnModel = new ReturnModel();
            returnModel.setState(false);
            returnModel.setMessage("表单已过期，请重新编辑");
            // 设定编码，否则中文乱码
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtil.getJsonString(returnModel));
        } else {
            // 跳转到表单过期页面
            //response.setContentType("text/html;charset=utf-8");// 设定编码
            response.sendRedirect("/error?errorMsg="+ URLEncoder.encode("表单已过期，请刷新页面或重新编辑","UTF-8"));
        }
    }

    private static void registerForm(HttpServletRequest request, String formId, String token) {
        if (StringUtils.isEmpty(formId) || StringUtils.isEmpty(token)){
            return;
        }
        HttpSession httpSession = request.getSession();
        Map<String, String> map;
        if (httpSession.getAttribute(SESSION_KEY) == null) {
            map = new LinkedHashMap<>();
        } else {
            // 有序Map
            map = (LinkedHashMap<String, String>) httpSession.getAttribute(SESSION_KEY);
        }
        if (map.containsKey(formId)) {
            map.replace(formId, token);
        } else {
            map.put(formId, token);
        }
        httpSession.setAttribute(SESSION_KEY, map);
    }

    private static void unRegisterForm(HttpServletRequest request, String formId) {
        if (StringUtils.isEmpty(formId)){
            return;
        }
        HttpSession httpSession = request.getSession();
        Map<String, String> formTokenMap = (LinkedHashMap<String, String>) httpSession.getAttribute(SESSION_KEY);
        formTokenMap.remove(formId);
        httpSession.setAttribute(SESSION_KEY, formTokenMap);
    }

    @Aspect
    @Component
    public static class ReSubmitValidationAspect {
        @Pointcut("@annotation(com.zx.base.annotation.FormReSubmitValidation)")
        public void doAspect() { }

        /**
         * 获取返回结果
         *
         * @param joinPoint 连接点
         * @param rvt       返回值
         **/
        @AfterReturning(pointcut = "doAspect()", returning = "rvt")
        public void afterReturning(JoinPoint joinPoint, Object rvt) throws URISyntaxException {

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            if (!method.isAnnotationPresent(FormReSubmitValidation.class)) {
                return;
            }
            FormReSubmitValidation formReSubmitValidation = method.getAnnotation(FormReSubmitValidation.class);
            if (!formReSubmitValidation.value()){
                return;
            }

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            // 当前请求
            HttpServletRequest request = attributes.getRequest();
            if (!method.isAnnotationPresent(ResponseBody.class)) {
                // 返回页面或者其他
                return;
            }
            String requestReferer = request.getHeader("Referer");
            if (StringUtils.isEmpty(requestReferer)) {
                return;
            }
            URI uri = new URI(requestReferer);
            String formId = uri.getPath();
            if (rvt instanceof ReturnModel) {
                ReturnModel returnModel = (ReturnModel) rvt;
                if (returnModel.getState()) {
                    unRegisterForm(request, formId);
                }
            } else {
                unRegisterForm(request, formId);
            }
        }
    }

}
