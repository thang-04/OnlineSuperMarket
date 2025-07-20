-- Script đơn giản để thêm dữ liệu mẫu cho bảng Blogs
-- Online Supermarket Blog Sample Data

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM Blogs;

-- Reset identity column
DBCC CHECKIDENT ('Blogs', RESEED, 0);

-- Thêm dữ liệu mẫu cho bảng Blogs
INSERT INTO Blogs (title, content, featuredImage, authorName, is_published, createdAt) VALUES
(
    N'10 Lợi ích sức khỏe của việc ăn rau xanh mỗi ngày',
    N'<h2>Lợi ích của rau xanh đối với sức khỏe</h2>
    <p>Rau xanh là nguồn dinh dưỡng quan trọng không thể thiếu trong bữa ăn hàng ngày. Dưới đây là 10 lợi ích sức khỏe mà rau xanh mang lại:</p>
    
    <h3>1. Giàu vitamin và khoáng chất</h3>
    <p>Rau xanh chứa nhiều vitamin A, C, K và các khoáng chất như sắt, canxi, magie giúp tăng cường hệ miễn dịch và sức khỏe xương.</p>
    
    <h3>2. Chống oxy hóa</h3>
    <p>Các chất chống oxy hóa trong rau xanh giúp bảo vệ tế bào khỏi tác hại của gốc tự do, ngăn ngừa lão hóa sớm.</p>
    
    <h3>3. Tốt cho tim mạch</h3>
    <p>Chất xơ và các hợp chất thực vật trong rau xanh giúp giảm cholesterol, huyết áp và nguy cơ mắc bệnh tim.</p>
    
    <h3>Kết luận</h3>
    <p>Việc ăn rau xanh mỗi ngày là thói quen tốt cho sức khỏe. Hãy bổ sung rau xanh vào bữa ăn hàng ngày để có một cơ thể khỏe mạnh!</p>',
    '/homePage/images/blog/img-1.jpg',
    N'Nguyễn Thị Hương',
    1,
    GETDATE()
);

INSERT INTO Blogs (title, content, featuredImage, authorName, is_published, createdAt) VALUES
(
    N'Cách chọn và bảo quản trái cây tươi ngon',
    N'<h2>Hướng dẫn chọn và bảo quản trái cây</h2>
    <p>Trái cây tươi là nguồn dinh dưỡng quan trọng, nhưng để có được những quả ngon và bảo quản được lâu, bạn cần biết một số mẹo nhỏ.</p>
    
    <h3>Cách chọn trái cây tươi ngon</h3>
    
    <h4>1. Chuối</h4>
    <ul>
        <li>Chọn chuối có màu vàng đều, không có đốm đen</li>
        <li>Cuống chuối phải còn xanh và cứng</li>
        <li>Tránh chọn chuối quá chín hoặc có mùi lạ</li>
    </ul>
    
    <h4>2. Táo</h4>
    <ul>
        <li>Chọn táo có vỏ bóng, không bị dập</li>
        <li>Quả táo phải cứng khi ấn nhẹ</li>
        <li>Màu sắc đều, không có đốm nâu</li>
    </ul>
    
    <h3>Kết luận</h3>
    <p>Việc chọn và bảo quản trái cây đúng cách sẽ giúp bạn có được những quả tươi ngon và đảm bảo dinh dưỡng cho gia đình.</p>',
    '/homePage/images/blog/img-2.jpg',
    N'Trần Văn Minh',
    1,
    DATEADD(day, -2, GETDATE())
);

INSERT INTO Blogs (title, content, featuredImage, authorName, is_published, createdAt) VALUES
(
    N'Thực đơn ăn chay lành mạnh cho gia đình',
    N'<h2>Thực đơn ăn chay dinh dưỡng</h2>
    <p>Ăn chay không chỉ tốt cho sức khỏe mà còn góp phần bảo vệ môi trường. Dưới đây là thực đơn ăn chay lành mạnh cho cả gia đình.</p>
    
    <h3>Lợi ích của việc ăn chay</h3>
    <ul>
        <li>Giảm nguy cơ mắc bệnh tim mạch</li>
        <li>Hỗ trợ giảm cân và duy trì cân nặng</li>
        <li>Tăng cường hệ miễn dịch</li>
        <li>Giảm nguy cơ mắc bệnh tiểu đường</li>
        <li>Bảo vệ môi trường</li>
    </ul>
    
    <h3>Thực đơn ăn chay trong tuần</h3>
    
    <h4>Thứ 2: Bữa sáng</h4>
    <ul>
        <li>Cháo yến mạch với hạt chia và trái cây</li>
        <li>Sinh tố rau xanh</li>
        <li>Trà xanh</li>
    </ul>
    
    <h3>Kết luận</h3>
    <p>Ăn chay lành mạnh mang lại nhiều lợi ích cho sức khỏe. Hãy bắt đầu với những bữa ăn đơn giản và dần dần xây dựng thói quen ăn chay cho gia đình.</p>',
    '/homePage/images/blog/img-3.jpg',
    N'Lê Thị Mai',
    1,
    DATEADD(day, -5, GETDATE())
);

-- Hiển thị dữ liệu đã thêm
SELECT * FROM Blogs ORDER BY createdAt DESC; 