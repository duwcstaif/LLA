-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: korean_app_db
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `lessons`
--

DROP TABLE IF EXISTS `lessons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lessons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `level` varchar(50) DEFAULT NULL,
  `topic_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lessons`
--

LOCK TABLES `lessons` WRITE;
/*!40000 ALTER TABLE `lessons` DISABLE KEYS */;
INSERT INTO `lessons` VALUES (1,'Chào hỏi & Làm quen','Các mẫu câu chào hỏi đầu tiên','Sơ cấp 1',1),(2,'Số đếm & Tiền tệ','Cách đếm số và đọc giá tiền','Sơ cấp 1',1),(3,'Thời gian & Thứ ngày','Hỏi giờ và các ngày trong tuần','Sơ cấp 1',1),(4,'Gia đình & Bản thân','Giới thiệu về người thân','Sơ cấp 1',1),(5,'Cảm xúc & Sức khỏe','Nói về tâm trạng và cơ thể','Sơ cấp 1',1),(6,'Tại sân bay','Thủ tục và hỏi đáp tại phi trường','Sơ cấp 2',2),(7,'Phương tiện công cộng','Cách đi xe bus, tàu điện ngầm','Sơ cấp 2',2),(8,'Khách sạn & Lưu trú','Đặt phòng và nhận phòng','Sơ cấp 2',2),(9,'Hỏi đường & Địa điểm','Cách tìm đường và vị trí','Sơ cấp 2',2),(10,'Tham quan & Chụp ảnh','Giao tiếp tại các điểm du lịch','Sơ cấp 2',2),(11,'Phỏng vấn xin việc','Chuẩn bị cho buổi phỏng vấn','Trung cấp 1',3),(12,'Đồng nghiệp & Công ty','Giao tiếp nơi công sở','Trung cấp 1',3),(13,'Họp hành & Báo cáo','Thuật ngữ dùng trong cuộc họp','Trung cấp 1',3),(14,'Thiết bị & Máy tính','Các vật dụng trong văn phòng','Trung cấp 1',3),(15,'Điện thoại & Email','Cách soạn thư và nghe máy','Trung cấp 1',3),(16,'Tại nhà hàng','Cách đặt bàn và xem menu','Sơ cấp 1',4),(17,'Các món ăn Hàn Quốc','Tên gọi các món ăn phổ biến','Sơ cấp 1',4),(18,'Trái cây & Đồ uống','Các loại quả và thức uống','Sơ cấp 1',4),(19,'Hương vị & Nấu ăn','Mô tả vị của thức ăn','Sơ cấp 1',4),(20,'Đi chợ & Siêu thị','Mua thực phẩm hàng ngày','Sơ cấp 1',4);
/*!40000 ALTER TABLE `lessons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics`
--

DROP TABLE IF EXISTS `topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (1,'Giao tiếp cơ bản',NULL),(2,'Du lịch & Đi lại',NULL),(3,'Công việc & Văn phòng',NULL),(4,'Ẩm thực & Ăn uống',NULL);
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `score` int(11) DEFAULT 0,
  `level` int(11) DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','123456',60,1),(2,'thinh','thinh',0,1),(3,'Học Giả Chăm Chỉ','',0,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vocabularies`
--

