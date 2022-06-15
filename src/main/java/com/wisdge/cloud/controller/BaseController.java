package com.wisdge.cloud.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class BaseController {
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpSession session;

    public String i18n(String key, Object...objects) {
        return i18n(request.getLocale(), "i18n", key, objects);
    }

    public String i18n(Locale locale, String baseName, String key, Object...objects) {
        ResourceBundle bundle = ResourceBundle.getBundle("resources/" + baseName, locale);
        if (bundle.containsKey(key)) {
            String resource = bundle.getString(key);
            if (objects != null && objects.length > 0)
                return MessageFormat.format(resource, objects);
            return resource;
        } else {
            return key;
        }
    }

}
