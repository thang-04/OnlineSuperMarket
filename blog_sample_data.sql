-- Script để thêm dữ liệu mẫu cho bảng Blogs
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
    
    <h3>4. Hỗ trợ tiêu hóa</h3>
    <p>Chất xơ trong rau xanh giúp thúc đẩy quá trình tiêu hóa, ngăn ngừa táo bón và các vấn đề đường ruột.</p>
    
    <h3>5. Giảm cân hiệu quả</h3>
    <p>Rau xanh ít calo nhưng giàu chất xơ, giúp bạn cảm thấy no lâu hơn và hỗ trợ quá trình giảm cân.</p>
    
    <h3>6. Tốt cho mắt</h3>
    <p>Vitamin A và lutein trong rau xanh giúp bảo vệ mắt, ngăn ngừa các bệnh về mắt như thoái hóa điểm vàng.</p>
    
    <h3>7. Tăng cường năng lượng</h3>
    <p>Chất sắt và vitamin B trong rau xanh giúp tăng cường năng lượng và giảm mệt mỏi.</p>
    
    <h3>8. Chống viêm</h3>
    <p>Các hợp chất chống viêm trong rau xanh giúp giảm viêm trong cơ thể và ngăn ngừa các bệnh mãn tính.</p>
    
    <h3>9. Tốt cho da</h3>
    <p>Vitamin C và các chất chống oxy hóa giúp làm đẹp da, ngăn ngừa lão hóa và mụn trứng cá.</p>
    
    <h3>10. Tăng cường miễn dịch</h3>
    <p>Rau xanh chứa nhiều vitamin và khoáng chất giúp tăng cường hệ miễn dịch, bảo vệ cơ thể khỏi bệnh tật.</p>
    
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
    
    <h4>3. Cam</h4>
    <ul>
        <li>Chọn cam có vỏ mỏng, căng bóng</li>
        <li>Quả cam phải nặng tay</li>
        <li>Mùi thơm tự nhiên, không có mùi lạ</li>
    </ul>
    
    <h3>Cách bảo quản trái cây</h3>
    
    <h4>1. Bảo quản trong tủ lạnh</h4>
    <p>Hầu hết trái cây nên được bảo quản trong tủ lạnh ở nhiệt độ 2-4°C để giữ được độ tươi ngon lâu hơn.</p>
    
    <h4>2. Phân loại trái cây</h4>
    <p>Không nên để chung các loại trái cây với nhau vì một số loại có thể tiết ra khí ethylene làm chín nhanh các loại khác.</p>
    
    <h4>3. Sử dụng túi giấy</h4>
    <p>Bọc trái cây trong túi giấy để giữ độ ẩm và ngăn chặn sự bay hơi nước.</p>
    
    <h4>4. Kiểm tra định kỳ</h4>
    <p>Thường xuyên kiểm tra trái cây để loại bỏ những quả bị hỏng, tránh lây lan sang các quả khác.</p>
    
    <h3>Lưu ý quan trọng</h3>
    <ul>
        <li>Rửa sạch trái cây trước khi ăn</li>
        <li>Không bảo quản trái cây đã cắt trong thời gian dài</li>
        <li>Sử dụng trái cây trong vòng 3-5 ngày sau khi mua</li>
        <li>Bảo quản ở nơi khô ráo, thoáng mát</li>
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
    
    <h4>Thứ 2: Bữa trưa</h4>
    <ul>
        <li>Cơm gạo lứt</li>
        <li>Đậu hũ xào rau cải</li>
        <li>Canh bí đỏ</li>
        <li>Salad rau xanh</li>
    </ul>
    
    <h4>Thứ 2: Bữa tối</h4>
    <ul>
        <li>Mì xào chay</li>
        <li>Nấm xào</li>
        <li>Súp rau củ</li>
    </ul>
    
    <h3>Nguyên liệu cần thiết</h3>
    <ul>
        <li>Rau xanh các loại</li>
        <li>Đậu hũ, đậu nành</li>
        <li>Nấm các loại</li>
        <li>Hạt dinh dưỡng</li>
        <li>Gạo lứt, yến mạch</li>
        <li>Trái cây tươi</li>
    </ul>
    
    <h3>Lưu ý khi ăn chay</h3>
    <ul>
        <li>Đảm bảo đủ protein từ đậu và hạt</li>
        <li>Bổ sung vitamin B12</li>
        <li>Ăn đa dạng các loại thực phẩm</li>
        <li>Uống đủ nước</li>
        <li>Tham khảo ý kiến bác sĩ nếu cần</li>
    </ul>
    
    <h3>Kết luận</h3>
    <p>Ăn chay lành mạnh mang lại nhiều lợi ích cho sức khỏe. Hãy bắt đầu với những bữa ăn đơn giản và dần dần xây dựng thói quen ăn chay cho gia đình.</p>',
    '/homePage/images/blog/img-3.jpg',
    N'Lê Thị Mai',
    1,
    DATEADD(day, -5, GETDATE())
);

