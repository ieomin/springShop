package hello.shop.repository.member;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDtoV1 {
    private String memberName;

    @QueryProjection
    public MemberDtoV1(String memberName) {
        this.memberName = memberName;
    }
}