DROP TABLE IF EXISTS `vocabularies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vocabularies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_id` int(11) DEFAULT NULL,
  `korean_word` varchar(100) NOT NULL,
  `pronunciation` varchar(100) DEFAULT NULL,
  `vietnamese_meaning` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vocabularies`
--

LOCK TABLES `vocabularies` WRITE;
/*!40000 ALTER TABLE `vocabularies` DISABLE KEYS */;
INSERT INTO `vocabularies` VALUES (1,1,'안녕하세요','An-nyong-ha-se-yo','Xin chào'),(2,1,'반갑습니다','Ban-gap-seum-ni-da','Rất vui được gặp'),(3,1,'이름','I-reum','Tên'),(4,1,'선생님','Seon-saeng-nim','Giáo viên'),(5,1,'학생','Hak-saeng','Học sinh'),(6,1,'한국','Han-guk','Hàn Quốc'),(7,1,'베트남','Be-teu-nam','Việt Nam'),(8,1,'사람','Sa-ram','Người'),(9,1,'네','Ne','Vâng/Có'),(10,1,'아니요','A-ni-yo','Không'),(11,6,'여권','Yeo-gwon','Hộ chiếu'),(12,6,'비행기','Pi-haeng-gi','Máy bay'),(13,6,'표','Pyo','Vé'),(14,6,'공항','Gong-hang','Sân bay'),(15,6,'입구','Ip-gu','Lối vào'),(16,6,'출구','Chul-gu','Lối ra'),(17,6,'짐','Jim','Hành lý'),(18,6,'시간','Si-gan','Thời gian'),(19,6,'도착','Do-chak','Đến nơi'),(20,6,'출발','Chul-bal','Xuất phát'),(21,11,'면접','Myeon-jeop','Phỏng vấn'),(22,11,'이력서','I-ryeok-seo','Sơ yếu lý lịch'),(23,11,'회사','Hwe-sa','Công ty'),(24,11,'지원하다','Ji-won-ha-da','Ứng tuyển'),(25,11,'직업','Jik-eop','Nghề nghiệp'),(26,11,'전공','Jeon-gong','Chuyên ngành'),(27,11,'능력','Neung-ryeok','Năng lực'),(28,11,'경험','Gyeong-heom','Kinh nghiệm'),(29,11,'합격','Hap-gyeok','Trúng tuyển'),(30,11,'열심히','Yeol-sim-hi','Chăm chỉ'),(31,16,'식당','Sik-dang','Nhà hàng'),(32,16,'메뉴','Me-nyu','Thực đơn'),(33,16,'물','Mul','Nước'),(34,16,'주문하다','Ju-mun-ha-da','Đặt món'),(35,16,'맛있다','Ma-sit-da','Ngon'),(36,16,'맵다','Maep-da','Cay'),(37,16,'달다','Dal-da','Ngọt'),(38,16,'계산서','Gye-san-seo','Hóa đơn'),(39,16,'숟가락','Sut-ga-rak','Thìa'),(40,16,'젓가락','Jeot-ga-rak','Đũa'),(41,2,'일','Il','Số 1 (Hán Hàn)'),(42,2,'이','I','Số 2 (Hán Hàn)'),(43,2,'삼','Sam','Số 3 (Hán Hàn)'),(44,2,'사','Sa','Số 4 (Hán Hàn)'),(45,2,'오','O','Số 5 (Hán Hàn)'),(46,2,'육','Yuk','Số 6 (Hán Hàn)'),(47,2,'칠','Chil','Số 7 (Hán Hàn)'),(48,2,'팔','Pal','Số 8 (Hán Hàn)'),(49,2,'구','Gu','Số 9 (Hán Hàn)'),(50,2,'십','Sip','Số 10 (Hán Hàn)'),(51,3,'월요일','Wol-yo-il','Thứ hai'),(52,3,'화요일','Hwa-yo-il','Thứ ba'),(53,3,'수요일','Su-yo-il','Thứ tư'),(54,3,'목요일','Mok-yo-il','Thứ năm'),(55,3,'금요일','Keum-yo-il','Thứ sáu'),(56,3,'토요일','To-yo-il','Thứ bảy'),(57,3,'일요일','Il-yo-il','Chủ nhật'),(58,3,'오늘','O-neul','Hôm nay'),(59,3,'내일','Nae-il','Ngày mai'),(60,3,'어제','Eo-je','Hôm qua'),(61,4,'아버지','A-beo-ji','Bố'),(62,4,'어머니','O-meo-ni','Mẹ'),(63,4,'형','Hyeong','Anh (em trai gọi)'),(64,4,'오빠','O-ppa','Anh (em gái gọi)'),(65,4,'누나','Nu-na','Chị (em trai gọi)'),(66,4,'언니','Eon-ni','Chị (em gái gọi)'),(67,4,'동생','Dong-saeng','Em'),(68,4,'할아버지','Hal-a-beo-ji','Ông'),(69,4,'할머니','Hal-meo-ni','Bà'),(70,4,'나','Na','Tôi (thân mật)'),(71,5,'기쁘다','Gi-ppeu-da','Vui'),(72,5,'슬프다','Seul-peu-da','Buồn'),(73,5,'화나다','Hwa-na-da','Tức giận'),(74,5,'행복하다','Haeng-bok-ha-da','Hạnh phúc'),(75,5,'아프다','A-peu-da','Đau/Ốm'),(76,5,'피곤하다','Pi-gon-ha-da','Mệt mỏi'),(77,5,'좋다','Jo-ta','Tốt/Thích'),(78,5,'싫다','Sil-ta','Ghét/Không thích'),(79,5,'괜찮다','Gwaen-chan-ta','Ổn/Không sao'),(80,5,'힘들다','Him-deul-da','Vất vả/Mệt'),(81,7,'버스','Beo-seu','Xe buýt'),(82,7,'지하철','Ji-ha-cheol','Tàu điện ngầm'),(83,7,'기차','Gi-cha','Tàu hỏa'),(84,7,'택시','Taek-si','Taxi'),(85,7,'정류장','Jeong-ryu-jang','Trạm dừng'),(86,7,'역','Yeok','Ga tàu'),(87,7,'표','Pyo','Vé'),(88,7,'타다','Ta-da','Lên (xe, tàu)'),(89,7,'내리다','Nae-ri-da','Xuống (xe, tàu)'),(90,7,'환승하다','Hwan-seung-ha-da','Đổi chuyến'),(91,8,'호텔','Ho-tel','Khách sạn'),(92,8,'방','Bang','Phòng'),(93,8,'예약하다','Ye-yak-ha-da','Đặt trước'),(94,8,'체크인','Che-keu-in','Nhận phòng'),(95,8,'체크아웃','Che-keu-a-ut','Trả phòng'),(96,8,'열쇠','Yeol-soe','Chìa khóa'),(97,8,'에어컨','E-eo-keon','Máy lạnh'),(98,8,'수건','Su-geon','Khăn tắm'),(99,8,'조식','Jo-sik','Bữa sáng'),(100,8,'프런트','Peu-reon-teu','Quầy lễ tân'),(101,9,'길','Gil','Con đường'),(102,9,'지도','Ji-do','Bản đồ'),(103,9,'어디','Eo-di','Ở đâu'),(104,9,'오른쪽','O-reun-jjok','Bên phải'),(105,9,'왼쪽','Oen-jjok','Bên trái'),(106,9,'앞','Ap','Phía trước'),(107,9,'뒤','Dwi','Phía sau'),(108,9,'건너편','Geon-neo-pyeon','Phía đối diện'),(109,9,'가깝다','Ga-kkap-da','Gần'),(110,9,'멀다','Meol-da','Xa'),(111,10,'여행','Yeo-haeng','Du lịch'),(112,10,'사진','Sa-jin','Bức ảnh'),(113,10,'찍다','Jjik-da','Chụp'),(114,10,'카메라','Ka-me-ra','Máy ảnh'),(115,10,'바다','Ba-da','Biển'),(116,10,'산','San','Núi'),(117,10,'박물관','Bang-mul-gwan','Bảo tàng'),(118,10,'기념품','Gi-nyeom-pum','Quà lưu niệm'),(119,10,'예쁘다','Ye-ppeu-da','Đẹp'),(120,10,'유명하다','Yu-myeong-ha-da','Nổi tiếng'),(121,12,'사장님','Sa-jang-nim','Giám đốc'),(122,12,'부장님','Bu-jang-nim','Trưởng phòng'),(123,12,'동료','Dong-ryo','Đồng nghiệp'),(124,12,'출근하다','Chul-geun-ha-da','Đi làm'),(125,12,'퇴근하다','Toe-geun-ha-da','Tan làm'),(126,12,'야근하다','Ya-geun-ha-da','Làm thêm giờ'),(127,12,'월급','Wol-geup','Lương'),(128,12,'명함','Myeong-ham','Danh thiếp'),(129,12,'사무실','Sa-mu-sil','Văn phòng'),(130,12,'회식','Hoe-sik','Tiệc công ty (Ăn uống cùng công ty)'),(131,13,'회의','Hoe-ui','Cuộc họp'),(132,13,'회의실','Hoe-ui-sil','Phòng họp'),(133,13,'보고서','Bo-go-seo','Bản báo cáo'),(134,13,'발표','Bal-pyo','Thuyết trình'),(135,13,'계획','Gye-hoek','Kế hoạch'),(136,13,'결과','Gyeol-gwa','Kết quả'),(137,13,'의견','Ui-gyeon','Ý kiến'),(138,13,'동의하다','Dong-ui-ha-da','Đồng ý'),(139,13,'반대하다','Ban-dae-ha-da','Phản đối'),(140,13,'자료','Ja-ryo','Tài liệu'),(141,14,'복사기','Bok-sa-gi','Máy photocopy'),(142,14,'프린터','Peu-rin-teo','Máy in'),(143,14,'종이','Jong-i','Giấy'),(144,14,'결재','Gyeol-jae','Phê duyệt / Ký duyệt'),(145,14,'서류','Seo-ryu','Hồ sơ / Giấy tờ'),(146,14,'책상','Chaek-sang','Bàn làm việc'),(147,14,'의자','Ui-ja','Ghế'),(148,14,'비밀번호','Bi-mil-beon-ho','Mật khẩu'),(149,14,'저장하다','Jeo-jang-ha-da','Lưu (Save)'),(150,14,'삭제하다','Sak-je-ha-da','Xóa (Delete)'),(151,15,'전화','Jeon-hwa','Điện thoại'),(152,15,'이메일','I-me-il','Email'),(153,15,'보내다','Bo-nae-da','Gửi'),(154,15,'받다','Bat-da','Nhận'),(155,15,'연락하다','Yeon-rak-ha-da','Liên lạc'),(156,15,'메시지','Me-si-ji','Tin nhắn'),(157,15,'수신자','Su-sin-ja','Người nhận'),(158,15,'발신자','Bal-sin-ja','Người gửi'),(159,15,'첨부파일','Cheom-bu-pa-il','File đính kèm'),(160,15,'제목','Je-mok','Tiêu đề'),(161,17,'김치','Kim-chi','Kim chi'),(162,17,'김밥','Gim-bap','Cơm cuộn rong biển'),(163,17,'떡볶이','Tteok-bok-i','Bánh gạo cay'),(164,17,'비빔밥','Bi-bim-bap','Cơm trộn'),(165,17,'불고기','Bul-go-gi','Thịt bò nướng/xào'),(166,17,'삼겹살','Sam-gyeop-sal','Thịt ba chỉ nướng'),(167,17,'냉면','Naeng-myeon','Mỳ lạnh'),(168,17,'라면','Ra-myeon','Mỳ tôm / Mỳ gói'),(169,17,'국밥','Guk-bap','Cơm canh'),(170,17,'만두','Man-du','Bánh bao / Há cảo'),(171,18,'사과','Sa-gwa','Quả táo'),(172,18,'바나나','Ba-na-na','Quả chuối'),(173,18,'수박','Su-bak','Dưa hấu'),(174,18,'딸기','Ttal-gi','Dâu tây'),(175,18,'포도','Po-do','Quả nho'),(176,18,'우유','U-yu','Sữa'),(177,18,'커피','Keo-pi','Cà phê'),(178,18,'차','Cha','Trà'),(179,18,'주스','Ju-seu','Nước ép'),(180,18,'맥주','Maek-ju','Bia'),(181,19,'짜다','Jja-da','Mặn'),(182,19,'시다','Si-da','Chua'),(183,19,'쓰다','Sseu-da','Đắng'),(184,19,'뜨겁다','Tteu-geop-da','Nóng (nhiệt độ)'),(185,19,'차갑다','Cha-gap-da','Lạnh (nhiệt độ)'),(186,19,'끓이다','Kkeul-i-da','Đun sôi / Nấu'),(187,19,'굽다','Gup-da','Nướng'),(188,19,'섞다','Seok-da','Trộn'),(189,19,'자르다','Ja-reu-da','Cắt / Thái'),(190,19,'붓다','But-da','Rót / Đổ vào'),(191,20,'시장','Si-jang','Chợ'),(192,20,'마트','Ma-teu','Siêu thị'),(193,20,'가격','Ga-gyeok','Giá cả'),(194,20,'싸다','Ssa-da','Rẻ'),(195,20,'비싸다','Bi-ssa-da','Đắt'),(196,20,'사다','Sa-da','Mua'),(197,20,'팔다','Pal-da','Bán'),(198,20,'봉투','Bong-tu','Túi nilon / Bao bì'),(199,20,'거스름돈','Geo-seu-reum-don','Tiền thối lại (Tiền thừa)'),(200,20,'할인','Hal-in','Giảm giá');
/*!40000 ALTER TABLE `vocabularies` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-26 22:04:42
