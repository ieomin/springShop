package hello.shop.repository.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchCond {

    private String itemName;
    private Integer maxPrice;
}
