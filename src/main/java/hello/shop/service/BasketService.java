package hello.shop.service;

import hello.shop.entity.*;
import hello.shop.exception.NotAllowCanceledOrderException;
import hello.shop.repository.basket.BasketRepository;
import hello.shop.repository.item.ItemRepository;
import hello.shop.repository.order.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void updateBasket(Long basketId, Long itemId, Integer count){
        Basket basket = basketRepository.findById(basketId).get();
        Item item = itemRepository.findById(itemId).get();
        basket.addBasketItem(new BasketItem(item, count));
    }

    public List<Basket> findByMemberId(Long memberId) {
        List<Basket> baskets = basketRepository.findByMemberId(memberId);
        return baskets;
    }
}
