package hello.shop.web.form.order;

import hello.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
public class OrderUpdateForm {

    private Long memberId;

    private String city;

    private String street;

    private String zipcode;
}
