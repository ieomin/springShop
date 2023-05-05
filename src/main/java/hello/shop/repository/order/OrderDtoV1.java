package hello.shop.repository.order;

import com.querydsl.core.annotations.QueryProjection;
import hello.shop.entity.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDtoV1 {

    private Long orderId;
    private String memberName;
    private OrderStatus orderStatus;

    @QueryProjection
    public OrderDtoV1(Long orderId, String memberName, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.orderStatus = orderStatus;
    }
}
