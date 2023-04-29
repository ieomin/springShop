package hello.shop.repository.member;

import hello.shop.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// 결과: jdbc는 jdbcMemberRepository(클래스명)으로 jpa는 jpaMemberRepository(인터페이스명)으로 빈이름이 등록되어 서로 다름
// 결과: MemberRepository로 찾은 빈 객체가 jdbcMemberRepo와 springDataJpaMemberRepo로 두 개의 이름이 같으면 오버라이드 하는데 달라서 오류나는 거
// 결과: jpaMemberRepository를 사용하면 항상 빈으로 등록되니까 변경에 용이하지 않음 MemberRepository 인터페이스를 구분하는게 좋은 설계일 듯
public class JdbcMemberRepository implements MemberRepository {
    @Override
    public Member save(Member member) {
        return null;
    }

    public Member findByLoginId(String loginId){
        return null;
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }

    public Page<MemberDtoV2> search(Pageable pageable){
        return null;
    }
}
