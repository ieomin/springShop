package hello.shop.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class OrderItem {

    @Id @GeneratedValue @Column(name = "order_item_id")
    private Long id;

    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "item_id")
    private Item item;

    public OrderItem(Item item, Integer count){
        this.setItem(item);
        this.setCount(count);
        item.removeQuantity(count);
    }
}
