package hello.shop.web.controller;

import hello.shop.entity.*;
import hello.shop.service.BasketService;
import hello.shop.service.ItemService;
import hello.shop.service.MemberService;
import hello.shop.web.SessionConst;
import hello.shop.web.form.basket.BasketUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BasketController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final BasketService basketService;

    // 팁: form에 post와 관련된 어떤 것도적지 않으면 자기자신에게 get으로 날라감
    @GetMapping("/basket/update/{itemId}")
    public String updateForm(@PathVariable Long itemId, @ModelAttribute BasketUpdateForm form, HttpServletRequest request){
        Item item = itemService.findById(itemId);
        Member loginMember = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Long memberId = loginMember.getId();
        Basket basket = basketService.findByMemberId(memberId);
        form.setBasketId(basket.getId());
        form.setItemId(itemId);
        form.setItemName(item.getName());
        return "basket/update";
    }

    @PostMapping("/basket/update/{itemId}")
    public String update(@PathVariable Long itemId, @Valid @ModelAttribute BasketUpdateForm form, BindingResult result, HttpServletRequest request){
        if(result.hasErrors()) return "basket/update";
        basketService.updateBasket(form.getBasketId(), itemId, form.getCount());
        Long memberId = ((Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        return "redirect:/basket/my/" + memberId;
    }

    @GetMapping("/basket/my/{id}")
    public String myGet(@PathVariable Long id, Model model){
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "basket/my";
    }

    @PostMapping("/basket/canceledMy/{basketId}/{basketItemId}")
    public String canceledMy(@PathVariable Long basketId, @PathVariable Long basketItemId, HttpServletRequest request){
        basketService.cancelBasketItem(basketId, basketItemId);
        Long memberId = ((Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        return "redirect:/basket/my/" + memberId;
    }


}
