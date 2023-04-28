package hello.shop.repository.member;

import hello.shop.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Member findByLoginId(String loginId);

    List<Member> findAll();

    Optional<Member> findById(Long id);

    Page<MemberDtoV2> search(Pageable pageable);
}
