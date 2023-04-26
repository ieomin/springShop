package hello.shop.web.form.item;

import hello.shop.entity.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class ItemDetailForm {
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private List<OrderItem> orderItems;
}
