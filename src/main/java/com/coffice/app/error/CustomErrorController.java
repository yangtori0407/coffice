package com.coffice.app.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coffice.app.users.UserVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
    public String handleError(HttpServletRequest request, @AuthenticationPrincipal UserVO userVO) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 401) return "error/401";
            if (statusCode == 403) {         	
            	return "error/403";
            }
            if (statusCode == 404) return "error/404";
        }
        return "error/error"; // generic error
    }
}
