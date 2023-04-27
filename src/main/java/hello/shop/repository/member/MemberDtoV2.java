package hello.shop.repository.member;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDtoV2 {
    private Long memberId;
    private String memberName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @QueryProjection
    public MemberDtoV2(Long memberId, String memberName, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
