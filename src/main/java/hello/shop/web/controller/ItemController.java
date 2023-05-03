package hello.shop.web.controller;

import hello.shop.entity.Item;
import hello.shop.entity.Member;
import hello.shop.repository.item.ItemSearchCond;
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

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final MemberService memberService;
    private final ItemService itemService;

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
        if(result.hasErrors()) return "item/create";
        // 팁: 생성자보다는 setter사용하는 것이 관례적이고 entity의 setter를 사용할 때는 함수로 해서 변경 지점을 명확히 해야 함
        Member loginMember = (Member) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        itemService.createItem(form.getName(), form.getPrice(), form.getQuantity(), loginMember);
        return "redirect:/item/list";
    }

    @GetMapping("/item/detail/{id}")
    public String detailGet(@PathVariable Long id, @ModelAttribute ItemDetailForm form){
        Item item = itemService.findById(id);

        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setQuantity(item.getQuantity());

        // 팁: form을 생성한다면 함수를 사용할텐데 생성하지 않아서 무의미하긴 하다
//        ItemDetailForm.createItemDetailForm(item, form);
        return "item/detail";
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
        if(result.hasErrors()) return "item/update";
        String name = form.getName();
        Integer price = form.getPrice();
        Integer quantity = form.getQuantity();
        itemService.updateItem(id, name, price, quantity);
        return "redirect:/item/list";
        // 팁: redirect 효과는 return을 페이지가 아니라 경로를 호출할 수 있게 해줌
    }

    @GetMapping("/item/my/{id}")
    public String myGet(@PathVariable Long id, Model model){
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "item/my";
    }


}
