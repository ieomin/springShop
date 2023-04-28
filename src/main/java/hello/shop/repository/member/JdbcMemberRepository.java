package hello.shop.repository.member;

import hello.shop.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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
