package hello.shop.repository.member;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;

import static hello.shop.entity.QMember.member;

public class JpaMemberRepositoryImpl implements JpaMemberRepositoryCustom {

    private JPAQueryFactory query;
    JpaMemberRepositoryImpl(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberDtoV2> search(Pageable pageable) {

        QueryResults<MemberDtoV2> results = query
                .select(new QMemberDtoV2(member.id, member.name, member.createdDate, member.lastModifiedDate))
                .from(member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.lastModifiedDate.desc())
                // 팁: 내용과 toal을 한번에 조회가능
                .fetchResults();

        List<MemberDtoV2> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
