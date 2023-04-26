package hello.shop.repository.order;

import java.util.List;

public interface OrderRepositoryCustom {
    public List<OrderDtoV1> searchV1(OrderSearchCond cond);
    public List<OrderDtoV2> searchV2(OrderSearchCond cond);
    public List<OrderDtoV3> searchV3();
}