INSERT INTO Blogs (title, content, featuredImage, authorName, is_published, createdAt) VALUES
(
    N'Cách làm nước ép trái cây tại nhà',
    N'<h2>Hướng dẫn làm nước ép trái cây tươi</h2>
    <p>Nước ép trái cây tươi không chỉ ngon miệng mà còn rất tốt cho sức khỏe. Dưới đây là cách làm các loại nước ép phổ biến tại nhà.</p>
    
    <h3>Dụng cụ cần thiết</h3>
    <ul>
        <li>Máy ép trái cây</li>
        <li>Máy xay sinh tố</li>
        <li>Rây lọc</li>
        <li>Dao, thớt</li>
        <li>Ly, cốc</li>
    </ul>
    
    <h3>Các loại nước ép phổ biến</h3>
    
    <h4>1. Nước ép cam</h4>
    <p><strong>Nguyên liệu:</strong></p>
    <ul>
        <li>4-5 quả cam tươi</li>
        <li>1 thìa mật ong (tùy chọn)</li>
        <li>Đá viên</li>
    </ul>
    <p><strong>Cách làm:</strong></p>
    <ol>
        <li>Rửa sạch cam, cắt đôi</li>
        <li>Ép lấy nước bằng máy ép</li>
        <li>Thêm mật ong và đá viên</li>
        <li>Khuấy đều và thưởng thức</li>
    </ol>
    
    <h4>2. Nước ép táo</h4>
    <p><strong>Nguyên liệu:</strong></p>
    <ul>
        <li>3-4 quả táo</li>
        <li>1 quả chanh</li>
        <li>Đá viên</li>
    </ul>
    <p><strong>Cách làm:</strong></p>
    <ol>
        <li>Rửa sạch táo, cắt miếng nhỏ</li>
        <li>Ép lấy nước táo</li>
        <li>Vắt chanh vào</li>
        <li>Thêm đá và thưởng thức</li>
    </ol>
    
    <h4>3. Nước ép cà rốt</h4>
    <p><strong>Nguyên liệu:</strong></p>
    <ul>
        <li>4-5 củ cà rốt</li>
        <li>1 quả táo</li>
        <li>1 nhánh gừng nhỏ</li>
    </ul>
    <p><strong>Cách làm:</strong></p>
    <ol>
        <li>Rửa sạch cà rốt, táo</li>
        <li>Cắt miếng nhỏ</li>
        <li>Ép lấy nước</li>
        <li>Thêm gừng và thưởng thức</li>
    </ol>
    
    <h3>Lưu ý quan trọng</h3>
    <ul>
        <li>Chọn trái cây tươi, không bị hỏng</li>
        <li>Rửa sạch trước khi ép</li>
        <li>Uống ngay sau khi ép để giữ dinh dưỡng</li>
        <li>Không thêm quá nhiều đường</li>
        <li>Bảo quản trong tủ lạnh nếu không uống ngay</li>
    </ul>
    
    <h3>Lợi ích của nước ép trái cây</h3>
    <ul>
        <li>Cung cấp vitamin và khoáng chất</li>
        <li>Tăng cường hệ miễn dịch</li>
        <li>Giải độc cơ thể</li>
        <li>Làm đẹp da</li>
        <li>Hỗ trợ tiêu hóa</li>
    </ul>
    
    <h3>Kết luận</h3>
    <p>Làm nước ép trái cây tại nhà vừa tiết kiệm vừa đảm bảo vệ sinh. Hãy thử các công thức trên để có những ly nước ép thơm ngon và bổ dưỡng!</p>',
    '/homePage/images/blog/img-4.jpg',
    N'Phạm Văn Hùng',
    1,
    DATEADD(day, -7, GETDATE())
);

