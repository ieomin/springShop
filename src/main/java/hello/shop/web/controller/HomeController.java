package hello.shop.web.controller;

import hello.shop.entity.Member;
import hello.shop.service.MemberService;
import hello.shop.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String homeGet(@Login Member loginMember, Model model) {
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }
        //세션이 유지되면 로그인으로 이동
        // 팁: get요청은 a태그의 href를 사용하거나 button의 onclick 사용가능
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
