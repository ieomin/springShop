package hello.shop.repository.member;

import hello.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

    Member findByLoginId(String loginId);
    // 팁: 못 찾으면 null 리턴

}
