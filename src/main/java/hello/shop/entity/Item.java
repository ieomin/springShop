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

    public void addMember(Member member){
        this.member = member;
        member.getItems().add(this);
    }

    public Item(String name, Integer price, Integer quantity, Member member){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.addMember(member);
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
