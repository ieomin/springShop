package hello.shop.web.form.item;

import hello.shop.entity.BasketItem;
import lombok.Data;

import java.util.List;

@Data
public class ItemDetailForm {
    private Long id;
    private String name;
    private int price;
    private int quantity;
}
