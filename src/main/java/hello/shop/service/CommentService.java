package hello.shop.service;

import hello.shop.entity.*;
import hello.shop.repository.basket.BasketRepository;
import hello.shop.repository.comment.CommentRepository;
import hello.shop.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(String content, Integer score, Item item, Member member){
        Comment comment = Comment.createComment(content, score, item, member);
        commentRepository.save(comment);
        return comment;
    }
}
