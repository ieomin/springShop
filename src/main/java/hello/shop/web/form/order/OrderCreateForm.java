package hello.shop.web.form.order;

import hello.shop.entity.Basket;
import hello.shop.entity.BasketItem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderCreateForm {

    private String city;
    private String street;
    private String zipcode;
    // 결과: 올 때 객체랑 갈 때 객체가 다름, 폼에서 객체를 보여주는 건 되는데 받는 것이 잘 되지가 않으니 이런 코드 사용하지 말도록
    private Basket basket;
}
