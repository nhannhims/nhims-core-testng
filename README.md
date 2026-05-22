# NHIMS Core TestNG - Automation Framework

Dự án kiểm thử tự động hóa (Automation Testing) được xây dựng dựa trên sự kết hợp giữa **Java**, **Selenium WebDriver**, **TestNG** và quản lý phụ thuộc bằng **Maven**. Framework áp dụng mô hình thiết kế **Page Object Model (POM)** kết hợp các thư viện tùy biến (Custom Controls & Utilities) nhằm tối ưu hiệu năng và độ ổn định khi chạy test.

---

## 🛠️ Công nghệ sử dụng (Tech Stack)

- **Ngôn ngữ lập trình:** Java (JDK 11+)
- **Thư viện chính:** Selenium WebDriver (4.x), TestNG
- **Quản lý dự án & Build:** Maven
- **Tiện ích đi kèm:** 
  - **Monte Screen Recorder** (ghi video quá trình chạy test)
  - **Custom Controls & Actions** (wrapper an toàn tránh các lỗi phổ biến của Selenium)

---

## 📂 Cấu trúc dự án (Project Structure)

```text
nhims-core-testng/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/nhims/
│   │           ├── browsers/      # Cấu hình khởi tạo & quản lý trình duyệt
│   │           ├── constants/     # Lưu trữ các hằng số, cấu hình chung
│   │           ├── controls/      # Custom element controls (Actions, Keyboards, BaseControl...)
│   │           ├── drivers/       # Quản lý Driver điều khiển (Chrome, Safari, Firefox...)
│   │           └── utils/         # Các lớp tiện ích (Logger, RecordVideo, HDate, HFile...)
│   └── test/
│       └── java/
│           └── com/nhims/
│               ├── data/          # Bộ dữ liệu dùng cho việc kiểm thử
│               ├── listeners/     # TestListener lắng nghe các sự kiện TestNG (chụp màn hình khi fail, ghi video...)
│               ├── pages/         # Các Page Object (ProductDetailPage, SearchPage, GenaralPage...)
│               └── scripts/       # Kịch bản kiểm thử (Test Cases)
├── pom.xml                        # File cấu hình Maven Dependencies & Plugins
└── README.md                      # Tài liệu hướng dẫn sử dụng dự án
```

---

## 🚀 Tính năng nổi bật

1. **Java Convention Compliant:** Toàn bộ phương thức tiện ích, hàm kiểm thử tuân thủ chuẩn đặt tên `camelCase` của Java (ví dụ: `info()`, `warning()`, `error()`, `startRecord()`).
2. **Safe Web Operations:** Các thao tác trên Element qua lớp `Actions` được bọc an toàn tránh lỗi `NullPointerException` (NPE) và hỗ trợ tự động chờ đợi (explicit wait).
3. **Tự động chụp màn hình & Ghi hình:**
   - Chụp ảnh màn hình (screenshot) khi phát hiện test case bị lỗi.
   - Ghi lại video quá trình thực hiện kịch bản test (sử dụng định dạng AVI qua Monte Media).
4. **Log chi tiết:** Tích hợp bộ ghi log chuyên biệt giúp xuất nhật ký chạy kịch bản ra cả Console và file log lưu trữ định dạng Text.

---

## 💻 Hướng dẫn chạy kiểm thử

### Yêu cầu hệ thống
- Đã cài đặt **Java JDK 11** trở lên.
- Đã cài đặt **Maven**.

### Các lệnh chạy chính
- **Kiểm tra và biên dịch dự án:**
  ```bash
  mvn clean test-compile
  ```
- **Chạy toàn bộ các test case:**
  ```bash
  mvn test
  ```
- **Chạy các class test cụ thể qua suite XML (nếu có):**
  ```bash
  mvn test -DsuiteXmlFile=testng.xml
  ```

---

## ⚙️ Cấu hình (Configuration)

Hệ thống hỗ trợ các cấu hình qua file properties nằm trong thư mục tài nguyên (ví dụ: `logger`, `driver`, `video`, `capture`). Bạn có thể điều chỉnh bật/tắt các tính năng ghi hình, chụp màn hình một cách linh hoạt mà không cần thay đổi code.
