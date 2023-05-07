package hello.shop.web.controller;

import hello.shop.entity.Item;
import hello.shop.entity.Member;
import hello.shop.repository.item.ItemSearchCond;
import hello.shop.service.ItemService;
import hello.shop.service.MemberService;
import hello.shop.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/")
    // 보류: 설마 세션으로 안하고 이렇게 가능?
    public String homeGet(@Login Member loginMember, @ModelAttribute ItemSearchCond cond, Model model, @PageableDefault Pageable pageable) {
        // 세션에 회원 데이터가 없으면 home

        Page<Item> items = itemService.search(cond, pageable);
        model.addAttribute("items", items);

//        if (loginMember == null) {
//            return "item/list";
//        }

        // 세션이 유지되면 로그인으로 이동
        // 팁: get요청은 a태그의 href를 사용하거나 button의 onclick 사용가능
//        model.addAttribute("member", loginMember);
//        return "loginHome";


        return "item/list";
    }

}
