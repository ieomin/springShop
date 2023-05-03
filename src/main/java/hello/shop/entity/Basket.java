package hello.shop.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Basket {
    @Id @GeneratedValue @Column(name = "basket_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<BasketItem> basketItems = new ArrayList<>();
    @Enumerated(EnumType.STRING)

    public void addBasketItem(BasketItem basketItem) {
        basketItems.add(basketItem);
        // 결론: orderItems에서 order를 생성자에서 넣지않기 때문에 order에서 별도로 설정
        // 결론: order에서 member를 생성자에서 넣기 때문에 member에서 별도로 설정X
        basketItem.setBasket(this);
    }


    public Basket(Member member, BasketItem... basketItems){
        this.member = member;
        if(basketItems != null){
            for (BasketItem bi : basketItems) {
                this.addBasketItem(bi);
            }
        }
    }



}
