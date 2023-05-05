package hello.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Base{

    @Id @GeneratedValue @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<BasketItem> basketItems = new ArrayList<>();

    private Integer totalPrice;

    public void addBasketItem(BasketItem basketItem){
        this.basketItems.add(basketItem);
        basketItem.setOrder(this);
    }

    public static Order createOrder(Member member, Delivery delivery, Basket basket, Integer totalPrice){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setTotalPrice(totalPrice);
        for (BasketItem bi : basket.getBasketItems()) {
            order.addBasketItem(bi);
            if(bi.getStatus() == BasketItemStatus.CONTAIN){
                bi.getItem().removeQuantity(bi.getCount());
            }
        }
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        return order;
    }
}
