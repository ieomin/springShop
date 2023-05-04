package hello.shop;

import hello.shop.entity.*;
import hello.shop.repository.basket.BasketRepository;
import hello.shop.repository.item.ItemRepository;
import hello.shop.repository.member.MemberRepository;
import hello.shop.repository.order.OrderRepository;
import hello.shop.service.BasketService;
import hello.shop.service.ItemService;
import hello.shop.service.MemberService;
import hello.shop.service.OrderService;
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

        private final MemberService memberService;
        private final ItemService itemService;
        private final OrderService orderService;
        private final BasketService basketService;

        public void dbInit() {

            ArrayList<Address> addresses = new ArrayList<>();
            List<Member> members = new ArrayList<>();
            List<Item> items = new ArrayList<>();
            List<Delivery> deliveries = new ArrayList<>();
            ArrayList<BasketItem> basketItems = new ArrayList<>();
            ArrayList<Basket> baskets = new ArrayList<>();

            for(int i=0; i<30; i++){
                Address address = new Address(String.valueOf(i+1), String.valueOf(i+1), String.valueOf(i+1));
                addresses.add(address);
            }

            for(int i=0; i<30; i++){
                Member member = memberService.createMember(String.valueOf(i+1), String.valueOf(i+1), "member" + (i+1), addresses.get(i));
                members.add(member);
            }

            for(int i=0; i<30; i++){
                Item item = itemService.createItem("item" + (i+1), 1000, 100, members.get(i));
                items.add(item);
            }

            for(int i=0; i<30; i++){
                Delivery delivery = new Delivery();
                deliveries.add(delivery);
            }

            for(int i=0; i<30; i++){
                BasketItem basketItem = new BasketItem(items.get(i), 10);
                basketItems.add(basketItem);
            }

            for(int i=0; i<30; i++){
                if(i == 0){
                    Basket basket = basketService.createBasket(members.get(i), basketItems.get(i), new BasketItem(items.get(20), 20));
                    baskets.add(basket);

                } else {
                    Basket basket = basketService.createBasket(members.get(i), basketItems.get(i));
                    baskets.add(basket);
                }
            }

            for(int i=0; i<30; i++){
                if(i == 0) continue;
                Order order = Order.createOrder(members.get(i), deliveries.get(i), baskets.get(i));
                basketService.clearBasket(baskets.get(i));
                orderService.save(order);
            }
        }
    }
}
