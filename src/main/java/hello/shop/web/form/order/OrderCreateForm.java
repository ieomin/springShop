package hello.shop.web.form.order;

import hello.shop.entity.Basket;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class OrderCreateForm {
    // 결과: 올 때 객체랑 갈 때 객체가 다름, 폼에서 객체를 보여주는 건 되는데 받는 것이 잘 되지가 않으니 주의
    private Basket basket;
    private Integer totalPrice;
    @NotBlank(message = "회원 배송지는 필수입니다")
    private String cityStreetZipcode;

}
