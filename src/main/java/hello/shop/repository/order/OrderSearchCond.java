package hello.shop.repository.order;

import hello.shop.entity.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearchCond {
    private String memberName;
    private OrderStatus orderStatus;
}