INSERT INTO Blogs (title, content, featuredImage, authorName, is_published, createdAt) VALUES
(
    N'Cách bảo quản thực phẩm trong tủ lạnh đúng cách',
    N'<h2>Hướng dẫn bảo quản thực phẩm trong tủ lạnh</h2>
    <p>Bảo quản thực phẩm đúng cách trong tủ lạnh không chỉ giữ được độ tươi ngon mà còn tiết kiệm chi phí và tránh lãng phí.</p>
    
    <h3>Nguyên tắc bảo quản cơ bản</h3>
    
    <h4>1. Nhiệt độ phù hợp</h4>
    <ul>
        <li>Ngăn đông: -18°C đến -20°C</li>
        <li>Ngăn mát: 2°C đến 4°C</li>
        <li>Ngăn rau củ: 4°C đến 8°C</li>
    </ul>
    
    <h4>2. Phân loại thực phẩm</h4>
    <ul>
        <li>Thịt, cá: Ngăn đông hoặc ngăn mát dưới cùng</li>
        <li>Rau củ: Ngăn rau củ riêng</li>
        <li>Trái cây: Ngăn mát trên cùng</li>
        <li>Sữa, trứng: Ngăn mát giữa</li>
    </ul>
    
    <h3>Bảo quản từng loại thực phẩm</h3>
    
    <h4>1. Thịt, cá</h4>
    <ul>
        <li>Rửa sạch, để ráo nước</li>
        <li>Chia nhỏ theo khẩu phần</li>
        <li>Bọc kín bằng màng bọc thực phẩm</li>
        <li>Đặt ở ngăn đông hoặc ngăn mát dưới cùng</li>
        <li>Thời gian bảo quản: 1-3 ngày (ngăn mát), 3-6 tháng (ngăn đông)</li>
    </ul>
    
    <h4>2. Rau củ</h4>
    <ul>
        <li>Loại bỏ lá úa, rễ thối</li>
        <li>Không rửa trước khi bảo quản</li>
        <li>Bọc trong giấy ẩm hoặc túi giấy</li>
        <li>Đặt ở ngăn rau củ</li>
        <li>Thời gian bảo quản: 3-7 ngày</li>
    </ul>
    
    <h4>3. Trái cây</h4>
    <ul>
        <li>Rửa sạch, để ráo</li>
        <li>Bọc trong túi giấy</li>
        <li>Đặt ở ngăn mát trên cùng</li>
        <li>Không để chung với rau củ</li>
        <li>Thời gian bảo quản: 3-5 ngày</li>
    </ul>
    
    <h4>4. Sữa và sản phẩm từ sữa</h4>
    <ul>
        <li>Đặt ở ngăn mát giữa</li>
        <li>Không để ở cửa tủ lạnh</li>
        <li>Kiểm tra hạn sử dụng thường xuyên</li>
        <li>Thời gian bảo quản: Theo hạn sử dụng</li>
    </ul>
    
    <h3>Lưu ý quan trọng</h3>
    <ul>
        <li>Không để thực phẩm nóng vào tủ lạnh</li>
        <li>Đóng kín cửa tủ lạnh</li>
        <li>Vệ sinh tủ lạnh định kỳ</li>
        <li>Kiểm tra thực phẩm thường xuyên</li>
        <li>Loại bỏ thực phẩm hỏng ngay lập tức</li>
    </ul>
    
    <h3>Dấu hiệu thực phẩm hỏng</h3>
    <ul>
        <li>Mùi lạ, khó chịu</li>
        <li>Màu sắc thay đổi</li>
        <li>Kết cấu nhầy, dính</li>
        <li>Nấm mốc</li>
        <li>Hạn sử dụng đã quá</li>
    </ul>
    
    <h3>Kết luận</h3>
    <p>Bảo quản thực phẩm đúng cách trong tủ lạnh giúp bạn tiết kiệm chi phí, đảm bảo vệ sinh và có những bữa ăn ngon miệng cho gia đình.</p>',
    '/homePage/images/blog/img-5.jpg',
    N'Hoàng Thị Lan',
    1,
    DATEADD(day, -10, GETDATE())
);

