package hello.shop.repository.member;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDtoV3 {
    private Long memberId;
    private String memberName;
    private String createdDate;
    private String lastModifiedDate;
}
