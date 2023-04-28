package hello.shop.web.form.item;

import hello.shop.entity.Item;
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

//    public static ItemDetailForm createItemDetailForm(Item item, ItemDetailForm form){
//        form.setId(item.getId());
//        form.setName(item.getName());
//        form.setPrice(item.getPrice());
//        form.setQuantity(item.getQuantity());
//        form.setOrderItems(item.getOrderItems());
//        return form;
//    }
}
