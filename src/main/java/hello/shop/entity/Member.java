package hello.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 결론: 추가 생성자가 붙으면 기본 생성자도 추가해줘야 함. 근데 이왕이면 스태틱메소드로 추가 생성자 역할을 대체하는 것이 코드 깔끔함
// 결론: 연관삭제O: 게시판&코드복잡성 연관삭제X: 쇼핑몰 취소: 기본키보존
// 팁: 나는 1인쪽에서 add함수를 걸기로 약속함
@Entity
@Getter @Setter
@NoArgsConstructor
public class Member extends Base{

    @Id @GeneratedValue @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Basket basket;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item){
        this.items.add(item);
        item.setMember(this);
    }

    public Member(String loginId, String password, String name, Address address){
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.address = address;
    }
}
