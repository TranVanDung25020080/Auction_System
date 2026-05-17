package com.auction.server.service.item;

import com.auction.common.dto.request.GetItemRequestDTO;
import com.auction.common.dto.response.AddItemResponseDTO;
import com.auction.common.dto.response.GetItemReponseDTO;
import com.auction.common.model.Item.Item;
import com.auction.server.dao.ItemDAO;
import com.auction.server.exception.DatabaseException;


import java.util.List;

public class ItemService {
    public GetItemReponseDTO getItemBySellerId(GetItemRequestDTO getItemRequestDTO) throws DatabaseException {
        int sellerId= getItemRequestDTO.getSellerId();

        List<Item> itemList=new ItemDAO().getItemBySellerId(sellerId);

        return new GetItemReponseDTO(sellerId,itemList);
    }
    public AddItemResponseDTO addItem(Item item) throws Exception {
        AddItemResponseDTO addItemResponseDTO=new AddItemResponseDTO();

        new ItemDAO().addItem(item);

        addItemResponseDTO.setItem(item);

        return addItemResponseDTO;

    }
}
