package hello.shop;

import hello.shop.repository.item.ItemRepository;
import hello.shop.repository.member.MemberRepository;
import hello.shop.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Optional;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    // 팁: @Service와 수동등록을 통해 등록
    // 결론: Bean으로 등록된 객체는 new로 생성하면 안되겠네

    // 결론: @Bean이 붙은 메소드의 인자가 찾는 빈이 단 하나만 있을 경우 @Autowired를 생략해도 됨
    // 결론: 메소드인자에 주입이 필요한 함수는 갖다 쓰기 불편함 여태껏 인자에 @Autowired가 있는 함수를 직접 호출한 코드가 존재하지 않았으니 코드가 깔끔했던 것 직접 호출할려면 함수 형태로 만들어
//    @Bean
//    public MemberService memberService(@Autowired MemberRepository memberRepository){
//        return new MemberService(memberRepository);
//    }

//    @Bean
//    public TestDataInit testDataInit(){
//        return new TestDataInit(memberRepository, itemRepository, orderRepository);
//    }
//
//    @Bean
//    public MemberService memberService(){
//        return new MemberService(memberRepository);
//    }
//
//    @Bean
//    public ItemService itemService(){
//        return new ItemService(itemRepository);
//    }
//
//    @Bean
//    public OrderService orderService(){
//        return new OrderService(orderRepository);
//    }
//
//    // 팁: 함수간의 앞뒤관계는 상관 없음
//    @Bean
//    public MemberApiController memberApiController(){
//        return new MemberApiController(memberService());
//    }
//
//    @Bean
//    public OrderApiController orderApiController(){
//        return new OrderApiController(orderService());
//    }

}