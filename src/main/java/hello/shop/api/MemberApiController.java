package hello.shop.api;

import hello.shop.entity.Member;
import hello.shop.exception.CustomException;
import hello.shop.repository.member.MemberDtoV1;
import hello.shop.repository.member.MemberDtoV2;
import hello.shop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
// 팁: MemberController을사용하고싶어도 스프링빈에서는허용안됨
public class MemberApiController {

    private final MemberService memberService;

    // 팁: PathVariable RequestParam ModelAttribute RequestBody
    // 팁: headerContenttypeApplicationjson -> bodyRawJson
    @PostMapping("/api/member/create/v1")
    public CreateMemberResponse createV1(@RequestBody @Valid Member member){
        Long id = memberService.save(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/member/create/v2")
    public CreateMemberResponse createV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.save(member);
        return new CreateMemberResponse(id);
    }

    // 팁: PutMapping으로 해도 상관은 없음
    @PostMapping("/api/member/update/{id}")
    public UpdateMemberResponse update(@PathVariable Long id, @RequestBody @Valid UpdateMemberRequest request){
        Long findId = memberService.updateMemberSimple(id, request.getName());
        Member member = memberService.findById(findId);
        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    // 결과: 받는걸변경하는이유는스펙이변해서이고 주는걸변경하는이유는로직추가때문이다
    @GetMapping("/api/member/list")
    public List<MemberDtoV1> list() {
        List<Member> findMembers = memberService.findAll();
        return findMembers.stream().map(m -> new MemberDtoV1(m.getName())).collect(Collectors.toList());
    }

    @GetMapping("/api/member/detail/{id}")
    public MemberDtoV2 detail(@PathVariable String id) {

        if (id.equals("IllegalArgumentException")) {
            throw new IllegalArgumentException("IllegalArgumentExceptionMessage");
        }
        if (id.equals("CustomException")) {
            throw new CustomException("CustomExceptionMessage");
        }
        if (id.equals("RuntimeException")) {
            throw new RuntimeException("RuntimeExceptionMessage");
        }

        Member member = memberService.findById(Long.valueOf(id));
        return new MemberDtoV2(String.valueOf(member.getId()), member.getName());
    }


    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }
}

