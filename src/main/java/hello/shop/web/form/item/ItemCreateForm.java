package hello.shop.web.form.item;

import hello.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ItemCreateForm {

    // 결론: NotBlank: " "도 허용안하므로 엄격한 itemName 같은 곳에서 사용
    @NotBlank(message = "상품 이름은 필수입니다")
    private String name;

    // 팁: 만약 int라면 null값을 가질 수 없으므로 무의미
    @NotNull(message = "상품 가격은 필수입니다") @Range(min=1000, max=1000000, message = "상품 가격은 1000~1000000 이어야 합니다")
    private Integer price;

    @NotNull(message = "상품 수량은 필수입니다") @Range(min=1, max=10000, message = "상품 수량은 1~10000 이어야 합니다")
    private Integer quantity;

    public static void createItem(ItemCreateForm form){
        Item item = new Item();
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setQuantity(form.getQuantity());
    }
}
