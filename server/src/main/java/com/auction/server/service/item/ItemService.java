package com.auction.server.service.item;

import com.auction.common.dto.request.GetItemRequestDTO;
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
}
