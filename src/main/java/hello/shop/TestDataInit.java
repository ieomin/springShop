package hello.shop;

import hello.shop.entity.*;
import hello.shop.repository.item.ItemRepository;
import hello.shop.repository.member.MemberRepository;
import hello.shop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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

        List<Member> members = new ArrayList<>();
        for(int i=1; i<31; i++){
            Member member = new Member(String.valueOf(i), String.valueOf(i), "member" + i);
            member.setAddress(new Address(String.valueOf(i), String.valueOf(i), String.valueOf(i)));
            members.add(member);
            memberRepository.save(member);
        }

        List<Item> items = new ArrayList<>();
        for(int i=1; i<31; i++){
            Item item = new Item("item" + i, 1000, 100);
            items.add(item);
            itemRepository.save(item);
        }

        // 팁: 같은 delivery를 서로 다른 order에 모두 초기화하면 두 order가 한 delivery를 바라보게 되니 일대일관계가 형성이 안됨
        List<Delivery> deliveries = new ArrayList<>();
        for(int i=1; i<31; i++){
            deliveries.add(new Delivery());
        }

        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for(int i=1; i<31; i++){
            orderItems.add(new OrderItem(items.get(i - 1), 10));
        }

        ArrayList<Order> orders = new ArrayList<>();
        for(int i=1; i<31; i++){
            Order order = new Order(members.get(i - 1), deliveries.get(i - 1), orderItems.get(i - 1));
            orders.add(order);
            orderRepository.save(order);
        }
    }
}
