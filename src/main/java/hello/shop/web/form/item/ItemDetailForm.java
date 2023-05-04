package hello.shop.web.form.item;

import hello.shop.entity.BasketItem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ItemDetailForm {
    private Long id;
    private String name;
    private int price;
    private int quantity;
}
