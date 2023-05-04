package hello.shop.service;

import hello.shop.exception.NotAllowCanceledOrderException;
import hello.shop.repository.order.*;
import hello.shop.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BasketService basketService;

    public void save(Order order){
        orderRepository.save(order);
    }
    public List<Order> findAll(){
        return orderRepository.findAll();
    }
    public Order findById(Long id){
        return orderRepository.findById(id).get();
    }

    public Page<OrderDtoV1> searchV1(OrderSearchCond cond, Pageable pageable){
        return orderRepository.searchV1(cond, pageable);
    }
    public List<OrderDtoV2> searchV2(OrderSearchCond cond){
        return orderRepository.searchV2(cond);
    }
    public List<OrderDtoV3> searchV3(){
        return orderRepository.searchV3();
    }

    @Transactional
    public Order createOrder(Member member, Delivery delivery, Basket basket) {
        Order order = Order.createOrder(member, delivery, basket);
        List<BasketItem> basketItems = basket.getBasketItems();
        for (BasketItem bi : basketItems) {
            bi.setBasket(null);
        }
        basket.getBasketItems().clear();
        basketService.clearBasket(basket);
        save(order);
        return order;
    }

    @Transactional
    public void updateOrder(Long orderId, Address address) {
        Order order = findById(orderId);
        order.setOrderDate(LocalDateTime.now());
        order.setDelivery(new Delivery(address));
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = findById(orderId);
        if(order.getDelivery().getStatus() == DeliveryStatus.COMP) {
            throw new NotAllowCanceledOrderException(("NotAllowCanceledOrderExceptionMessage"));
        }
        order.setStatus(OrderStatus.CANCEL);
        for(BasketItem oi :order.getBasketItems()){
            Item item = oi.getItem();
            Integer count = oi.getCount();
            Integer quantity = item.getQuantity();
            item.setQuantity(quantity+count);
        }
    }
}
