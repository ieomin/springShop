package hello.shop.repository.member;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDtoV2 {
    private String memberId;
    private String memberName;

    @QueryProjection
    public MemberDtoV2(String memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }
}
