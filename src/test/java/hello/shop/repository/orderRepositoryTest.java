package hello.shop.repository;

import hello.shop.entity.*;
import hello.shop.repository.item.ItemRepository;
import hello.shop.repository.member.MemberRepository;
import hello.shop.repository.order.OrderDtoV2;
import hello.shop.repository.order.OrderRepository;
import hello.shop.repository.order.OrderSearchCond;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class orderRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void beforeEach(){
        Address address1 = new Address("A", "A", "A");
        Address address2 = new Address("B", "B", "B");
        Member member1 = new Member("1", "1", "member1", address1);
        Member member2 = new Member("2", "2", "member2", address2);
        Delivery delivery1_1 = new Delivery(member1.getAddress());
        Delivery delivery1_2 = new Delivery(member1.getAddress());
        Item item1 = new Item("item1", 1000, 10);
        Item item2 = new Item("item2", 2000, 20);
        OrderItem orderItem1 = new OrderItem(item2, 1);
        OrderItem orderItem2 = new OrderItem(item1, 1);
        Order order1 = new Order(member1, delivery1_1, orderItem1);
        Order order2 = new Order(member2, delivery1_2, orderItem2);
        memberRepository.save(member1);
        memberRepository.save(member2);
        itemRepository.save(item1);
        itemRepository.save(item2);
        orderRepository.save(order1);
        orderRepository.save(order2);
    }

    @Test
    public void basicTest(){
        OrderSearchCond cond = new OrderSearchCond();
        cond.setMemberName("member1");
        cond.setOrderStatus(OrderStatus.ORDER);
        List<OrderDtoV2> orderDtoV2s = orderRepository.searchV2(cond);
        for (OrderDtoV2 o : orderDtoV2s) {
            System.out.println("itemName = " + o.getItemName());
            System.out.println("memberName = " + o.getMemberName());
            System.out.println("itemQuantity = " + o.getItemQuantity());
        }
    }

    @Test
    public void classTest(){
        Parent child1 = new Child();
        child1.speakParent();
        Child child2 = (Child) child1;
        child2.speakChild();
        child2.speakParent();

        // 결론: 감싸주는게 더 크면 안됨 그리고 감싸주는 거에 T가 들어감
//        Child parent = new Parent();
        Child child3 = (Child) new Parent();
        child3.speakChild();
        child3.speakParent();
    }
    
    public static class Child extends Parent{
        public void speakChild(){
            System.out.println("밥 줘");
        }
    }
    public static class Parent{
        public void speakParent(){
            System.out.println("밥 줄게");
        }
    }

}
