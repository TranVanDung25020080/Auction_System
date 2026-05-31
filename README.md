# 🏷️ Hệ Thống Đấu Giá Trực Tuyến (Auction System)

**Đồ án / Bài tập lớn môn học**

---

## 1. Mô tả bài toán và phạm vi hệ thống

**Bài toán:**
Hệ thống giải quyết nhu cầu định giá và giao dịch tài sản trực tuyến thông qua hình thức đấu giá thời gian thực (Real-time). Thách thức lớn nhất đặt ra đối với nền tảng là phải đảm bảo tính toàn vẹn dữ liệu khi có nhiều người cùng đặt giá tại một mili-giây, đồng thời duy trì kết nối ổn định liên tục giữa máy chủ và nhiều máy khách.

**Phạm vi hệ thống:**
Ứng dụng hoạt động theo kiến trúc Client-Server, phân quyền thành 3 nhóm người dùng chính:
* **Quản trị viên (Admin):** Quản lý hệ thống, người dùng và theo dõi thống kê các phiên đấu giá.
* **Người bán (Seller):** Đăng tải hàng hóa, thiết lập giá khởi điểm và thời gian bắt đầu phiên.
* **Người mua (Bidder):** Tham gia vào các phòng đấu giá trực tuyến, theo dõi biến động giá realtime và thực hiện đặt giá (Bid).

---

## 2. Công nghệ sử dụng, môi trường chạy và yêu cầu cài đặt

**Công nghệ cốt lõi:**
* **Ngôn ngữ lập trình:** Java (Core & Đa luồng)
* **Giao diện Frontend:** JavaFX, FXML (Scene Builder), CSS
* **Giao tiếp mạng:** Java Socket (TCP/IP)
* **Cơ sở dữ liệu:** MySQL / MariaDB (Triển khai trên Cloud Railway)
* **Công cụ Quản lý & Build:** Apache Maven
* **Kiểm thử (Testing):** JUnit 5

**Yêu cầu môi trường & Cài đặt:**
* **Hệ điều hành:** Đa nền tảng (Windows, macOS, Linux).
* **Môi trường:** Máy tính cần cài đặt JDK (phiên bản 17 hoặc 21) và đã thiết lập biến môi trường `JAVA_HOME`.
* **Mạng:** Bắt buộc có kết nối Internet để ứng dụng giao tiếp với Server và Cơ sở dữ liệu đám mây. Không yêu cầu cài đặt MySQL/XAMPP dưới Localhost.

---

## 3. Cấu trúc thư mục và các module chính

Dự án áp dụng mô hình **MVC** cho giao diện và **DAO Pattern** cho CSDL. Mã nguồn được chia thành 3 module độc lập quản lý bởi Maven nhằm đảm bảo tính Separation of Concerns:

```text
📦 Auction_System
 ┣ 📂 common (Thư viện giao tiếp dùng chung)
 ┃ ┣ 📂 model     : Thực thể (Entity) đại diện cho các bảng CSDL (User, Item, Bid)
 ┃ ┣ 📂 dto       : Data Transfer Object đóng gói dữ liệu qua mạng
 ┃ ┣ 📂 network   : Định nghĩa cấu trúc gói tin (Request/Response)
 ┃ ┣ 📂 exception : Xử lý lỗi nghiệp vụ tùy chỉnh (InvalidBidException...)
 ┃ ┗ 📂 enums     : Tập hằng số cấu hình hệ thống (Role, ItemStatus)
 ┃
 ┣ 📂 server (Backend - Xử lý nghiệp vụ & Database)
 ┃ ┗ 📂 src/main/java/com.auction.server
 ┃   ┣ 📂 dao       : Data Access Object tương tác trực tiếp với MySQL/MariaDB.
 ┃   ┣ 📂 db        : Cấu hình và quản lý kết nối Cơ sở dữ liệu.
 ┃   ┣ 📂 dp        : Chứa các Design Patterns (Mẫu thiết kế) áp dụng trong hệ thống.
 ┃   ┣ 📂 exception : Định nghĩa các ngoại lệ (lỗi) tùy chỉnh phía Server.
 ┃   ┣ 📂 handler   : Quản lý luồng xử lý độc lập cho từng Client kết nối (ClientHandler).
 ┃   ┣ 📂 main      : Điểm khởi chạy Server Socket.
 ┃   ┣ 📂 network   : Quản lý giao tiếp mạng, luồng Server và SessionManager.
 ┃   ┣ 📂 routes    : Điều hướng và định tuyến các endpoint / yêu cầu xử lý.
 ┃   ┣ 📂 service   : Tầng nghiệp vụ cốt lõi, xử lý đồng bộ và kiểm tra logic hệ thống.
 ┃   ┗ 📂 util      : Các lớp tiện ích hỗ trợ (ví dụ: băm mật khẩu, format ngày tháng).
    📂 src/main/resources
      ┗ ⚙️ config.properties   : Tách biệt các thông số cấu hình (configuration) ra khỏi mã nguồn (source code)
 ┃
 ┗ 📂 client (Frontend - Giao diện JavaFX)
   ┣ 📂 src/main/java/com.auction.client
   ┃ ┣ 📂 controller: Điều khiển giao diện, bắt sự kiện thao tác của người dùng.
   ┃ ┣ 📂 main      : Điểm nạp giao diện và khởi chạy ứng dụng JavaFX.
   ┃ ┣ 📂 network   : Lớp xử lý kết nối mạng (Socket Client / HTTP) gửi nhận gói tin.
   ┃ ┣ 📂 service   : Quản lý các tác vụ luồng nền, lắng nghe Response realtime.
   ┃ ┗ 📂 util      : Tiện ích hỗ trợ giao diện (Load cấu hình, Session, Dialog cảnh báo).
   ┃
   ┗ 📂 src/main/resources
     ┣ 📂 com.auction.client : Chứa các file tĩnh như giao diện (.fxml), thiết kế (.css) và hình ảnh.
     ┗ ⚙️ config.properties   : Tệp cấu hình lưu tham số mạng (Host, Port, URL Server).
```

