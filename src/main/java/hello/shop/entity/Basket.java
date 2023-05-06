package hello.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Basket {

    @Id @GeneratedValue @Column(name = "basket_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "basket")
    private List<BasketItem> basketItems = new ArrayList<>();

    private Integer totalPrice = 0;

    public void addBasketItem(BasketItem basketItem) {
        this.basketItems.add(basketItem);
        basketItem.setBasket(this);
    }

    public static Basket createBasket(Member member, BasketItem... basketItems){
        Basket basket = new Basket();
        basket.setMember(member);
        if(basketItems != null){
            for (BasketItem bi : basketItems) {
                basket.addBasketItem(bi);
            }
        }
        return basket;
    }
}
