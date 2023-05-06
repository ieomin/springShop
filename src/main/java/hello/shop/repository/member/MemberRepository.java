package hello.shop.repository.member;

import hello.shop.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long id);

    // 보류: 옵셔널객체 처리 Member를 선택하면 MemberOrNull이 되는건가
    // 팁: 옵셔널이 아닌 멤버 반환
    Member findByLoginId(String loginId);

    Page<MemberDtoV2> search(Pageable pageable);
}
