package hello.shop.api;

import hello.shop.repository.order.OrderDtoV3;
import hello.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    // 팁: form변수명은간단히 dto변수명은복잡히
    // 결과: apidto는 querydto랑 다르지 않음
    // 팁: dtoList변수명은 collect 추천
    @GetMapping("/api/order/list")
    public List<OrderDtoV3> listV1(){
        return orderService.searchV3();
    }


}
