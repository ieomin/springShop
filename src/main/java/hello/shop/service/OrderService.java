package hello.shop.service;

import hello.shop.exception.NotAllowCanceledOrderException;
import hello.shop.repository.order.*;
import hello.shop.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public void save(Order order){
        orderRepository.save(order);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order findById(Long id){
        return orderRepository.findById(id).get();
    }

    public List<OrderDtoV1> searchV1(OrderSearchCond cond){
        return orderRepository.searchV1(cond);
    }
    public List<OrderDtoV2> searchV2(OrderSearchCond cond){
        return orderRepository.searchV2(cond);
    }
    public List<OrderDtoV3> searchV3(){
        return orderRepository.searchV3();
    }

    // 팁: set사용할 시에는 transactional 추가해서 함수로 뽑아야 함
    @Transactional
    public void cancelOrder(Long id){
        Order order = findById(id);

        if(order.getDelivery().getStatus() == DeliveryStatus.COMP) {
            throw new NotAllowCanceledOrderException(("NotAllowCanceledOrderExceptionMessage"));
        }

        order.setStatus(OrderStatus.CANCEL);
        for(OrderItem oi :order.getOrderItems()){
            Item item = oi.getItem();
            Integer count = oi.getCount();
            Integer quantity = item.getQuantity();
            item.setQuantity(quantity+count);
        }
    }
}
