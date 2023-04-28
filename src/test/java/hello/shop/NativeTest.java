package hello.shop;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class NativeTest {

    @Test
    public void classTest(){
        Parent child1 = new Child();
        child1.speakParent();
        Child child2 = (Child) child1;
        child2.speakChild();
        child2.speakParent();

        // 결론: 감싸주는게 더 크면 안됨 그리고 형변환 해도 안됨 그리고 감싸주는 거에 T가 들어감
//        Child parent = new Parent();
//        Child child3 = (Child) new Parent();
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

    @Test
    public void optionalTest(){
        Optional<String> optionalMessage = Optional.ofNullable("Hello, World!");
        // 팁: orElse를 하면 무조건 String 반환 확정
        // 결과: 널 참조를 막을 수 있도록 사용해야 가치 있을 듯
        String result = optionalMessage.orElse("Default message");

        if (optionalMessage.isPresent()) {
            System.out.println(optionalMessage.get());
        } else {
            System.out.println("메시지가 없습니다.");
        }

        String message = "Hello, World!";
        if(message != null){
            System.out.println(message);
        } else{
            System.out.println("메시지가 없습니다.");
        }
    }

}
