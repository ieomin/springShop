package hello.shop.web.form.member;

import hello.shop.entity.Basket;
import hello.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MemberDetailForm {
    private Long id;
    private String name;
    private String cityStreetZipcode;
    private List<Order> orders;
    private Basket basket;
}
