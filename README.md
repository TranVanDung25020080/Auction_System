# 🏛️ Cấu Trúc Dự Án: Hệ Thống Đấu Giá (Auction System)

Dự án này được xây dựng theo kiến trúc **Client-Server**, áp dụng mô hình **MVC** cho giao diện người dùng (JavaFX) và **DAO Pattern** cho cơ sở dữ liệu. Mã nguồn được chia thành 3 module riêng biệt quản lý bằng Maven để đảm bảo tính module hóa và dễ bảo trì (Separation of Concerns).

Dưới đây là chi tiết công dụng của từng package trong hệ thống:

---

## 📦 1. Module `common` (Thư viện dùng chung)
*Đóng vai trò là "ngôn ngữ chung" giúp Client và Server giao tiếp. Tuyệt đối không chứa logic giao diện hay kết nối cơ sở dữ liệu.*

* **`com.auction.common.model`**: Chứa các lớp Thực thể (Entity) đại diện cho các bảng trong CSDL (ví dụ: `User`, `Item`, `Bid`).

* **`com.auction.common.dto`**: Chứa các đối tượng Data Transfer Object. Dùng để đóng gói dữ liệu gửi qua mạng, giúp ẩn giấu thông tin nhạy cảm (như mật khẩu, CCCD) và tối ưu băng thông (ví dụ: `UserDTO`, `AuctionItemDTO`).

* **`com.auction.common.network`**: Định nghĩa cấu trúc gói tin giao tiếp giữa Client và Server (ví dụ: `Request` chứa lệnh yêu cầu, `Response` chứa kết quả trả về kèm chuỗi JSON).

* **`com.auction.common.exception`**: Chứa các class ngoại lệ tự định nghĩa (Custom Exceptions) để xử lý các lỗi nghiệp vụ chung (ví dụ: `InvalidBidException`, `AuctionEndedException`).

* **`com.auction.common.enums`**: Chứa các hằng số tĩnh của hệ thống để tránh sai sót khi gõ chuỗi (ví dụ: `Role` cho phân quyền, `ItemStatus` cho trạng thái sản phẩm).

---

## ⚙️ 2. Module `server` (Backend - Xử lý nghiệp vụ & Database)
*Khối óc của hệ thống, chịu trách nhiệm kết nối CSDL, xử lý logic, đồng bộ hóa đa luồng (chống Race Condition) và phản hồi Client.*

**Phần Source Code (`src/main/java`)**
* **`com.auction.server.main`**: Điểm khởi chạy của Server (lưu ý đặt tên class là `MainServer` cho khớp vs file pom.xml nhé) để mở Server Socket lắng nghe kết nối.

* **`com.auction.server.dao`**: (Data Access Object) Tầng duy nhất giao tiếp trực tiếp với MySQL/SQL Server. Chứa các câu lệnh SQL để truy vấn và cập nhật dữ liệu (ví dụ: `DBConnection`, `UserDAO`, `ItemDAO`).

* **`com.auction.server.service`**: Tầng nghiệp vụ cốt lõi. Nhận Request, kiểm tra tính hợp lệ của dữ liệu, áp dụng các thuật toán đấu giá và quản lý luồng đồng thời (`synchronized`) trước khi gọi DAO lưu dữ liệu (ví dụ: `UserService`, `AuctionService`).

* **`com.auction.server.network`**: Quản lý mạng và Socket. Cấp cho mỗi Client một luồng (`ClientHandler`) riêng biệt và quản lý danh sách Client đang online (`SessionManager`) để gửi dữ liệu Realtime.

* **`com.auction.server.dp`**: Áp dụng các mẫu thiết kế cần thiết (ví dụ: Factory Method) để thay thế việc sử dụng if-else hay switch-case.


**Phần Resources (`src/main/resources`)**
* Nằm trực tiếp ở thư mục gốc của resources. Chứa các file cấu hình hệ thống (ví dụ: `database.properties` lưu cấu hình chuỗi kết nối DB, mật khẩu, port).

**Phần Unit Test (`src/test/java`)**
* Chứa các kịch bản kiểm thử (JUnit) cho các logic quan trọng, ví dụ như test thuật toán đấu giá với hàng trăm người gọi cùng lúc để kiểm tra tính toàn vẹn dữ liệu.

---

## 💻 3. Module `client` (Frontend - Giao diện người dùng)
*Gương mặt của hệ thống, xử lý hiển thị giao diện bằng JavaFX, bắt sự kiện người dùng và giao tiếp với Server.*

**Phần Source Code (`src/main/java`)**
* **`com.auction.client.main`**: Chứa class kế thừa từ `Application` của JavaFX, dùng để khởi chạy app và nạp màn hình đầu tiên (tương tự nhớ đạt tên class là MainApplication nhóe)

* **`com.auction.client.controller`**: Bộ điều khiển (Chữ C trong MVC). Gắn kết trực tiếp với các file `.fxml`, bắt sự kiện click/nhập liệu, gửi Request lên Server và cập nhật dữ liệu trả về lên màn hình (ví dụ: `LoginController`, `AuctionRoomController`).

* **`com.auction.client.service`**: Chịu trách nhiệm duy trì Socket kết nối tới Server, gửi gói tin đi và chạy luồng nền để lắng nghe `Response` Realtime từ Server (`ServerConnection`).

* **`com.auction.client.util`**: Chứa các lớp hỗ trợ tiện ích cho UI (ví dụ: `Session` lưu thông tin người dùng đang đăng nhập, `DialogHelper` hiển thị popup cảnh báo lỗi/thành công).

**Phần Resources (`src/main/resources`)**
*Cấu trúc thư mục con bắt buộc phải khớp 100% với package bên java để JavaFX có thể nạp được file.*

* **`com/auction/client/view`**: Chứa các file giao diện tĩnh thiết kế bằng Scene Builder (`.fxml`).

* **`com/auction/client/css`**: Chứa các file định dạng (`.css`) để trang trí, làm đẹp nút bấm, bố cục.

* **`com/auction/client/images`**: Chứa các tài nguyên hình ảnh (logo, icon) dùng trong ứng dụng.


## 🛠️ Hướng Dẫn Cài Đặt & Chạy Dự Án

Sử dụng Terminal tại thư mục gốc của dự án (`Auction_System`) để thực hiện các lệnh sau:

### Bước 1: Biên dịch toàn dự án (Chạy 1 lần khi có thay đổi code)
Lệnh này giúp làm sạch và đóng gói lại các module để chúng nhận diện được nhau.
```bash
mvn clean install
```
### Bước 2: Khởi động Server (CHẠY 1 LẦN DUY NHẤT)
Server đóng vai trò là trung tâm điều phối, chỉ cần 1 Server chạy để quản lý tất cả các kết nối.

```bash
mvn exec:java -pl server
```
(Giữ nguyên cửa sổ này để Server duy trì hoạt động).

### Bước 3: Khởi động Client (CHẠY NHIỀU LẦN ĐỂ TEST)
Lệnh này mở giao diện người dùng. Có thể mở nhiều tab Terminal khác nhau và chạy lệnh này nhiều lần để tạo ra nhiều người tham gia đấu giá cùng lúc.

```bash
mvn javafx:run -pl client
```
Lần 1: Mở cửa sổ cho Người dùng A.

Lần 2: Mở cửa sổ cho Người dùng B.

Lần n: Mở thêm bao nhiêu người tùy thích để test tính năng realtime.