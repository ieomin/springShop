package hello.shop.web.form.member;

import hello.shop.entity.Basket;
import hello.shop.entity.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MemberDetailForm {
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    private List<Order> orders;
    private Basket basket;
}
