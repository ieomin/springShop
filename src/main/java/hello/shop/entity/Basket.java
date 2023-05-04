package hello.shop.entity;

import lombok.Data;
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

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<BasketItem> basketItems = new ArrayList<>();

    public void addBasketItem(BasketItem basketItem) {
        this.basketItems.add(basketItem);
        basketItem.setBasket(this);
    }

    public static Basket createBasket(Member member,
                               BasketItem... basketItems){
        Basket basket = new Basket();
        basket.member = member;
        if(basketItems != null){
            for (BasketItem bi : basketItems) {
                basket.addBasketItem(bi);
            }
        }
        return basket;
    }
}
