# Hướng dẫn thêm dữ liệu mẫu cho Blog

## Mô tả
Các file SQL này được tạo để thêm dữ liệu mẫu cho bảng `Blogs` trong cơ sở dữ liệu Online Supermarket.

## Cấu trúc bảng Blogs
```sql
CREATE TABLE Blogs (
    blogId INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    featuredImage NVARCHAR(512),
    authorName NVARCHAR(255),
    is_published BIT NOT NULL DEFAULT 0,
    createdAt DATETIME
);
```

## Các file SQL có sẵn

### 1. `blog_sample_data.sql` (Đầy đủ)
- Chứa 6 bài blog mẫu với nội dung chi tiết
- Bao gồm các chủ đề về dinh dưỡng, sức khỏe, nấu ăn
- Nội dung HTML được format đẹp

### 2. `blog_data_simple.sql` (Đơn giản)
- Chứa 3 bài blog mẫu với nội dung ngắn gọn
- Phù hợp để test nhanh

## Cách sử dụng

### Bước 1: Kết nối SQL Server
1. Mở SQL Server Management Studio (SSMS)
2. Kết nối đến database `OnlineSuperMarket`

### Bước 2: Chạy script
1. Mở file SQL muốn sử dụng
2. Chọn database `OnlineSuperMarket`
3. Nhấn F5 hoặc click "Execute" để chạy script

### Bước 3: Kiểm tra kết quả
```sql
SELECT * FROM Blogs ORDER BY createdAt DESC;
```

## Nội dung các bài blog mẫu

### 1. 10 Lợi ích sức khỏe của việc ăn rau xanh mỗi ngày
- Tác giả: Nguyễn Thị Hương
- Nội dung: Lợi ích dinh dưỡng của rau xanh

### 2. Cách chọn và bảo quản trái cây tươi ngon
- Tác giả: Trần Văn Minh
- Nội dung: Hướng dẫn chọn và bảo quản trái cây

### 3. Thực đơn ăn chay lành mạnh cho gia đình
- Tác giả: Lê Thị Mai
- Nội dung: Thực đơn và lợi ích của việc ăn chay

### 4. Cách làm nước ép trái cây tại nhà
- Tác giả: Phạm Văn Hùng
- Nội dung: Hướng dẫn làm nước ép

### 5. Cách bảo quản thực phẩm trong tủ lạnh đúng cách
- Tác giả: Hoàng Thị Lan
- Nội dung: Hướng dẫn bảo quản thực phẩm

### 6. Cách nấu cơm gạo lứt ngon và dinh dưỡng
- Tác giả: Vũ Thị Hoa
- Nội dung: Hướng dẫn nấu cơm gạo lứt

## Lưu ý quan trọng

1. **Backup dữ liệu**: Nên backup database trước khi chạy script
2. **Xóa dữ liệu cũ**: Script sẽ xóa tất cả dữ liệu cũ trong bảng Blogs
3. **Reset ID**: Script sẽ reset identity column về 0
4. **Hình ảnh**: Các đường dẫn hình ảnh sử dụng format `/homePage/images/blog/img-X.jpg`

## Troubleshooting

### Lỗi thường gặp
1. **Lỗi kết nối database**: Kiểm tra connection string
2. **Lỗi encoding**: Đảm bảo database hỗ trợ Unicode (NVARCHAR)
3. **Lỗi identity**: Kiểm tra quyền reset identity

### Kiểm tra dữ liệu
```sql
-- Kiểm tra số lượng blog
SELECT COUNT(*) FROM Blogs;

-- Kiểm tra blog đã publish
SELECT * FROM Blogs WHERE is_published = 1;

-- Kiểm tra blog theo tác giả
SELECT * FROM Blogs WHERE authorName LIKE '%Hương%';
```

## Liên hệ
Nếu có vấn đề gì, vui lòng liên hệ team phát triển. 