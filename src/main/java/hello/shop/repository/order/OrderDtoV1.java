package hello.shop.repository.order;

import com.querydsl.core.annotations.QueryProjection;
import hello.shop.entity.OrderItem;
import hello.shop.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class OrderDtoV1 {

    private Long orderId;
    private String memberName;
    private String itemName;
    private Integer orderItemCount;
    private OrderStatus orderStatus;

    @QueryProjection
    public OrderDtoV1(Long orderId, String memberName, String itemName, Integer orderItemCount, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.itemName = itemName;
        this.orderItemCount = orderItemCount;
        this.orderStatus = orderStatus;
    }
}
