package hello.shop.repository.order;

import com.querydsl.core.annotations.QueryProjection;
import hello.shop.entity.Address;
import hello.shop.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDtoV3 {
    private Long orderId;
    private String memberName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address orderAddress;

    @QueryProjection
    public OrderDtoV3(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus, Address orderAddress) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderAddress = orderAddress;
    }
}
