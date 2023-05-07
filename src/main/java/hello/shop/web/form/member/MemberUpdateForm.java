package hello.shop.web.form.member;

import hello.shop.entity.Item;
import hello.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
public class MemberUpdateForm {
    private Long id;
    @NotBlank(message = "회원 이름은 필수입니다")
    private String name;
    @NotBlank(message = "회원 배송지는 필수입니다")
    private String cityStreetZipcode;
    private List<Order> orders;
    private List<Item> items;
}
