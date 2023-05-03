package hello.shop.repository.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {
    public Page<OrderDtoV1> searchV1(OrderSearchCond cond, Pageable pageable);
    public List<OrderDtoV2> searchV2(OrderSearchCond cond);
    public List<OrderDtoV3> searchV3();
}
