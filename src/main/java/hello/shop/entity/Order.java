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

    // 팁: delivery 너는 내가 생성 될 때 같이 생성 되렴
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Integer totalPrice;

    // 보류: cascade가 없으면 테스트데이터의 baketitem이 생성되지 않음
    // 그 말은 원코드는 basketCascade가 적용되고 testData는 적용안된다는 뜻
    // 어쨎든 basketItem은 repo가 없기 때문에 연관된 모든 엔티티에서 cacade를 해야 변경이 가능함 basket, order, item 등 근데 item은 흐름상 안해도 되니 패스
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<BasketItem> basketItems = new ArrayList<>();

    public void addBasketItem(BasketItem basketItem){
        this.basketItems.add(basketItem);
        basketItem.setOrder(this);
    }

    public static Order createOrder(Member member, Delivery delivery, Basket basket, Integer totalPrice){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        order.setTotalPrice(totalPrice);

        for (BasketItem bi : basket.getBasketItems()) {
            order.addBasketItem(bi);
            if(bi.getStatus() == BasketItemStatus.CONTAIN){
                bi.getItem().removeQuantity(bi.getCount());
            }
        }
        return order;
    }
}
