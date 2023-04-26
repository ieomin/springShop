package hello.shop.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", null, "기본 메시지", Locale.ENGLISH);
        Assertions.assertThat(result).isEqualTo("hello Spring");
    }
}