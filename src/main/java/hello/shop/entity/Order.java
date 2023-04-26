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
public class Order {

    @Id @GeneratedValue @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    // 팁: read
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 팁: order에게 기본키를 맡기도록 약속하고 싶은데 많다고 delivery가 가져가는게 좋다네
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 결론: member는 order를가지고create할필요없으므로 read만존재
    // 결론: order는 orderItem을가지고create해야하므로 read+create존재
    // 팁: create
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        // 결론: orderItems에서 order를 생성자에서 넣지않기 때문에 order에서 별도로 설정
        // 결론: order에서 member를 생성자에서 넣기 때문에 member에서 별도로 설정X
        orderItem.setOrder(this);
    }

    // 결론: 사실 상 이 함수는 Delivery로 가서 짝을 이루어야 하는데 먼저 생성 못됐기 때문에 여기있음
    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 팁: 이건 create 아님
    public Order(Member member, Delivery delivery, OrderItem... orderItems){
        this.setMember(member);
        this.addDelivery(delivery);
        for(OrderItem oi: orderItems){
            // 결론: 이걸 생략하는거는 orderItems변수가 없다는 뜻이고 결국 단방향이어서 order를 저장하면 order만저장됨(지금 member저장하면 member만 저장되듯이)
            this.addOrderItem(oi);
        }
        this.setOrderDate(LocalDateTime.now());
        this.setStatus(OrderStatus.ORDER);
    }


}
