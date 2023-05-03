package hello.shop.beanfind;

import hello.shop.AppConfig;
import hello.shop.ShopApplication;
import hello.shop.repository.basket.BasketRepository;
import hello.shop.repository.member.MemberRepository;
import hello.shop.service.ItemService;
import hello.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ACInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ShopApplication.class);
    @Test
    void basicScan() {
        MemberRepository memberRepository = ac.getBean(MemberRepository.class);
        System.out.println("memberRepository = " + memberRepository);
    }

    @Test @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            // Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈 Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " // object = " + bean);
            }
        }
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        Map<String, BasketRepository> beansOfType = ac.getBeansOfType(BasketRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("name = " + key + " // object = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
    }
}
