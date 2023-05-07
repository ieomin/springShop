package hello.shop.web.form.basket;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class BasketUpdateForm {
    private Long basketId;
    private Long itemId;
    private String itemName;
    @NotNull(message = "주문 수량은 필수입니다") @Range(min=1, max=100000, message = "주문 수량은 1~100000 이어야 합니다")
    private Integer count;
}
