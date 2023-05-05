package hello.shop.web.form.item;

import hello.shop.entity.BasketItem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class ItemUpdateForm {
    private Long id;
    @NotBlank(message = "상품 이름은 필수입니다")
    private String name;
    @NotNull(message = "상품 가격은 필수입니다") @Range(min=1000, max=1000000, message = "상품 가격은 1000~1000000 이어야 합니다")
    // 팁: int말고 Integer로 통일성 가지기
    private Integer price;
    @NotNull(message = "상품 수량은 필수입니다") @Range(min = 1, max = 1000000, message = "상품 수량은 1~1000000 이어야 합니다")
    private Integer quantity;
}
