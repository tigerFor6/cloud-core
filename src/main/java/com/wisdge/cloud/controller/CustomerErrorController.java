package com.wisdge.cloud.controller;

import com.wisdge.cloud.dto.ApiResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CustomerErrorController implements ErrorController {
    @RequestMapping(value = "/error")
    public ApiResult handleError(HttpServletResponse response, Exception e) {
        int code = response.getStatus();
        switch (code) {
            case 404:
                return ApiResult.notFound();
            case 403:
                return ApiResult.forbidden();
            case 401:
                return ApiResult.unauthorized();
            case 500:
                return ApiResult.internalError();
            default:
                return ApiResult.fail(e.getMessage());
        }
    }
}
