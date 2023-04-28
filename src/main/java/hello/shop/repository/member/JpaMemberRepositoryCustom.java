package hello.shop.repository.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JpaMemberRepositoryCustom {
    public Page<MemberDtoV2> search(Pageable pageable);
}
