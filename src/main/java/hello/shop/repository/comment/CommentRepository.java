package hello.shop.repository.comment;

import hello.shop.entity.Basket;
import hello.shop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{
}
