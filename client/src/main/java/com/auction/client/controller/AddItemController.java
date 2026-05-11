package com.auction.client.controller;

import com.auction.common.enums.ItemStatus;
import com.auction.common.model.Item.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddItemController {
    @FXML private ComboBox<String> cbItemType;
    @FXML private TextField txtName, txtPrice;
    @FXML private TextArea txtDescription;
    @FXML private VBox vboxDynamicFields;

    private TextField txtExtra; // Ô nhập liệu động cho Tác giả/Hãng/Bảo hành

    @FXML
    public void initialize() {
        // Xóa trắng dữ liệu cũ nếu có và thêm các tùy chọn
        cbItemType.getItems().clear();
        cbItemType.getItems().addAll("Nghệ thuật (Art)", "Điện tử (Electronics)", "Phương tiện (Vehicle)");
    }

    @FXML
    private void handleTypeChange() {
        vboxDynamicFields.getChildren().clear();
        String type = cbItemType.getValue();
        if (type == null) return;

        Label lbl = new Label();
        txtExtra = new TextField();

        // Gán style từ CSS để đồng bộ giao diện
        txtExtra.getStyleClass().add("form-input");

        if (type.contains("Art")) {
            lbl.setText("Tên tác giả:");
            txtExtra.setPromptText("Ví dụ: Leonardo da Vinci");
        } else if (type.contains("Electronics")) {
            lbl.setText("Tháng bảo hành:");
            txtExtra.setPromptText("Ví dụ: 12");
        } else if (type.contains("Vehicle")) {
            lbl.setText("Hãng sản xuất:");
            txtExtra.setPromptText("Ví dụ: Toyota, Mercedes...");
        }

        vboxDynamicFields.getChildren().addAll(lbl, txtExtra);
    }

    @FXML
    private void handleSave() {
        try {
            String type = cbItemType.getValue();
            if (type == null) {
                showAlert("Lỗi", "Vui lòng chọn loại mặt hàng!");
                return;
            }

            String name = txtName.getText();
            String desc = txtDescription.getText();
            double price = Double.parseDouble(txtPrice.getText());

            // Ở đây bạn cần lấy đối tượng Seller đang đăng nhập (Ví dụ từ Session)
            // Seller currentSeller = (Seller) Session.getInstance().getCurrentUser();

            Item newItem = null;

            if (type.contains("Art")) {
                newItem = new Art(0, name, desc, price, null, ItemStatus.AVAILABLE, txtExtra.getText());
            } else if (type.contains("Electronics")) {
                int warranty = Integer.parseInt(txtExtra.getText());
                newItem = new Electronics(0, name, desc, price, null, ItemStatus.AVAILABLE, warranty);
            } else if (type.contains("Vehicle")) {
                newItem = new Vehicle(0, name, desc, price, null, ItemStatus.AVAILABLE, txtExtra.getText());
            }

            // GỬI QUA SOCKET LÊN SERVER
            // ObjectOutputStream out = SocketManager.getInstance().getOut();
            // out.writeObject(new Request("ADD_ITEM", newItem));

            System.out.println("Đã tạo thành công: " + newItem.getName());
            closeStage();

        } catch (NumberFormatException e) {
            showAlert("Lỗi định dạng", "Giá tiền hoặc thông số kỹ thuật phải là số!");
        } catch (Exception e) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin!");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}