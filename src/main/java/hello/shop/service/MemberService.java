package hello.shop.service;

import hello.shop.entity.Address;
import hello.shop.entity.Basket;
import hello.shop.entity.BasketItem;
import hello.shop.entity.Member;
import hello.shop.exception.DuplicateMemberLoginIdException;
import hello.shop.repository.member.MemberDtoV2;
import hello.shop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member){
        return memberRepository.save(member);
    }
    public List<Member> findAll(){
        return  memberRepository.findAll();
    }
    public Member findById(Long id){
        return memberRepository.findById(id).get();
    }
    public Member findByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId);
    }

    public Page<MemberDtoV2> search(Pageable pageable){
        return memberRepository.search(pageable);
    }

    public Member createMember(String loginId, String password, String name, Address address){
        Member member = Member.createMember(loginId, password, name, address);
        validateDuplicateMemberLoginId(member);
        save(member);
        return member;
    }

    private void validateDuplicateMemberLoginId(Member member) {
        Member findMember = memberRepository.findByLoginId(member.getLoginId());
        if (findMember != null) {
            try{
                throw new Exception("ExceptionMessage");
            } catch (Exception e){
                throw new DuplicateMemberLoginIdException(e.getMessage(), e);
            }
        }
    }

    @Transactional
    public void updateMember(Long id, String name, Address address) {
        Member member = findById(id);
        member.setName(name);
        member.setAddress(address);
    }

    @Transactional
    public Long updateMemberSimple(Long id, String name) {
        Member member = findById(id);
        member.setName(name);
        return member.getId();
    }

    public Member loginMember(String loginId, String password){
        Member member = memberRepository.findByLoginId(loginId);
        if(member == null){
            return null;
        }
        if(member.getPassword().equals(password)) {
            return member;
        }
        else{
            return null;
        }
    }
}
