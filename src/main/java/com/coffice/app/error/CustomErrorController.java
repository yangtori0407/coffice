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
	
    @RequestMapping("/error/403")
    public String error403() {
        return "error/403"; // resources/templates/error/403.html (또는 .jsp)
    }

	@RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == 400) return "error/400";
            if (statusCode == 404) return "error/404";
            if (statusCode == 500) return "error/500";
        }
        return "error/error"; // generic error
    }
}
