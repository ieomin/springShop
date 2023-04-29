package hello.shop.web.form.order;

import hello.shop.entity.Delivery;
import hello.shop.entity.Item;
import hello.shop.entity.Member;
import hello.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class OrderCreateForm {

    private List<Item> items;
    @NotNull(message = "주문 상품은 필수입니다")
    private Long itemId;

    @NotNull(message = "주문 수량은 필수입니다") @Range(min=1, max=100, message = "주문 수량은 1~100 이어야 합니다")
    private Integer count;

    private String city;

    private String street;

    private String zipcode;

    private List<OrderItem> orderItems;
}