---

## 4. Hướng dẫn khởi chạy ứng dụng bằng Command Line

Hệ thống sử dụng các plugin của Maven nên thao tác khởi chạy hoàn toàn thống nhất và **chạy được trên mọi Terminal/Command Prompt của Windows, Linux, hoặc macOS**.

### Bước 1: Biên dịch toàn dự án (Chỉ chạy 1 lần)
Mở Terminal tại thư mục gốc của dự án (nơi chứa file `pom.xml` cha) để tải thư viện và liên kết các module:
```bash
mvn clean install
```

### Bước 2: Khởi động Server (Bắt buộc chạy trước)
Chuyển hướng Terminal (hoặc mở Terminal mới tại thư mục gốc) và chạy lệnh:
```bash
mvn exec:java -pl server
```
*(Lưu ý: Cần giữ nguyên cửa sổ này để Server liên tục hoạt động và điều phối các kết nối).*

### Bước 3: Khởi động Client (Có thể chạy nhiều cửa sổ)
Mở một cửa sổ Terminal **hoàn toàn mới** và chạy lệnh:
```bash
mvn javafx:run -pl client
```
*(Mẹo: Có thể mở nhiều tab Terminal và chạy lệnh này lặp đi lặp lại để tạo ra 2, 3, hay nhiều Client giả lập các người dùng khác nhau cùng tham gia vào một phòng đấu giá).*

---

## 5. Danh sách các chức năng đã hoàn thành

* **Tương tác thời gian thực (Real-time):** Bất kỳ lệnh đặt giá (Bid) nào cũng lập tức được Broadcast (phát sóng) đến toàn bộ Client đang online trong phòng với độ trễ tối thiểu.
* **Chống xung đột dữ liệu (Concurrency Control):** Áp dụng từ khóa `synchronized` trên tầng Service của Server để khóa luồng, ngăn chặn lỗi Race Condition khi hàng chục người cùng thao tác.
* **Bảo mật & Tối ưu băng thông:** Các thông tin nhạy cảm không được gửi trực tiếp mà đều thông qua lớp giáp `DTO`, gói gọn trong định dạng JSON nhẹ nhàng.
* **Triển khai Cloud trên máy:** Database và server cùng host trên chung 1 máy, client chạy với nhau thông qua LAN ảo.
* **Trải nghiệm UI/UX tốt:** Cảnh báo popup rõ ràng (DialogHelper), cập nhật giao diện mượt mà không gây đơ ứng dụng nhờ phân tách luồng UI và luồng Network.

---

## 6. Tài liệu Báo cáo và Video Demo

* 📄🎥 **Báo cáo PDF và Video Demo toàn bộ luồng hệ thống:** [https://drive.google.com/drive/folders/12BNc9SLQoVMXROSgmx39HYHk-L7iVNlY?fbclid=IwY2xjawSJP19leHRuA2FlbQIxMABicmlkETFVSHdmZWR6dFZ4TEpXM1ZJc3J0YwZhcHBfaWQQMjIyMDM5MTc4ODIwMDg5MgABHsjIWpSXZ5ZdXin0d3A57S2YX4j3UJln5rVlFq_ZyMtPBW3oLzH1jMHsaczz_aem_bMPxaVml-cJJ8VqqtT-eXQ]
