package hello.shop.entity;

import hello.shop.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Item extends Base{

    @Id @GeneratedValue @Column(name = "item_id")
    private Long id;

    private String name;

    private Integer price;

    private Integer quantity;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<BasketItem> basketItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    public static Item createItem(String name, Integer price, Integer quantity, Member member){
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setMember(member);
        return item;
    }



    public void removeQuantity(Integer count) {
        Integer restQuantity = quantity - count;
        if (restQuantity < 0) {
            throw new NotEnoughStockException("NotEnoughStockExceptionMessage");
        } else {
            this.setQuantity(restQuantity);
        }
    }
}
