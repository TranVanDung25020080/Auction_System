package com.auction.client.controller.sellerdashboard.buttonhandler;

import com.auction.client.controller.annoucement.Alert;
import com.auction.client.controller.itemcardseller.ItemCardSellerController;
import com.auction.client.controller.sellerdashboard.SellerDashboardController;
import com.auction.client.network.http.ItemAPI;
import com.auction.common.dto.request.GetItemRequestDTO;
import com.auction.common.dto.response.GetItemReponseDTO;
import com.auction.common.model.Item.Item;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ShowIntentoryButton {
    private FlowPane flowPaneContent;
    private int sellerId;

    public void handle(SellerDashboardController sellerDashboardController){
        Label lblHeader=sellerDashboardController.getLblHeader();
        this.flowPaneContent=sellerDashboardController.getFlowPaneContent();

        lblHeader.setText("KHO HÀNG CỦA TÔI");
        flowPaneContent.getChildren().clear();

        //Goi API Lay dach sach that :
        GetItemReponseDTO getItemReponseDTO=new GetItemReponseDTO();

        try{
            this.sellerId=sellerDashboardController.getSellerId();
            GetItemRequestDTO getItemRequestDTO=new GetItemRequestDTO(sellerId);


            getItemReponseDTO=new ItemAPI().getItemBySellerId(getItemRequestDTO);



        } catch (IOException e) {
            Alert.showAlert("ERORR",e.getMessage());
            e.printStackTrace();
        }

        List<Item> mockItems = getItemReponseDTO.getItemList();

        renderInventory(mockItems);
        System.out.println(mockItems);
        //

    }

    private void renderInventory(List<Item> itemList) {
        flowPaneContent.getChildren().clear();
        if (itemList.size()!=0){
            for (Item item : itemList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/item_card_seller.fxml"));
                    VBox card = loader.load();

                    ItemCardSellerController controller = loader.getController();
                    controller.setItemData(item,this.sellerId);

                    flowPaneContent.getChildren().add(card);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
