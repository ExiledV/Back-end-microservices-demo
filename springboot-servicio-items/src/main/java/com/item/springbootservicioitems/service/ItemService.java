package com.item.springbootservicioitems.service;

import java.util.List;

import com.item.springbootservicioitems.model.Item;

public interface ItemService {

    public List<Item> findAll();
    public Item findById(Long id, Integer cantidad);
}
