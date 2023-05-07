package hello.shop;

import hello.shop.entity.*;
import hello.shop.service.*;
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
        private final CommentService commentService;

        public void dbInit() {

            List<Member> members = new ArrayList<>();
            List<Item> items = new ArrayList<>();

            for(int i=0; i<30; i++){
                members.add(memberService.createMember(String.valueOf(i+1), String.valueOf(i+1), "member" + (i+1), new Address("cityStreetZipcode" + (i+1))));
                items.add(itemService.createItem("item" + (i+1), 1000, 100, members.get(i)));
                commentService.createComment("content" + (i+1), 5, items.get(i), members.get(i));
                basketService.updateBasket(members.get(i).getBasket().getId(), items.get(i).getId(), 10);
                if(i != 0) {
                    orderService.createOrder(members.get(i), new Delivery(new Address("cityStreetZipcode" + (i+1))), members.get(i).getBasket(), members.get(i).getBasket().getTotalPrice());
                }
            }
            basketService.updateBasket(members.get(0).getBasket().getId(), items.get(20).getId(), 20);
        }
    }
}