INSERT INTO Blogs (title, content, featuredImage, authorName, is_published, createdAt) VALUES
(
    N'Cách nấu cơm gạo lứt ngon và dinh dưỡng',
    N'<h2>Hướng dẫn nấu cơm gạo lứt</h2>
    <p>Gạo lứt là loại gạo giàu dinh dưỡng, chứa nhiều vitamin, khoáng chất và chất xơ. Dưới đây là cách nấu cơm gạo lứt ngon và đúng cách.</p>
    
    <h3>Lợi ích của gạo lứt</h3>
    <ul>
        <li>Giàu chất xơ, tốt cho tiêu hóa</li>
        <li>Chứa nhiều vitamin B, E</li>
        <li>Giàu khoáng chất: sắt, magie, kẽm</li>
        <li>Giúp kiểm soát đường huyết</li>
        <li>Hỗ trợ giảm cân</li>
        <li>Tốt cho tim mạch</li>
    </ul>
    
    <h3>Chuẩn bị nguyên liệu</h3>
    <ul>
        <li>Gạo lứt: 1 cup</li>
        <li>Nước: 2-2.5 cup</li>
        <li>Muối: 1/4 thìa cà phê</li>
        <li>Dầu oliu: 1 thìa cà phê (tùy chọn)</li>
    </ul>
    
    <h3>Cách nấu cơm gạo lứt</h3>
    
    <h4>Bước 1: Vo gạo</h4>
    <ol>
        <li>Đổ gạo vào rổ</li>
        <li>Rửa sạch dưới vòi nước</li>
        <li>Vo nhẹ nhàng để loại bỏ bụi bẩn</li>
        <li>Để ráo nước</li>
    </ol>
    
    <h4>Bước 2: Ngâm gạo</h4>
    <ol>
        <li>Ngâm gạo trong nước ấm 30-60 phút</li>
        <li>Điều này giúp gạo mềm và nấu nhanh hơn</li>
        <li>Vớt gạo ra, để ráo</li>
    </ol>
    
    <h4>Bước 3: Nấu cơm</h4>
    <ol>
        <li>Đổ gạo vào nồi</li>
        <li>Thêm nước theo tỷ lệ 1:2 hoặc 1:2.5</li>
        <li>Thêm muối và dầu oliu</li>
        <li>Đun sôi với lửa to</li>
        <li>Khi sôi, giảm lửa nhỏ</li>
        <li>Đậy nắp, nấu 40-50 phút</li>
        <li>Tắt bếp, để 10 phút</li>
    </ol>
    
    <h3>Mẹo nấu cơm gạo lứt ngon</h3>
    <ul>
        <li>Ngâm gạo trước khi nấu</li>
        <li>Sử dụng nồi có đáy dày</li>
        <li>Không mở nắp khi đang nấu</li>
        <li>Để cơm nghỉ sau khi nấu</li>
        <li>Có thể thêm gia vị như gừng, tỏi</li>
    </ul>
    
    <h3>Các món ăn kèm với cơm gạo lứt</h3>
    <ul>
        <li>Rau xào</li>
        <li>Thịt nạc</li>
        <li>Cá hấp</li>
        <li>Đậu hũ</li>
        <li>Nấm xào</li>
        <li>Canh rau</li>
    </ul>
    
    <h3>Bảo quản cơm gạo lứt</h3>
    <ul>
        <li>Để nguội hoàn toàn</li>
        <li>Bọc kín bằng màng bọc</li>
        <li>Bảo quản trong tủ lạnh</li>
        <li>Sử dụng trong vòng 3-5 ngày</li>
        <li>Hâm nóng trước khi ăn</li>
    </ul>
    
    <h3>Kết luận</h3>
    <p>Nấu cơm gạo lứt đúng cách sẽ cho bạn những bát cơm thơm ngon, bổ dưỡng. Hãy thử công thức này để có những bữa ăn lành mạnh cho gia đình!</p>',
    '/homePage/images/blog/img-6.jpg',
    N'Vũ Thị Hoa',
    1,
    DATEADD(day, -12, GETDATE())
);

-- Hiển thị dữ liệu đã thêm
SELECT * FROM Blogs ORDER BY createdAt DESC; 