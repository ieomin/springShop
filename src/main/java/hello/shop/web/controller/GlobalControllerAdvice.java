package hello.shop.web.controller;

import hello.shop.entity.Member;
import hello.shop.repository.item.ItemSearchCond;
import hello.shop.service.MemberService;
import hello.shop.web.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    @ModelAttribute("currentMember")
    public Member getCurrentMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // HTTP 세션이 없는 경우 null 반환
        if (session != null) {
            Object sessionAttribute = session.getAttribute(SessionConst.LOGIN_MEMBER);
            if (sessionAttribute instanceof Member) {
                return (Member) sessionAttribute;
            }
        }
        return null;
    }

    @ModelAttribute("currentItemSearchCond")
    public ItemSearchCond getCurrentItemSearchCond() {
        return new ItemSearchCond();
    }
}