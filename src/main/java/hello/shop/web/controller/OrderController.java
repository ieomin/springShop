package hello.shop.web.controller;

import hello.shop.exception.NotAllowCanceledOrderException;
import hello.shop.exception.NotEnoughStockException;
import hello.shop.repository.order.OrderDtoV1;
import hello.shop.entity.*;
import hello.shop.repository.order.OrderSearchCond;
import hello.shop.service.BasketService;
import hello.shop.service.MemberService;
import hello.shop.service.OrderService;
import hello.shop.web.SessionConst;
import hello.shop.web.form.order.OrderCreateForm;
import hello.shop.web.form.order.OrderUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final BasketService basketService;

    // 팁: form에 post와 관련된 어떤 것도적지 않으면 자기자신에게 get으로 날라감

    @GetMapping("/order/list")
    // 팁: requestParamValue는 defaultValue와 같이 쓰임
    public String listGet(@RequestParam(required = false) String message, @ModelAttribute OrderSearchCond cond, Model model, @PageableDefault Pageable pageable) {
        Page<OrderDtoV1> orders = orderService.searchV1(cond, pageable);
        model.addAttribute("orders", orders);
        model.addAttribute("message", message);
        return "order/list";
    }

    // 팁: th:if가 참이면 포함하는 태그 전체가 무시됨
    // 팁: queryParameter는 한글 못받음
    @PostMapping("/order/canceledList/{id}")
    public String canceledList(@PathVariable Long id) {
        try{
            orderService.cancelOrder(id);
        } catch(NotAllowCanceledOrderException e){
            return "redirect:/order/list?message=" + e.getMessage();
        }
        return "redirect:/order/list";
    }

    // 팁: pathVariable과 달리 requestParam은 URL에 명시 안함
    @GetMapping("/order/create")
    public String createGet(@ModelAttribute OrderCreateForm form, @RequestParam(required = false) String message,  Model model, HttpServletRequest request, BindingResult result){
        Long memberId = ((Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        Basket basket = basketService.findByMemberId(memberId);
        form.setBasket(basket);
        form.setTotalPrice(basket.getTotalPrice());
        model.addAttribute("message", message);
        return "order/create";
    }

    @PostMapping("/order/create")
    // 팁: input태그의 name은 RequestParam과 매칭도 되는데 set해주는 역할을 자주 함
    public String create(@Valid @ModelAttribute OrderCreateForm form, BindingResult result, HttpServletRequest request){

        // 단일검증
        if(result.hasErrors()) {
            Basket basket = basketService.findByMemberId(((Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER)).getId());
            form.setBasket(basket);
            form.setTotalPrice(basket.getTotalPrice());
            return "order/create";
        }

        // 생성하고전체검증
        Member loginMember = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Basket basket = basketService.findByMemberId(loginMember.getId());
        Integer totalPrice = basket.getTotalPrice();
        Order order;
        try{
            order = orderService.createOrder(loginMember, new Delivery(new Address(form.getCityStreetZipcode())), basket, totalPrice);
        } catch (NotEnoughStockException e){
            return "redirect:/order/create?message=" + e.getMessage();
        }
        if(order == null){
            form.setBasket(basket);
            form.setTotalPrice(basket.getTotalPrice());
            result.reject("orderCreateFail", "장바구니에 상품이 존재하지 않아 주문을 생성할 수 없습니다");
            return "order/create";
        }

        return "redirect:/order/my/" + loginMember.getId();
    }

    @GetMapping("/order/detail/{id}")
    public String detailGet(@PathVariable Long id, Model model){
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "order/detail";
    }

    @GetMapping("/order/update/{id}")
    public String updateGet(@PathVariable Long id, @ModelAttribute OrderUpdateForm form, HttpServletRequest request){
        Long memberId = ((Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        Order order = orderService.findById(id);
        form.setMemberId(memberId);
        form.setCityStreetZipcode(order.getDelivery().getAddress().getCityStreetZipcode());
        return "order/update";
    }

    @PostMapping("/order/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute OrderUpdateForm form, HttpServletRequest request, BindingResult result){

        if(result.hasErrors()){
            return "order/update";
        }
        orderService.updateOrder(id, form.getCityStreetZipcode());

        return "redirect:/order/my/" + ((Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER)).getId();
    }

    @GetMapping("/order/my/{id}")
    public String myGet(@PathVariable Long id, Model model){
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "order/my";
    }

    @PostMapping("/order/canceledMy/{id}")
    public String canceledMy(@PathVariable Long id, HttpServletRequest request) {
        try{
            orderService.cancelOrder(id);
        } catch(NotAllowCanceledOrderException e){
            return "redirect:/order/my?message=" + e.getMessage();
        }
        Long memberId = ((Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER)).getId();
        return "redirect:/order/my/" + memberId;
    }
}
