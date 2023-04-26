package hello.shop.repository.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDtoV2 {

    private String memberName;
    private String itemName;
    private Integer itemQuantity;

    @QueryProjection
    public OrderDtoV2(String memberName, String itemName, Integer itemQuantity) {
        this.memberName = memberName;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }
}
