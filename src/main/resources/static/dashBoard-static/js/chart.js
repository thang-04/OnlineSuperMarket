// Đợi cho toàn bộ trang web được tải xong trước khi thực thi mã
document.addEventListener('DOMContentLoaded', function () {

	// Kiểm tra xem phần tử chứa biểu đồ có tồn tại trên trang không
	if (document.getElementById('incomeChartContainer')) {

		// Sử dụng dữ liệu đã được truyền từ Controller qua index.html
		// Các biến monthlyIncomeData và monthsData đã được định nghĩa trong file index.html

		Highcharts.chart('incomeChartContainer', {
			chart: {
				type: 'area', // Bạn có thể đổi thành 'line', 'column', 'bar'
				zoomType: 'x',
				backgroundColor: '#FFFFFF' // Màu nền của biểu đồ
			},
			title: {
				text: 'Tổng doanh thu theo tháng năm 2025',
				align: 'left'
			},
			subtitle: {
				text: 'Nhấp và kéo để phóng to',
				align: 'left'
			},
			xAxis: {
				// Sử dụng mảng tên các tháng làm nhãn cho trục X
				categories: monthsData,
				crosshair: true
			},
			yAxis: {
				title: {
					text: 'Doanh thu (VND)'
				}
			},
			tooltip: {
				// Cấu hình thông tin hiển thị khi di chuột vào một điểm trên biểu đồ
				headerFormat: '<span style="font-size:12px">{point.key}</span><table>',
				pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
					'<td style="padding:0"><b>{point.y:,.0f} VND</b></td></tr>',
				footerFormat: '</table>',
				shared: true,
				useHTML: true
			},
			plotOptions: {
				area: {
					marker: {
						radius: 2
					},
					lineWidth: 1,
					states: {
						hover: {
							lineWidth: 1
						}
					},
					threshold: null
				}
			},
			series: [{
				name: 'Doanh thu',
				// Sử dụng mảng dữ liệu doanh thu
				data: monthlyIncomeData
			}],
			credits: {
				enabled: false // Tắt dòng chữ "Highcharts.com"
			}
		});
	}
});