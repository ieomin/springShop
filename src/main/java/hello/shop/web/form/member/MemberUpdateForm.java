package hello.shop.web.form.member;

import hello.shop.entity.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class MemberUpdateForm {

    // 팁: 어차피 html에서 readOnly라서 @NotNull은 사실 상 무의미
    private Long id;

    @NotBlank(message = "회원 이름은 필수입니다")
    private  String name;

    @NotBlank(message = "회원 배송 도시는 필수입니다")
    private String city;

    @NotBlank(message = "회원 배송 도로는 필수입니다")
    private String street;

    @NotBlank(message = "회원 배송 도로번호는 필수입니다")
    private String zipcode;

    private List<Order> orders;
}
