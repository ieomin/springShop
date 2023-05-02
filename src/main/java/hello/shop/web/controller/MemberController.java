package hello.shop.web.controller;

import hello.shop.entity.Address;
import hello.shop.entity.Member;
import hello.shop.exception.DuplicateMemberLoginIdException;
import hello.shop.repository.member.MemberDtoV2;
import hello.shop.service.MemberService;
import hello.shop.web.SessionConst;
import hello.shop.web.form.member.MemberCreateForm;
import hello.shop.web.form.member.MemberDetailForm;
import hello.shop.web.form.member.MemberLoginForm;
import hello.shop.web.form.member.MemberUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    // 결론: html에 넘기는 변수명은 풀로 적어주기, ModelAttribute를 사용하면 변수명이 아니라 클래스명으로 넘어감
    // 팁: BindingResult 는 검증할 대상 바로 다음에 와야한다. 순서가 중요하다. 예를 들어서 @ModelAttribute Item item , 바로 다음에 BindingResult 가 와야 한다

    private final MemberService memberService;

    @GetMapping("/member/list")
    public String listGet(Model model, @PageableDefault Pageable pageable){
        Page<MemberDtoV2> members = memberService.search(pageable);
        model.addAttribute("members", members);
        return "member/list";
    }

    @GetMapping("/member/create")
    public String createGet(@ModelAttribute MemberCreateForm form, @RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "member/create";
    }
    

    @PostMapping("/member/create")
    public String create(@Valid @ModelAttribute MemberCreateForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "member/create";
        }
        try {
            memberService.createMember(form.getLoginId(), form.getPassword(), form.getName(), new Address(form.getCity(), form.getStreet(), form.getZipcode()));
        } catch(DuplicateMemberLoginIdException e){
            // 결과: 복구불가능예외(SQLException)는 Runtime예외로 변경하고 복구가능대상을 사용자가 아니라 관리자로 해야 함
            memberService.informAdminOfException(e.getMessage());
            return "redirect:/member/create?message=" + e.getMessage();
        }
        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String loginGet(@ModelAttribute MemberLoginForm form, @RequestParam(required = false) String redirectURL, Model model) {
        model.addAttribute("redirectURL", redirectURL);
        return "member/login";
    }

    @PostMapping("/member/login")
    public String login(@Valid @ModelAttribute MemberLoginForm form, BindingResult result, HttpServletRequest request, @RequestParam(defaultValue = "/") String redirectURL) {
        if (result.hasErrors()) {
            return "member/login";
        }
        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());
        if (loginMember == null) {
            // 팁: 이게 글로벌 에러
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "member/login";
        }
        
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:" + redirectURL;
    }

    @PostMapping("/member/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        // 팁: 예외를 처리하니 Console에 Exception 출력하는 것을 막아버리네
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    // 결론: modelattribute로부터 받은 객체를 새로 할당하면 기존 객체는 무시됨
    // 팁: 폼에서는 객체를 다 풀어서 넣어야 html에서 사용하기 편리함
    @GetMapping("/member/detail/{id}")
    public String detailGet(@PathVariable Long id, @ModelAttribute MemberDetailForm form) {
        Member member = memberService.findById(id);
        // 팁: 한 폼당 set은 왠만하면 한번만 호출되서 폼 함수는 의미 없겠다
        form.setId(member.getId());
        form.setName(member.getName());
        form.setOrders(member.getOrders());
        form.setCity(member.getAddress().getCity());
        form.setStreet(member.getAddress().getStreet());
        form.setZipcode(member.getAddress().getZipcode());
        form.setBasket(member.getBaskets().get(0));
        return "member/detail";
    }

    @GetMapping("/member/update/{id}")
    public String updateGet(@PathVariable Long id, @ModelAttribute MemberUpdateForm form){
        Member member = memberService.findById(id);
        form.setId(member.getId());
        form.setName(member.getName());
        form.setOrders(member.getOrders());
        form.setCity(member.getAddress().getCity());
        form.setStreet(member.getAddress().getStreet());
        form.setZipcode(member.getAddress().getZipcode());
        return "member/update";
    }

    // 결론: th:action에 값을 안넣으면 자신의경로 호출
    @PostMapping("/member/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute MemberUpdateForm form, BindingResult result){
        // 결론: bindingResult를 가지고 return해주면 파라미터 값은 다 가지고 이동
        if(result.hasErrors()) return "member/update";
        memberService.updateMember(id, form.getName(), new Address(form.getCity(), form.getStreet(), form.getZipcode()));
        return "redirect:/member/list";
    }
}
