package com.auction.server.service.item;

import com.auction.common.dto.request.GetItemRequestDTO;
import com.auction.common.dto.request.ItemRequestDTO;
import com.auction.common.dto.response.ItemResponseDTO;
import com.auction.common.dto.response.GetItemReponseDTO;
import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.Item;
import com.auction.server.dao.ItemDAO;
import com.auction.server.exception.DatabaseException;


import java.sql.SQLException;
import java.util.List;

public class ItemService {
    public GetItemReponseDTO getItemBySellerId(GetItemRequestDTO getItemRequestDTO) throws DatabaseException {
        int sellerId= getItemRequestDTO.getSellerId();

        List<Item> itemList=new ItemDAO().getItemBySellerId(sellerId);

        return new GetItemReponseDTO(sellerId,itemList);
    }
    public ItemResponseDTO addItem(Item item) throws Exception {
        ItemResponseDTO itemResponseDTO =new ItemResponseDTO();

        new ItemDAO().addItem(item);

        itemResponseDTO.setItem(item);

        return itemResponseDTO;

    }
    public ItemResponseDTO removeItem(ItemRequestDTO itemRequestDTO) {
        ItemResponseDTO itemResponseDTO=new ItemResponseDTO();

        int itemId= itemRequestDTO.getItemId();

        try {
            new ItemDAO().updateItemStatus(itemId,ItemStatus.INVALID);
            itemResponseDTO.setMessage("remove item successfully");
        } catch (SQLException e) {
            itemResponseDTO.setMessage(e.getMessage());
        }

        return itemResponseDTO;

    }
}
