package hello.shop.web.form.order;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class OrderUpdateForm {
    private Long memberId;
    // 보류: 왜 이것만 안되냐?? 디버깅 할 곳도 없네
    @NotBlank(message = "배송지는 필수입니다")
    private String cityStreetZipcode;
}
