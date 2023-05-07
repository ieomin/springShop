package hello.shop.web.form.item;

import hello.shop.entity.BasketItem;
import hello.shop.entity.Comment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemDetailForm {
    private Long id;
    private String memberName;
    private String name;
    private int price;
    private int quantity;
    // 결과: 먼저 생성이 되고 바인딩이 되겄네
    private List<String> basketMemberNames;
    private List<String> orderMemberNames = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private Comment comment;
    private Integer score;
}

