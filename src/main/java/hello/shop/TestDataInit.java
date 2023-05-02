package hello.shop;

import hello.shop.entity.*;
import hello.shop.repository.basket.BasketRepository;
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

    // 결과: 자식이 부모를 받을려고 하니까 오류
//    private final JpaMemberRepository memberRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final BasketRepository basketRepository;
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

        ArrayList<BasketItem> basketItems = new ArrayList<>();
        for(int i=1; i<31; i++){
            basketItems.add(new BasketItem(items.get(i - 1), 10));
        }

        for(int i=1; i<31; i++){
            if(i == 1){
                BasketItem basketItem = new BasketItem(items.get(20), 20);
                Basket basket = new Basket(members.get(i - 1), basketItem, basketItems.get(i - 1));
                basketRepository.save(basket);

            } else {
                Basket basket = new Basket(members.get(i - 1), basketItems.get(i - 1));
                basketRepository.save(basket);
            }
        }
        // 보류: 왜 멤버는 부를 수 있고 delivery와orderItem은 못부르지
//        Delivery delivery = new Delivery();
//        OrderItem orderItem = new OrderItem(items.get(1), 10);
//        Order order = new Order(members.get(0), delivery, orderItem);

    }
}
