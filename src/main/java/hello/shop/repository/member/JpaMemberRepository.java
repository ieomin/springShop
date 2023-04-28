package hello.shop.repository.member;

import hello.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface JpaMemberRepository extends JpaRepository<Member, Long>, JpaMemberRepositoryCustom, MemberRepository {

    Member findByLoginId(String loginId);
    // 팁: 못 찾으면 null 리턴

}
