package hello.shop.web.interceptor;

import hello.shop.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인증 인셉 15: [{}]", requestURI);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("인증 인셉 15(미인증)");
            //로그인으로 redirect
            response.sendRedirect("/member/login?redirectURL=" + requestURI);
            return false;
        }
        return true;
    }
}
