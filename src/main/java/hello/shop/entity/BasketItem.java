package hello.shop.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class BasketItem extends Base{
    @Id @GeneratedValue @Column(name = "basket_item_id")
    private Long id;
    private Integer count;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "basket_id")
    private Basket basket;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id")
    private Order order;
    @Enumerated(EnumType.STRING)
    private BasketItemStatus status;


    public BasketItem(Item item, Integer count){
        this.setItem(item);
        this.setCount(count);
        this.setStatus(BasketItemStatus.BASKET_ITEM);
    }
}
