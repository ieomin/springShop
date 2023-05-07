package hello.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Comment extends Base{

    @Id @GeneratedValue @Column(name = "comment_id")
    private Long id;

    private String content;

    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    public static Comment createComment(String content, Integer score, Item item, Member member){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setScore(score);
        comment.setItem(item);
        comment.setMember(member);
        return comment;
    }
}
