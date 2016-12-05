CREATE DATABASE  IF NOT EXISTS `microbbs` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `microbbs`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 192.168.154.3    Database: microbbs
-- ------------------------------------------------------
-- Server version	5.7.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `m_category`
--

DROP TABLE IF EXISTS `m_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_code` varchar(255) NOT NULL,
  `c_title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ill36rr1ycxvumougstwiqlab` (`c_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_category`
--

LOCK TABLES `m_category` WRITE;
/*!40000 ALTER TABLE `m_category` DISABLE KEYS */;
INSERT INTO `m_category` VALUES (1,'zatan','杂谈');
/*!40000 ALTER TABLE `m_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_message`
--

DROP TABLE IF EXISTS `m_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `m_author_id` bigint(20) NOT NULL,
  `m_content` varchar(255) NOT NULL,
  `m_created_time` datetime NOT NULL,
  `m_floor` bigint(20) DEFAULT NULL,
  `m_has_read` bit(1) NOT NULL,
  `m_post_id` bigint(20) DEFAULT NULL,
  `m_reply_id` bigint(20) DEFAULT NULL,
  `m_target_user_id` bigint(20) NOT NULL,
  `p_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_TARGET` (`m_target_user_id`),
  KEY `IDX_HASREAD` (`m_has_read`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_message`
--

LOCK TABLES `m_message` WRITE;
/*!40000 ALTER TABLE `m_message` DISABLE KEYS */;
INSERT INTO `m_message` VALUES (1,1,'小明回复了你的帖子：装逼指南 #floor1','2016-12-02 14:34:48',1,'\0',1,1,1,'reply');
/*!40000 ALTER TABLE `m_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_post`
--

DROP TABLE IF EXISTS `m_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `p_content` longtext NOT NULL,
  `p_created_time` datetime NOT NULL,
  `p_deleted_time` datetime DEFAULT NULL,
  `p_floor_count` bigint(20) NOT NULL,
  `p_last_author` bigint(20) DEFAULT NULL,
  `p_last_author_name` varchar(255) DEFAULT NULL,
  `p_last_reply_time` datetime DEFAULT NULL,
  `p_last_time` datetime NOT NULL,
  `p_perfect` bit(1) NOT NULL,
  `p_perfect_time` datetime DEFAULT NULL,
  `p_read_count` bigint(20) NOT NULL,
  `p_rendered_content` longtext NOT NULL,
  `p_reply_count` bigint(20) NOT NULL,
  `p_replyable` bit(1) NOT NULL,
  `p_status` varchar(255) NOT NULL,
  `p_summary` longtext,
  `p_title` varchar(255) NOT NULL,
  `p_top` bit(1) NOT NULL,
  `p_top_time` datetime DEFAULT NULL,
  `p_updated_time` datetime NOT NULL,
  `p_author` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_STATUS` (`p_status`),
  KEY `FK3hsotrueexqled1fda3o2btit` (`p_author`),
  CONSTRAINT `FK3hsotrueexqled1fda3o2btit` FOREIGN KEY (`p_author`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_post`
--

LOCK TABLES `m_post` WRITE;
/*!40000 ALTER TABLE `m_post` DISABLE KEYS */;
INSERT INTO `m_post` VALUES (1,'无可奉告！','2016-12-02 14:34:15',NULL,1,1,'小明','2016-12-02 14:34:48','2016-12-02 14:34:48','\0',NULL,2,'<p>无可奉告！</p>',1,'','actived','<p>无可奉告！</p>\n','装逼指南','\0',NULL,'2016-12-02 14:34:15',1);
/*!40000 ALTER TABLE `m_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_posts_tags`
--

DROP TABLE IF EXISTS `m_posts_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_posts_tags` (
  `post_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  KEY `FKdgn25w6upm0561cvfgarcwvla` (`tag_id`),
  KEY `FKipqwhuvd2fekwht3ttl79386v` (`post_id`),
  CONSTRAINT `FKdgn25w6upm0561cvfgarcwvla` FOREIGN KEY (`tag_id`) REFERENCES `m_tag` (`id`),
  CONSTRAINT `FKipqwhuvd2fekwht3ttl79386v` FOREIGN KEY (`post_id`) REFERENCES `m_post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_posts_tags`
--

LOCK TABLES `m_posts_tags` WRITE;
/*!40000 ALTER TABLE `m_posts_tags` DISABLE KEYS */;
INSERT INTO `m_posts_tags` VALUES (1,1),(1,2),(1,3);
/*!40000 ALTER TABLE `m_posts_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_reply`
--

DROP TABLE IF EXISTS `m_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `r_adopt` bit(1) NOT NULL,
  `r_adopt_time` datetime DEFAULT NULL,
  `r_content` longtext NOT NULL,
  `r_created_time` datetime NOT NULL,
  `r_deleted_time` datetime DEFAULT NULL,
  `r_down_count` bigint(20) NOT NULL,
  `r_floor` bigint(20) NOT NULL,
  `r_from` varchar(255) DEFAULT NULL,
  `r_perfect` bit(1) NOT NULL,
  `r_perfect_time` datetime DEFAULT NULL,
  `r_rendered_content` longtext NOT NULL,
  `r_status` varchar(255) NOT NULL,
  `r_top` bit(1) NOT NULL,
  `r_top_time` datetime DEFAULT NULL,
  `r_up_count` bigint(20) NOT NULL,
  `r_updated_time` datetime NOT NULL,
  `r_author` bigint(20) NOT NULL,
  `r_post` bigint(20) NOT NULL,
  `r_reply` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_STATUS` (`r_status`),
  KEY `FK1wsegqt8dnrms7awpum134o0p` (`r_author`),
  KEY `FKkdqrmgaeh7bye2i528g24dosn` (`r_post`),
  KEY `FKfdqlv37e4t8rr7ldmdnfkc9s1` (`r_reply`),
  CONSTRAINT `FK1wsegqt8dnrms7awpum134o0p` FOREIGN KEY (`r_author`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKfdqlv37e4t8rr7ldmdnfkc9s1` FOREIGN KEY (`r_reply`) REFERENCES `m_reply` (`id`),
  CONSTRAINT `FKkdqrmgaeh7bye2i528g24dosn` FOREIGN KEY (`r_post`) REFERENCES `m_post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_reply`
--

LOCK TABLES `m_reply` WRITE;
/*!40000 ALTER TABLE `m_reply` DISABLE KEYS */;
INSERT INTO `m_reply` VALUES (1,'\0',NULL,'还是要提高自己的知识水平，识得唔识得啊？','2016-12-02 14:34:48',NULL,0,1,NULL,'\0',NULL,'<p>还是要提高自己的知识水平，识得唔识得啊？</p>','actived','\0',NULL,0,'2016-12-02 14:34:48',1,1,NULL);
/*!40000 ALTER TABLE `m_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_tag`
--

DROP TABLE IF EXISTS `m_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `t_code` varchar(255) NOT NULL,
  `t_title` varchar(255) NOT NULL,
  `t_category` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j6hloymait2pyfntpqaegp7w` (`t_code`),
  KEY `FK9pso72vnsulpuplpnwnpxo14q` (`t_category`),
  CONSTRAINT `FK9pso72vnsulpuplpnwnpxo14q` FOREIGN KEY (`t_category`) REFERENCES `m_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_tag`
--

LOCK TABLES `m_tag` WRITE;
/*!40000 ALTER TABLE `m_tag` DISABLE KEYS */;
INSERT INTO `m_tag` VALUES (1,'haha','蛤蛤',1),(2,'zb','装逼',1),(3,'bignews','大新闻',1);
/*!40000 ALTER TABLE `m_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_permission`
--

DROP TABLE IF EXISTS `sys_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2vm98en2ouht0v15fvef2whp4` (`code`),
  UNIQUE KEY `UK_c5tr2w25fip75a01eb3r21nc4` (`title`),
  UNIQUE KEY `UK_phioc554k4w812wxqb0xepsjy` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` VALUES (1,'admin',NULL,'管理功能','/admin/**'),(2,'admin_index',1,'管理首页','/admin/index'),(3,'admin_posts',1,'话题管理','/admin/posts'),(4,'admin_replies',1,'回复管理','/admin/replies');
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_plpigyqwsqfn7mn66npgf9ftp` (`code`),
  UNIQUE KEY `UK_8sxhg7rmhhovrs05vp18o3ui4` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'ROLE_ADMIN','管理员'),(2,'ROLE_USER','普通用户');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_permission`
--

DROP TABLE IF EXISTS `sys_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FKomxrs8a388bknvhjokh440waq` (`permission_id`),
  CONSTRAINT `FK9q28ewrhntqeipl1t04kh1be7` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKomxrs8a388bknvhjokh440waq` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_permission`
--

LOCK TABLES `sys_role_permission` WRITE;
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
INSERT INTO `sys_role_permission` VALUES (1,1);
/*!40000 ALTER TABLE `sys_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_address` varchar(255) DEFAULT NULL,
  `u_area` varchar(255) DEFAULT NULL,
  `u_avatar` varchar(255) DEFAULT NULL,
  `u_cellphone` varchar(255) DEFAULT NULL,
  `u_channel` varchar(255) NOT NULL,
  `u_city` varchar(255) DEFAULT NULL,
  `u_client` varchar(255) NOT NULL,
  `u_company` varchar(255) DEFAULT NULL,
  `u_company_type` varchar(255) DEFAULT NULL,
  `u_email` varchar(255) DEFAULT NULL,
  `u_gender` int(11) NOT NULL,
  `u_id_card` varchar(255) DEFAULT NULL,
  `u_id_card_type` varchar(255) DEFAULT NULL,
  `u_info` varchar(255) DEFAULT NULL,
  `u_is_active` bit(1) NOT NULL,
  `u_last_login_time` datetime DEFAULT NULL,
  `u_login_times` int(11) NOT NULL,
  `u_name` varchar(255) DEFAULT NULL,
  `u_nick` varchar(255) DEFAULT NULL,
  `u_open_id` varchar(255) DEFAULT NULL,
  `u_password` varchar(255) NOT NULL,
  `u_province` varchar(255) DEFAULT NULL,
  `u_register_time` datetime NOT NULL,
  `u_title` varchar(255) DEFAULT NULL,
  `u_username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_s3w5w4ueoh3246x6pajshvb9r` (`u_username`),
  UNIQUE KEY `UK_bes9r9qwvd1iryoa864v3p1a0` (`u_cellphone`),
  UNIQUE KEY `UK_clfy5ypfawdaft0bo879pshgp` (`u_email`),
  UNIQUE KEY `UK_m1ft7yrukypw1jxthjkhm507n` (`u_id_card`),
  UNIQUE KEY `UK_hwosilu1i6am1bnl362k02jla` (`u_nick`),
  UNIQUE KEY `UK_mxcsyghx3ekgtjsjo3uvcgmb0` (`u_open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,NULL,NULL,NULL,'18600010001','ADMIN','Beijing','WEB','Tripinn',NULL,'demo@meiqiu.me',1,NULL,NULL,'你们啊，Naive！','','2016-09-11 00:00:00',0,'王小明','小明','202cb962ac59075b964b07152d234b70','$2a$10$EGULJ6bQfLJuc5GNWps8VuHv9wjA7JC.Ql/KTE.zQapyVFijzC57O','Beijing','2016-09-11 00:00:00',NULL,'xiaoming');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  CONSTRAINT `FKb40xxfch70f5qnyfw8yme1n1s` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_userprofile`
--

DROP TABLE IF EXISTS `sys_userprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_userprofile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `down_count` bigint(20) NOT NULL,
  `fans_count` bigint(20) NOT NULL,
  `favor_count` bigint(20) NOT NULL,
  `follow_count` bigint(20) NOT NULL,
  `post_count` bigint(20) NOT NULL,
  `pub_active` bit(1) NOT NULL,
  `pub_post` bit(1) NOT NULL,
  `pub_reply` bit(1) NOT NULL,
  `reply_count` bigint(20) NOT NULL,
  `tags_count` bigint(20) NOT NULL,
  `up_count` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_f1qnf9hyy00f2xr3u020yigvx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_userprofile`
--

LOCK TABLES `sys_userprofile` WRITE;
/*!40000 ALTER TABLE `sys_userprofile` DISABLE KEYS */;
INSERT INTO `sys_userprofile` VALUES (1,0,0,0,0,1,'','','',1,0,0,1);
/*!40000 ALTER TABLE `sys_userprofile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_usersocial`
--

DROP TABLE IF EXISTS `sys_usersocial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_usersocial` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `openid` varchar(255) NOT NULL,
  `source` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_1` (`source`,`openid`),
  KEY `FKo3abge7n60aqi5c4i1v4530dl` (`user_id`),
  CONSTRAINT `FKo3abge7n60aqi5c4i1v4530dl` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_usersocial`
--

LOCK TABLES `sys_usersocial` WRITE;
/*!40000 ALTER TABLE `sys_usersocial` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_usersocial` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-02 14:34:51
