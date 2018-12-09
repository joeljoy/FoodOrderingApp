package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Item;
import org.upgrad.repositories.ItemRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item getById(Integer itemId) {
        return itemRepository.getById(itemId);
    }
}
