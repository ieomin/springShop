package hello.shop.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Delivery extends Base{
    @Id @GeneratedValue @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    public Delivery(Address address){
        this.setAddress(address);
        this.setStatus(DeliveryStatus.READY);
    }
}
