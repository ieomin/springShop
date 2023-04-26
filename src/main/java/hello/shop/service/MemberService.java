package hello.shop.service;

import hello.shop.entity.Address;
import hello.shop.entity.Member;
import hello.shop.exception.DuplicateMemberLoginIdException;
import hello.shop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // 결론: apiException파트가 따로 있는 이유가 여기 있구나
    public Long save(Member member){
        validateDuplicateMemberLoginId(member);
        Member m = memberRepository.save(member);
        return m.getId();
    }

    // 팁: 자주 사용하는 IllegalStateException은 RuntimeException을 상속하는 예외임
    private void validateDuplicateMemberLoginId(Member member) {
        Member findMember = memberRepository.findByLoginId(member.getLoginId());
        if (findMember != null) {
            // 결론: 일반Exception이아니라 SQLException이라 생각하고 변환해보기도하고 기존예외도포함하는 코드이기도 하고 그냥 Exception이면 체크예외여서 컨트롤러까지 못잡는다면 컨트롤러가 이 예외가 SQLException이었다면 jdbc에 종속적이게 되는 코드가 되는건데 그걸 해결
            // 팁: Exception을 SQLEx라생각하고 DuplicateMemberNameEx를 MySQLEx로생각
            try{
                throw new Exception("ExceptionMessage");
            } catch (Exception e){
                throw new DuplicateMemberLoginIdException(e.getMessage(), e);
            }
        }
    }

    public Member login(String loginId, String password){
        Member member = memberRepository.findByLoginId(loginId);
        // 팁: 여기서는 검증으로 처리 가능하기 때문에 굳이 exception 발생안시켜도 됨
        if(member == null){
            // 일치하는 아이디 존재x
            return null;
        }
        if(member.getPassword().equals(password)) {
            return member;
        }
        else{
            return null;
        }
    }

    public List<Member> findAll(){
        List<Member> members = memberRepository.findAll();
        return members;
    }

    public Member findById(Long id){
        Member member = memberRepository.findById(id).get();
        return member;
    }

    public Member findByLoginId(String loginId){
        Member member = memberRepository.findByLoginId(loginId);
        return member;
    }

    /** * Perform createMember.
     * @param name entity instance
     * @param address entity instance
     * @throws DuplicateMemberLoginIdException if the member name already exists.
     **/
    public void createMember(String loginId, String password, String name, Address address){
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setName(name);
        member.setAddress(address);
        save(member);
    }

    // 결론: 같은 캐시를 사용할려면 find와 set이 같은 트랜잭션 안에서 이루어져야 함
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


    public void informAdminOfException(String message){
        System.out.println("관리자에게 알릴 내용: " + message);
    }
}
