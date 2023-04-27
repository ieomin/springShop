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
    private List<OrderItem> orderItems = new ArrayList<>();

    public Item(String name, Integer price, Integer quantity){
        this.setName(name);
        this.setPrice(price);
        this.setQuantity(quantity);
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
