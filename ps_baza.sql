-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 30, 2016 at 08:04 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ps_baza`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrator`
--

CREATE TABLE IF NOT EXISTS `administrator` (
  `user` tinytext,
  `pass` tinytext,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `administrator`
--

INSERT INTO `administrator` (`user`, `pass`, `id`, `token`) VALUES
('ivan', 'ivan', 1, 'TOKEN##1'),
('marko', 'marko', 2, NULL),
('q', 'q', 3, 'TOKEN##3');

-- --------------------------------------------------------

--
-- Table structure for table `liga`
--

CREATE TABLE IF NOT EXISTS `liga` (
  `ligaID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `naziv` tinytext,
  `broj_takmicara` int(11) DEFAULT NULL,
  `takmicenje` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`ligaID`),
  KEY `liga_ibfk_1` (`takmicenje`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `liga`
--

INSERT INTO `liga` (`ligaID`, `naziv`, `broj_takmicara`, `takmicenje`) VALUES
(2, 'dsakop', 4, 8),
(3, 'asdads', 2, 11),
(4, 'liga33', 4, 10),
(5, 'jelenova liga', 22, 10),
(6, 'vozilovaLiga', 2, 14),
(7, 'dsaads', 12, 8),
(8, 'liga3', 1, 14),
(9, 'brza liga ', 1, 8),
(10, 'JelenLigaIII_bg', 7, 10);

-- --------------------------------------------------------

--
-- Table structure for table `mec`
--

CREATE TABLE IF NOT EXISTS `mec` (
  `mecID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `takmicarDID` int(11) unsigned DEFAULT NULL,
  `takmicarGID` int(11) unsigned DEFAULT NULL,
  `rezultat` tinytext,
  PRIMARY KEY (`mecID`),
  KEY `mec_ibfk_1` (`takmicarDID`),
  KEY `mec_ibfk_2` (`takmicarGID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `mec`
--

INSERT INTO `mec` (`mecID`, `takmicarDID`, `takmicarGID`, `rezultat`) VALUES
(2, 11, 16, '6:2, 6:2'),
(3, 1, 5, '6:2,6:3'),
(5, 16, 11, '7:6,2:6,6:4'),
(8, 1, 5, '6:2,8:6'),
(9, 1, 5, '6:2,6:2'),
(10, 1, 19, '6:2,6:2,6:2');

-- --------------------------------------------------------

--
-- Table structure for table `mesto`
--

CREATE TABLE IF NOT EXISTS `mesto` (
  `ptt` int(11) unsigned NOT NULL,
  `naziv` tinytext,
  PRIMARY KEY (`ptt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `mesto`
--

INSERT INTO `mesto` (`ptt`, `naziv`) VALUES
(121, 'beograd'),
(500, 'milanovac'),
(600, 'subotica'),
(11000, 'beograd'),
(12000, 'ns'),
(12323, 'ns');

-- --------------------------------------------------------

--
-- Table structure for table `takmicar`
--

CREATE TABLE IF NOT EXISTS `takmicar` (
  `takmicarID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ime` tinytext,
  `prezime` tinytext,
  `opis` text,
  `fb_link` tinytext,
  `pozicija` int(11) DEFAULT NULL,
  `broj_pobeda` int(11) DEFAULT NULL,
  `broj_izgubljenih` int(11) DEFAULT NULL,
  `broj_poena` int(11) DEFAULT NULL,
  `gem_plus` int(11) DEFAULT NULL,
  `gem_minus` int(11) DEFAULT NULL,
  `set_plus` int(11) DEFAULT NULL,
  `set_minus` int(11) DEFAULT NULL,
  `liga` int(10) unsigned DEFAULT NULL,
  `mesto` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`takmicarID`),
  KEY `takmicar_ibfk_1` (`liga`),
  KEY `takmicar_ibfk_2` (`mesto`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `takmicar`
--

INSERT INTO `takmicar` (`takmicarID`, `ime`, `prezime`, `opis`, `fb_link`, `pozicija`, `broj_pobeda`, `broj_izgubljenih`, `broj_poena`, `gem_plus`, `gem_minus`, `set_plus`, `set_minus`, `liga`, `mesto`) VALUES
(1, 'ivan', 'arackic', 'sgdgd', 'null', 0, 1, 0, 0, 15, -16, 2, -1, 5, 11000),
(5, 'sdaasd', 'adsdas', 'adsdas', 'null', 0, 0, -1, 0, 16, -15, -1, -1, 5, 11000),
(6, 'fad', 'adf', 'adf', 'null', 0, 0, 0, 0, 0, 0, 0, 0, 2, 600),
(11, 'dfdf', 'sfdsfd', 'fdssdf', 'null', 0, 0, -1, 0, 16, -15, -1, -1, 4, 12323),
(12, 'Marko', 'Blanusa', 'marko', 'blanusa', 0, 0, 0, 0, 0, 0, 0, 0, 10, 600),
(13, 'marko', 'markovic', 'dasdas', 'null', 0, 0, 0, 0, 0, 0, 0, 0, 6, 121),
(16, 'Marko', 'Ivanovic', 'daads', 'null', 0, 1, 0, 0, 15, -16, 1, 1, 4, 11000),
(18, 'ivan', 'ivanovic', 'sadkposda', 'null', 0, 0, 0, 0, 0, 0, 0, 0, 5, 121),
(19, 'marko', 'markovic', 'daoda', 'null', 0, 0, 0, 0, 0, 0, 0, 0, 5, 121),
(20, 'Ivan', 'Filipovic', 'Mnogo dobar decko', 'null', 0, 0, 0, 0, 0, 0, 0, 0, 4, 500);

-- --------------------------------------------------------

--
-- Table structure for table `takmicenje`
--

CREATE TABLE IF NOT EXISTS `takmicenje` (
  `takmicenjeID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `naziv` tinytext,
  `datum_pocetka` date DEFAULT NULL,
  `tiptakmicenja` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`takmicenjeID`),
  KEY `takmicenje_ibfk_1` (`tiptakmicenja`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=52 ;

--
-- Dumping data for table `takmicenje`
--

INSERT INTO `takmicenje` (`takmicenjeID`, `naziv`, `datum_pocetka`, `tiptakmicenja`) VALUES
(8, 'beton ligaX', '2015-07-03', 65),
(9, 'beton takmicenje NS', '2015-07-06', 66),
(10, 'jelensuper takmicenje2', '2015-07-06', 65),
(11, 'Ogi222', '2015-07-06', 65),
(14, 'sustinaVozila34', '2015-07-02', 67),
(50, 'novo', '2016-03-26', 64),
(51, 'lll', '2016-03-26', 64);

-- --------------------------------------------------------

--
-- Table structure for table `tiptakmicenja`
--

CREATE TABLE IF NOT EXISTS `tiptakmicenja` (
  `tiptakmicenjaID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `naziv_tipa` tinytext,
  `vrsta_sistema` int(11) DEFAULT NULL,
  PRIMARY KEY (`tiptakmicenjaID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=69 ;

--
-- Dumping data for table `tiptakmicenja`
--

INSERT INTO `tiptakmicenja` (`tiptakmicenjaID`, `naziv_tipa`, `vrsta_sistema`) VALUES
(64, 'jednokruzni sistem', 1),
(65, 'dvokruzni sistem', 2),
(66, 'prvi tip', 1),
(67, 'tip33', 1),
(68, 'tip34', 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `liga`
--
ALTER TABLE `liga`
  ADD CONSTRAINT `liga_ibfk_1` FOREIGN KEY (`takmicenje`) REFERENCES `takmicenje` (`takmicenjeID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `mec`
--
ALTER TABLE `mec`
  ADD CONSTRAINT `mec_ibfk_1` FOREIGN KEY (`takmicarDID`) REFERENCES `takmicar` (`takmicarID`) ON DELETE CASCADE,
  ADD CONSTRAINT `mec_ibfk_2` FOREIGN KEY (`takmicarGID`) REFERENCES `takmicar` (`takmicarID`) ON DELETE CASCADE;

--
-- Constraints for table `takmicar`
--
ALTER TABLE `takmicar`
  ADD CONSTRAINT `takmicar_ibfk_2` FOREIGN KEY (`mesto`) REFERENCES `mesto` (`ptt`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `takmicar_ibfk_1` FOREIGN KEY (`liga`) REFERENCES `liga` (`ligaID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `takmicenje`
--
ALTER TABLE `takmicenje`
  ADD CONSTRAINT `takmicenje_ibfk_1` FOREIGN KEY (`tiptakmicenja`) REFERENCES `tiptakmicenja` (`tiptakmicenjaID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
