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

    private Integer totalPrice;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private BasketItemStatus status;

    // 결과: basketController가 없으니 이건 단독으로 이렇게 만들어도 됨
    public static BasketItem createBasketItem(Item item, Integer count){
        BasketItem basketItem = new BasketItem();
        basketItem.setItem(item);
        basketItem.setCount(count);
        basketItem.setTotalPrice(item.getPrice() * count);
        basketItem.setStatus(BasketItemStatus.CONTAIN);
        return basketItem;
    }
}
