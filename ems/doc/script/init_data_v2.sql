CREATE DATABASE  IF NOT EXISTS `ems` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ems`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: ems
-- ------------------------------------------------------
-- Server version	5.6.19

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exampaper`
--

DROP TABLE IF EXISTS `exampaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exampaper` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATEDATE` date DEFAULT NULL,
  `DURATION` int(11) DEFAULT NULL,
  `STARTDATE` date DEFAULT NULL,
  `MODULE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_EXAMPAPER_MODULE_ID` (`MODULE_ID`),
  CONSTRAINT `FK_EXAMPAPER_MODULE_ID` FOREIGN KEY (`MODULE_ID`) REFERENCES `module` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exampaper`
--

LOCK TABLES `exampaper` WRITE;
/*!40000 ALTER TABLE `exampaper` DISABLE KEYS */;
INSERT INTO `exampaper` VALUES (1,'2014-10-31',180,'2014-11-05',1);
/*!40000 ALTER TABLE `exampaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examsection`
--

DROP TABLE IF EXISTS `examsection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examsection` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SECTIONNAME` varchar(255) DEFAULT NULL,
  `SECTIONTYPE` int(11) DEFAULT NULL,
  `TOTALMARKS` varchar(255) DEFAULT NULL,
  `EXAMPAPER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_EXAMSECTION_EXAMPAPER_ID` (`EXAMPAPER_ID`),
  CONSTRAINT `FK_EXAMSECTION_EXAMPAPER_ID` FOREIGN KEY (`EXAMPAPER_ID`) REFERENCES `exampaper` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examsection`
--

LOCK TABLES `examsection` WRITE;
/*!40000 ALTER TABLE `examsection` DISABLE KEYS */;
INSERT INTO `examsection` VALUES (1,'section 1',1,'5',1);
/*!40000 ALTER TABLE `examsection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examsession`
--

DROP TABLE IF EXISTS `examsession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examsession` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SESSIONID` varchar(255) DEFAULT NULL,
  `STARTDATE` date DEFAULT NULL,
  `EXAMPAPER_ID` bigint(20) DEFAULT NULL,
  `STUDENT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SESSIONID` (`SESSIONID`),
  KEY `FK_EXAMSESSION_STUDENT_ID` (`STUDENT_ID`),
  KEY `FK_EXAMSESSION_EXAMPAPER_ID` (`EXAMPAPER_ID`),
  CONSTRAINT `FK_EXAMSESSION_EXAMPAPER_ID` FOREIGN KEY (`EXAMPAPER_ID`) REFERENCES `exampaper` (`ID`),
  CONSTRAINT `FK_EXAMSESSION_STUDENT_ID` FOREIGN KEY (`STUDENT_ID`) REFERENCES `student` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examsession`
--

LOCK TABLES `examsession` WRITE;
/*!40000 ALTER TABLE `examsession` DISABLE KEYS */;
INSERT INTO `examsession` VALUES (1,'session id','2014-11-05',1,1);
/*!40000 ALTER TABLE `examsession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturer`
--

DROP TABLE IF EXISTS `lecturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lecturer` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LECTURERID` varchar(255) DEFAULT NULL,
  `LECTURERNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturer`
--

LOCK TABLES `lecturer` WRITE;
/*!40000 ALTER TABLE `lecturer` DISABLE KEYS */;
INSERT INTO `lecturer` VALUES (1,'lecturer','lecturer name');
/*!40000 ALTER TABLE `lecturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturer_module`
--

DROP TABLE IF EXISTS `lecturer_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lecturer_module` (
  `modules_ID` bigint(20) NOT NULL,
  `lecturers_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`modules_ID`,`lecturers_ID`),
  KEY `FK_LECTURER_MODULE_lecturers_ID` (`lecturers_ID`),
  CONSTRAINT `FK_LECTURER_MODULE_lecturers_ID` FOREIGN KEY (`lecturers_ID`) REFERENCES `lecturer` (`ID`),
  CONSTRAINT `FK_LECTURER_MODULE_modules_ID` FOREIGN KEY (`modules_ID`) REFERENCES `module` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturer_module`
--

LOCK TABLES `lecturer_module` WRITE;
/*!40000 ALTER TABLE `lecturer_module` DISABLE KEYS */;
INSERT INTO `lecturer_module` VALUES (1,1),(2,1);
/*!40000 ALTER TABLE `lecturer_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MODULECODE` varchar(255) DEFAULT NULL,
  `MODULENAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MODULECODE` (`MODULECODE`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES (1,'ejava','ejava'),(2,'oodp','oodp');
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATEDON` date DEFAULT NULL,
  `MARK` int(11) DEFAULT NULL,
  `QUESTIONTEXT` varchar(255) DEFAULT NULL,
  `QUESTIONTYPE` int(11) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CREATEDBY_ID` bigint(20) DEFAULT NULL,
  `MODULE_ID` bigint(20) DEFAULT NULL,
  `depreciated` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_QUESTION_CREATEDBY_ID` (`CREATEDBY_ID`),
  KEY `FK_QUESTION_MODULE_ID_idx` (`MODULE_ID`),
  CONSTRAINT `FK_QUESTION_CREATEDBY_ID` FOREIGN KEY (`CREATEDBY_ID`) REFERENCES `lecturer` (`ID`),
  CONSTRAINT `FK_QUESTION_MODULE_ID` FOREIGN KEY (`MODULE_ID`) REFERENCES `module` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'2014-10-31',2,'question 1',1,0,1,1,NULL),(2,'2014-10-31',3,'question 2',1,0,1,1,NULL),(3,'2014-11-05',2,'asasdsadsad',0,0,1,1,NULL),(4,'2014-11-05',2,'<span style=\"font-weight: bold;\">aasdasdsad</span>',0,0,1,1,NULL),(5,'2014-11-05',3,'asdasd',0,0,1,1,NULL),(6,'2014-11-05',6,'sdasd',0,0,1,1,NULL),(7,'2014-11-05',4,'test',0,0,1,1,NULL),(8,'2014-11-05',5,'test 2',0,0,1,1,NULL),(9,'2014-11-05',3,'test 3',0,0,1,1,NULL),(10,'2014-11-05',3,'asdasd',0,0,1,1,NULL),(11,'2014-11-05',5,'asdadsd',0,0,1,1,NULL),(12,'2014-11-06',233,'asdasd',0,0,1,1,NULL),(13,'2014-11-06',12,'milandasasd',0,0,1,1,NULL),(14,'2014-11-06',100,'test milan questio',0,0,1,1,NULL),(15,'2014-11-06',3,'sadasda',0,0,1,1,NULL),(16,'2014-11-06',12,'sdasd',0,0,1,1,NULL),(17,'2014-11-06',324,'sdasdasd',0,0,1,2,NULL),(18,'2014-11-06',2342,'asdasd',0,0,1,1,NULL),(19,'2014-11-06',3,'asdasd',0,0,1,2,NULL),(20,'2014-11-06',3,'asdasd',0,0,1,1,NULL),(21,'2014-11-06',4,'asdasdasd',0,0,1,1,NULL),(22,'2014-11-06',32,'asdasdasd',0,0,1,1,NULL),(23,'2014-11-06',32,'asdasdasdasd',0,0,1,1,NULL),(24,'2014-11-06',2321,'asdasdsd',0,0,1,1,NULL),(25,'2014-11-06',232,'dasdasd',0,0,1,1,NULL),(26,'2014-11-06',23,'asdadasd',0,0,1,1,NULL),(27,'2014-11-06',23,'asdasdasd',0,0,1,1,NULL),(28,'2014-11-06',32,'asdasdsad',0,0,1,1,NULL),(29,'2014-11-06',43,'adasdsad',0,0,1,1,NULL),(30,'2014-11-06',234,'qdasdasd',0,0,1,2,NULL),(31,'2014-11-06',2,'asdas',0,0,1,1,0),(32,'2014-11-07',34,'multiple correct',1,0,1,1,0),(33,'2014-11-07',21,'essay',2,0,1,1,0);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_examsection`
--

DROP TABLE IF EXISTS `question_examsection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_examsection` (
  `questions_ID` bigint(20) NOT NULL,
  `examSections_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`questions_ID`,`examSections_ID`),
  KEY `FK_QUESTION_EXAMSECTION_examSections_ID` (`examSections_ID`),
  CONSTRAINT `FK_QUESTION_EXAMSECTION_examSections_ID` FOREIGN KEY (`examSections_ID`) REFERENCES `examsection` (`ID`),
  CONSTRAINT `FK_QUESTION_EXAMSECTION_questions_ID` FOREIGN KEY (`questions_ID`) REFERENCES `question` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_examsection`
--

LOCK TABLES `question_examsection` WRITE;
/*!40000 ALTER TABLE `question_examsection` DISABLE KEYS */;
INSERT INTO `question_examsection` VALUES (1,1),(2,1);
/*!40000 ALTER TABLE `question_examsection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_subjecttag`
--

DROP TABLE IF EXISTS `question_subjecttag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_subjecttag` (
  `questions_ID` bigint(20) NOT NULL,
  `subjectTags_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`questions_ID`,`subjectTags_ID`),
  KEY `FK_QUESTION_SUBJECTTAG_subjectTags_ID` (`subjectTags_ID`),
  CONSTRAINT `FK_QUESTION_SUBJECTTAG_questions_ID` FOREIGN KEY (`questions_ID`) REFERENCES `question` (`ID`),
  CONSTRAINT `FK_QUESTION_SUBJECTTAG_subjectTags_ID` FOREIGN KEY (`subjectTags_ID`) REFERENCES `subjecttag` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_subjecttag`
--

LOCK TABLES `question_subjecttag` WRITE;
/*!40000 ALTER TABLE `question_subjecttag` DISABLE KEYS */;
INSERT INTO `question_subjecttag` VALUES (16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1),(33,1),(16,2),(17,2),(18,2),(19,2),(23,2),(32,2),(33,2);
/*!40000 ALTER TABLE `question_subjecttag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionoption`
--

DROP TABLE IF EXISTS `questionoption`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionoption` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OPTIONVALUE` varchar(255) DEFAULT NULL,
  `QUESTION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_QUESTIONOPTION_QUESTION_ID` (`QUESTION_ID`),
  CONSTRAINT `FK_QUESTIONOPTION_QUESTION_ID` FOREIGN KEY (`QUESTION_ID`) REFERENCES `question` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionoption`
--

LOCK TABLES `questionoption` WRITE;
/*!40000 ALTER TABLE `questionoption` DISABLE KEYS */;
INSERT INTO `questionoption` VALUES (1,'option 1',1),(2,'option 2',1),(3,'option 3',1),(4,'option 4',1),(5,'option 1',2),(6,'option 2',2),(7,'option 3',2),(8,'option 4',2),(9,'asdasdasd',3),(10,'asdasdas',4),(11,'asdasdas',4),(12,'asdsad',5),(13,'sdsad',6),(14,'test',7),(15,'asdasdas',8),(16,'sdasd',9),(17,'asdasd',10),(18,'asd',11),(19,'asdasd',12),(20,'asdsad',13),(21,'asdasd',14),(22,'asdasd',15),(23,'asdasd',16),(24,'sadasd',17),(25,'21asdasdas',18),(26,'sdad',19),(27,'asdsad',20),(28,'sdasdsad',21),(29,'asdasd',22),(30,'sdadsdas',23),(31,'sadasda',24),(32,'asdsad',25),(33,'asdasd',26),(34,'asdasd',27),(35,'asdasd',28),(36,'asdadasd',29),(37,'adasd',30),(38,'asdasd',31),(39,'multiple correct option 1',32);
/*!40000 ALTER TABLE `questionoption` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionoption_studentanswer`
--

DROP TABLE IF EXISTS `questionoption_studentanswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionoption_studentanswer` (
  `studentAnswers_ID` bigint(20) NOT NULL,
  `optionsSelected_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`studentAnswers_ID`,`optionsSelected_ID`),
  KEY `FK_QUESTIONOPTION_STUDENTANSWER_optionsSelected_ID` (`optionsSelected_ID`),
  CONSTRAINT `FK_QUESTIONOPTION_STUDENTANSWER_optionsSelected_ID` FOREIGN KEY (`optionsSelected_ID`) REFERENCES `questionoption` (`ID`),
  CONSTRAINT `FK_QUESTIONOPTION_STUDENTANSWER_studentAnswers_ID` FOREIGN KEY (`studentAnswers_ID`) REFERENCES `studentanswer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionoption_studentanswer`
--

LOCK TABLES `questionoption_studentanswer` WRITE;
/*!40000 ALTER TABLE `questionoption_studentanswer` DISABLE KEYS */;
INSERT INTO `questionoption_studentanswer` VALUES (1,1),(2,5);
/*!40000 ALTER TABLE `questionoption_studentanswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STUDENTID` varchar(255) DEFAULT NULL,
  `STUDENTNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `STUDENTID` (`STUDENTID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'student','milan');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_module`
--

DROP TABLE IF EXISTS `student_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_module` (
  `modules_ID` bigint(20) NOT NULL,
  `students_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`modules_ID`,`students_ID`),
  KEY `FK_STUDENT_MODULE_students_ID` (`students_ID`),
  CONSTRAINT `FK_STUDENT_MODULE_modules_ID` FOREIGN KEY (`modules_ID`) REFERENCES `module` (`ID`),
  CONSTRAINT `FK_STUDENT_MODULE_students_ID` FOREIGN KEY (`students_ID`) REFERENCES `student` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_module`
--

LOCK TABLES `student_module` WRITE;
/*!40000 ALTER TABLE `student_module` DISABLE KEYS */;
INSERT INTO `student_module` VALUES (1,1);
/*!40000 ALTER TABLE `student_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentanswer`
--

DROP TABLE IF EXISTS `studentanswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentanswer` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ANSWER` varchar(255) DEFAULT NULL,
  `EXAMSESSION_ID` bigint(20) DEFAULT NULL,
  `QUESTION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_STUDENTANSWER_EXAMSESSION_ID` (`EXAMSESSION_ID`),
  KEY `FK_STUDENTANSWER_QUESTION_ID` (`QUESTION_ID`),
  CONSTRAINT `FK_STUDENTANSWER_EXAMSESSION_ID` FOREIGN KEY (`EXAMSESSION_ID`) REFERENCES `examsession` (`ID`),
  CONSTRAINT `FK_STUDENTANSWER_QUESTION_ID` FOREIGN KEY (`QUESTION_ID`) REFERENCES `question` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentanswer`
--

LOCK TABLES `studentanswer` WRITE;
/*!40000 ALTER TABLE `studentanswer` DISABLE KEYS */;
INSERT INTO `studentanswer` VALUES (1,NULL,1,1),(2,NULL,1,2);
/*!40000 ALTER TABLE `studentanswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjecttag`
--

DROP TABLE IF EXISTS `subjecttag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subjecttag` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TAGNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjecttag`
--

LOCK TABLES `subjecttag` WRITE;
/*!40000 ALTER TABLE `subjecttag` DISABLE KEYS */;
INSERT INTO `subjecttag` VALUES (1,'ejava'),(2,'oodp');
/*!40000 ALTER TABLE `subjecttag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjecttag_question`
--

DROP TABLE IF EXISTS `subjecttag_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subjecttag_question` (
  `questions_ID` bigint(20) NOT NULL,
  `subjectTags_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`questions_ID`,`subjectTags_ID`),
  KEY `FK_SUBJECTTAG_QUESTION_subjectTags_ID` (`subjectTags_ID`),
  CONSTRAINT `FK_SUBJECTTAG_QUESTION_questions_ID` FOREIGN KEY (`questions_ID`) REFERENCES `question` (`ID`),
  CONSTRAINT `FK_SUBJECTTAG_QUESTION_subjectTags_ID` FOREIGN KEY (`subjectTags_ID`) REFERENCES `subjecttag` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjecttag_question`
--

LOCK TABLES `subjecttag_question` WRITE;
/*!40000 ALTER TABLE `subjecttag_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `subjecttag_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `USERID` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  PRIMARY KEY (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','admin'),('lecturer','lecturer'),('student','student');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_groups`
--

DROP TABLE IF EXISTS `users_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_groups` (
  `GROUPID` varchar(20) NOT NULL,
  `USERID` varchar(255) NOT NULL,
  PRIMARY KEY (`GROUPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_groups`
--

LOCK TABLES `users_groups` WRITE;
/*!40000 ALTER TABLE `users_groups` DISABLE KEYS */;
INSERT INTO `users_groups` VALUES ('admin','admin'),('lecturer','lecturer'),('student','student');
/*!40000 ALTER TABLE `users_groups` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-07  0:11:29
