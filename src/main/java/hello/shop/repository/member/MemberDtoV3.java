package hello.shop.repository.member;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MemberDtoV3 {
    private Long memberId;
    private String memberName;
    private String createdDate;
    private String lastModifiedDate;
}
