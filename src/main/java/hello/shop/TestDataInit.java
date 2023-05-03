package hello.shop;

import hello.shop.entity.*;
import hello.shop.repository.basket.BasketRepository;
import hello.shop.repository.item.ItemRepository;
import hello.shop.repository.member.MemberRepository;
import hello.shop.repository.order.OrderRepository;
import hello.shop.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("local")
public class TestDataInit {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final MemberRepository memberRepository;
        private final ItemRepository itemRepository;
        private final BasketRepository basketRepository;
        private final OrderRepository orderRepository;
        private final BasketService basketService;

        public void dbInit() {
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

            List<Delivery> deliveries = new ArrayList<>();
            for(int i=1; i<31; i++){
                deliveries.add(new Delivery());
            }

            ArrayList<BasketItem> basketItems = new ArrayList<>();
            for(int i=1; i<31; i++){
                basketItems.add(new BasketItem(items.get(i - 1), 10));
            }

            ArrayList<Basket> baskets = new ArrayList<>();
            for(int i=1; i<31; i++){
                if(i == 1){
                    BasketItem basketItem = new BasketItem(items.get(20), 20);
                    Basket basket = new Basket(members.get(i - 1), basketItem, basketItems.get(i - 1));
                    baskets.add(basket);
                    basketRepository.save(basket);

                } else {
                    Basket basket = new Basket(members.get(i - 1), basketItems.get(i - 1));
                    baskets.add(basket);
                    basketRepository.save(basket);
                }
            }

            for(int i=1; i<31; i++){
                if(i == 1) continue;
                Order order = new Order(members.get(i - 1), deliveries.get(i - 1), baskets.get(i - 1));
                basketService.clearBasket(baskets.get(i-1));
                orderRepository.save(order);
            }
        }
    }
}
