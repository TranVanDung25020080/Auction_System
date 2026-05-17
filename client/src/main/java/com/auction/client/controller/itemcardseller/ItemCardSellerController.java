package com.auction.client.controller.itemcardseller;

import com.auction.client.controller.openauctionpopup.OpenAuctionPopupController;
import com.auction.common.model.Item.Art;
import com.auction.common.model.Item.Electronics;
import com.auction.common.model.Item.Item;
import com.auction.common.model.Item.Vehicle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ItemCardSellerController {
    //FXML fields:
    @FXML private VBox cardContainer;
    @FXML private Label lblBadge;
    @FXML private Label lblItemName;
    @FXML private Label lblPrice;
    @FXML private Label lblExtraInfo;
    @FXML private Button startAuctionButton,deleteItemButton;
    //Other fields:
    private Item item;
    private int sellerId;

    /**
     * Đổ dữ liệu từ Item model vào Card UI
     */
    public void setItemData(Item item,int sellerId) {
        this.item = item;
        this.sellerId=sellerId;

        lblItemName.setText(item.getName());
        lblPrice.setText(String.format("%,.0f VNĐ", item.getInitialPrice()));

        // Reset style badge để tránh lỗi hiển thị khi tái sử dụng card
        lblBadge.getStyleClass().removeAll("badge-art", "badge-electronics", "badge-vehicle");

        if (item instanceof Art) {
            Art art = (Art) item;
            lblBadge.setText("NGHỆ THUẬT");
            lblBadge.getStyleClass().add("badge-art");
            lblExtraInfo.setText("Tác giả: " + art.getAuthor());
        }
        else if (item instanceof Electronics) {
            Electronics elec = (Electronics) item;
            lblBadge.setText("ĐIỆN TỬ");
            lblBadge.getStyleClass().add("badge-electronics");
            lblExtraInfo.setText("Bảo hành: " + elec.getWarranty() + " tháng");
        }
        else if (item instanceof Vehicle) {
            Vehicle vehi = (Vehicle) item;
            lblBadge.setText("PHƯƠNG TIỆN");
            lblBadge.getStyleClass().add("badge-vehicle");
            lblExtraInfo.setText("Hãng xe: " + vehi.getCompany());
        }
    }

    /**
     * Mở Popup thiết lập thông số đấu giá
     */
    @FXML
    private void handleStartAuction() {
        if (item == null) return;

        try {
            // 1. Load FXML của Popup mở đấu giá
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/auction/client/view/open_auction_popup.fxml"));
            Parent root = loader.load();

            // 2. Truyền dữ liệu Item sang Popup Controller
            OpenAuctionPopupController controller = loader.getController();
            controller.setItemData(this.item,this.sellerId);

            // 3. Hiển thị cửa sổ dạng Modal (bắt buộc xử lý xong mới quay lại được)
            Stage stage = new Stage();
            stage.setTitle("Thiết lập đấu giá: " + item.getName());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // 4. Nếu người dùng xác nhận "Bắt đầu" trên Popup thì mới xóa Card khỏi kho
            if (controller.isConfirmed()) {
                removeCardFromUI();
                System.out.println("Sản phẩm đã được đưa lên sàn đấu giá.");
            }

        } catch (IOException e) {
            System.err.println("Lỗi mở popup mở đấu giá: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Xác nhận và xóa sản phẩm khỏi giao diện
     */
    @FXML
    private void handleDelete() {
        if (item == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xóa");
        confirm.setHeaderText("Bạn có chắc chắn muốn xóa sản phẩm này khỏi kho?");
        confirm.setContentText(item.getName());

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            removeCardFromUI();

            // TODO: Gửi lệnh xóa (id) lên Server qua Socket
            System.out.println("Đã gửi yêu cầu xóa sản phẩm ID: " + item.getId());
        }
    }

    /**
     * Hàm phụ trợ để xóa Card này ra khỏi FlowPane cha
     */
    private void removeCardFromUI() {
        FlowPane parent = (FlowPane) cardContainer.getParent();
        if (parent != null) {
            parent.getChildren().remove(cardContainer);
        }
    }
    //method for other classes to call:
    public Item getItem(){
        return this.item;
    }
    public int getSellerId(){
        return this.sellerId;
    }
}