package hello.shop;

import hello.shop.entity.*;
import hello.shop.repository.item.ItemRepository;
import hello.shop.repository.member.MemberRepository;
import hello.shop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Profile("local")
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    // 결론: 플러시가 따로 되나? 안되지 근데 JPQL퀴리실행시(일반쿼리x) 플러시가 되지
    @PostConstruct
    public void init(){
        Member member1 = new Member("q", "q", "member1",  new Address("Q", "Q", "Q"));
        Member member2 = new Member("w", "w", "member2", new Address("W", "W", "W"));
        // 팁: 같은 delivery를 서로 다른 order에 모두 초기화하면 두 order가 한 delivery를 바라보게 되니 일대일관계가 형성이 안됨
        Delivery delivery1_1 = new Delivery(member1.getAddress());
        Delivery delivery1_2 = new Delivery(member1.getAddress());
        delivery1_2.setStatus(DeliveryStatus.COMP);
        Item item1 = new Item("item1", 1000, 10);
        Item item2 = new Item("item2", 2000, 20);
        OrderItem orderItem1 = new OrderItem(item2, 10);
        OrderItem orderItem2 = new OrderItem(item2, 10);
        Order order1 = new Order(member1, delivery1_1, orderItem1);
        Order order2 = new Order(member1, delivery1_2, orderItem2);
        memberRepository.save(member1);
        memberRepository.save(member2);
        itemRepository.save(item1);
        itemRepository.save(item2);
        orderRepository.save(order1);
        orderRepository.save(order2);
    }
}
