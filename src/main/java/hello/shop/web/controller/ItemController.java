package hello.shop.web.controller;

import hello.shop.entity.*;
import hello.shop.exception.NotEnoughStockException;
import hello.shop.repository.item.ItemSearchCond;
import hello.shop.service.CommentService;
import hello.shop.service.ItemService;
import hello.shop.service.MemberService;
import hello.shop.web.SessionConst;
import hello.shop.web.form.item.ItemCreateForm;
import hello.shop.web.form.item.ItemDetailForm;
import hello.shop.web.form.item.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final CommentService commentService;

    @GetMapping("/item/list")
    public String listGet(@ModelAttribute ItemSearchCond cond, Model model, @PageableDefault Pageable pageable){
        Page<Item> items = itemService.search(cond, pageable);
        model.addAttribute("items", items);
        return "item/list";
    }

    @GetMapping("/item/create")
    public String createGet(@ModelAttribute ItemCreateForm form){
        return "item/create";
    }

    @PostMapping("/item/create")
    public String create(@Valid @ModelAttribute ItemCreateForm form, BindingResult result, HttpServletRequest request){

        if(result.hasErrors()) {
            return "item/create";
        }

        Member loginMember = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Item item = itemService.createItem(form.getName(), form.getPrice(), form.getQuantity(), loginMember);
        if(item == null){
            result.reject("itemCreateFail", "이름이 중복되어 상품을 생성할 수 없습니다");
            return "item/create";
        }

        return "redirect:/item/list";
    }

    @GetMapping("/item/detail/{id}")
    public String detailGet(@PathVariable Long id, @ModelAttribute ItemDetailForm form){
        Item item = itemService.findById(id);
        List<BasketItem> basketItems = item.getBasketItems();
        List<String> basketMemberNames = new ArrayList<>();
        List<String> orderMemberNames = new ArrayList<>();
        for (BasketItem basketItem : basketItems) {
            Basket basket = basketItem.getBasket();
            Order order = basketItem.getOrder();
            if(basket != null){
                String name = basket.getMember().getName();
                basketMemberNames.add(name);
            }
            if(order != null){
                String name = order.getMember().getName();
                orderMemberNames.add(name);
            }
        }
        List<Comment> comments = item.getComments();

        form.setId(item.getId());
        form.setMemberName(item.getMember().getName());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setQuantity(item.getQuantity());
        form.setBasketMemberNames(basketMemberNames);
        form.setOrderMemberNames(orderMemberNames);
        form.setComments(comments);
        return "item/detail";
    }

    @PostMapping("/item/detail/{id}")
    public String detailCreateComment(@PathVariable Long id, @ModelAttribute ItemDetailForm form, HttpServletRequest request){
        Item item = itemService.findById(id);
        List<Comment> comments = form.getComments();
        String content = form.getComment().getContent();
        Integer score = form.getComment().getScore();
        Member member = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        Comment comment = commentService.createComment(content, score, item, member);
        comments.add(comment);
        item.setComments(comments);
        return "redirect:/item/detail/" + id;
    }

    @GetMapping("/item/update/{id}")
    public String updateGet(@PathVariable Long id, @ModelAttribute ItemUpdateForm form){
        Item item = itemService.findById(id);
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setQuantity(item.getQuantity());
        return "item/update";
    }

    @PostMapping("/item/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute @Valid ItemUpdateForm form, BindingResult result){

        if(result.hasErrors()) {
            Item item = itemService.findById(id);
            form.setId(item.getId());
            return "item/update";
        }

        Item updateItem = itemService.updateItem(id, form.getName(), form.getPrice(), form.getQuantity());
        if(updateItem == null){
            result.reject("itemUpdateFail", "이름이 중복되어 상품을 수정할 수 없습니다");
            return "item/update";
        }

        return "redirect:/item/list";
    }

    @GetMapping("/item/my/{id}")
    public String myGet(@PathVariable Long id, Model model){
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "item/my";

    }
}



