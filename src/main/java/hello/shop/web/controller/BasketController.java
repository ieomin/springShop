package hello.shop.web.controller;

import hello.shop.entity.*;
import hello.shop.service.BasketService;
import hello.shop.service.ItemService;
import hello.shop.service.MemberService;
import hello.shop.service.OrderService;
import hello.shop.web.SessionConst;
import hello.shop.web.form.basket.BasketUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BasketController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final BasketService basketService;

    // 팁: form에 post와 관련된 어떤 것도적지 않으면 자기자신에게 get으로 날라감

    @GetMapping("/basket/update/{itemId}")
    public String updateForm(@PathVariable Long itemId, @ModelAttribute BasketUpdateForm form, HttpServletRequest request){
        Item item = itemService.findById(itemId);

        Member loginMember = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Long memberId = loginMember.getId();
        log.info("memberId = {}", memberId);
        List<Basket> baskets = basketService.findByMemberId(memberId);
        for (Basket b : baskets) {
            log.info("basketId = {}", b.getId());
            log.info("basketMember = {}", b.getMember());
            log.info("basketStatus = {}", b.getStatus());
            if(b.getStatus() == BasketStatus.USING){
                form.setBasketId(b.getId());
            }
        }

        form.setItemId(itemId);
        form.setItemName(item.getName());
        return "basket/update";
    }

    @PostMapping("/basket/update/{itemId}")
    public String update(@PathVariable Long itemId, @Valid @ModelAttribute BasketUpdateForm form, BindingResult result){
        if(result.hasErrors()) return "basket/update";
        log.info("basketId = {}", form.getBasketId());
        basketService.updateBasket(form.getBasketId(), itemId, form.getCount());
        return "redirect:/";
    }
}
