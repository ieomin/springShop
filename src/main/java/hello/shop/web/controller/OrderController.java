package hello.shop.web.controller;

import hello.shop.exception.NotAllowCanceledOrderException;
import hello.shop.exception.NotEnoughStockException;
import hello.shop.repository.order.OrderDtoV1;
import hello.shop.entity.*;
import hello.shop.repository.order.OrderSearchCond;
import hello.shop.service.ItemService;
import hello.shop.service.MemberService;
import hello.shop.service.OrderService;
import hello.shop.web.form.order.OrderCreateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    // 팁: form에 post와 관련된 어떤 것도적지 않으면 자기자신에게 get으로 날라감

    @GetMapping(value = "/order/list")
    public String listGetV3(@RequestParam(name = "message", required = false) String message, @ModelAttribute OrderSearchCond cond, Model model) {
        List<OrderDtoV1> orders = orderService.searchV1(cond);
        log.info("message = {}", message);
        model.addAttribute("orders", orders);
        model.addAttribute("message", message);
        return "order/list";
    }

    // 팁: th:if가 참이면 포함하는 태그 전체가 무시됨
    // 팁: queryParameter는 한글 못받음
    @PostMapping(value = "/order/canceledList/{id}")
    public String CanceledListGet(@PathVariable Long id) {
        try{
            orderService.cancelOrder(id);
        } catch(NotAllowCanceledOrderException e){
            return "redirect:/order/list?message=" + e.getMessage();
        }
        return "redirect:/order/list";
    }

    @GetMapping("/order/create")
    public String createForm(@RequestParam(name = "message", required = false) String message, @ModelAttribute OrderCreateForm form, Model model){
        List<Member> members = memberService.findAll();
        List<Item> items = itemService.findAll();
        form.setMembers(members);
        form.setItems(items);
        model.addAttribute("message", message);
        return "order/create";
    }

    @PostMapping("/order/create")
    // 팁: input태그의 name은 RequestParam과 매칭도 되는데 set해주는 역할을 자주 함
    public String create(@Valid @ModelAttribute OrderCreateForm form, BindingResult result){
        if(result.hasErrors()) {
            List<Member> members = memberService.findAll();
            List<Item> items = itemService.findAll();
            form.setItems(items);
            form.setMembers(members);
            return "order/create";
        }
        Item item = itemService.findById(form.getItemId());
        OrderItem orderItem;
        try{
            orderItem = new OrderItem(item, form.getCount());
        } catch(NotEnoughStockException e){
            log.info("catch(NotEnoughStockException e)");
            return "redirect:/order/create?message=" + e.getMessage();
        }

        Member member = memberService.findById(form.getMemberId());
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Delivery delivery = new Delivery(address);
        Order order = new Order(member, delivery, orderItem);
        orderService.save(order);
        return "redirect:/order/list";
    }
}
