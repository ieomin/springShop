package hello.shop.repository.basket;

import hello.shop.entity.Basket;
import hello.shop.entity.Order;
import hello.shop.repository.order.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long>{
    List<Basket> findByMemberId(Long memberId);
}
