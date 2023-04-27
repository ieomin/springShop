package hello.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Delivery extends Base{
    @Id @GeneratedValue @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    // 보류: 바로 초기화해도 되겠지
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status = DeliveryStatus.READY;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    public Delivery(Address address){
        this.setAddress(address);
        this.setStatus(DeliveryStatus.READY);
    }
}
