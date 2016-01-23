-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 24, 2015 at 10:26 AM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `elibrary`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE IF NOT EXISTS `books` (
  `bookname` varchar(30) NOT NULL,
  `bookid` int(11) NOT NULL AUTO_INCREMENT,
  `bookdept` varchar(6) NOT NULL,
  `ownedby` varchar(30) NOT NULL DEFAULT '1234567890',
  PRIMARY KEY (`bookname`,`bookid`),
  UNIQUE KEY `bookid` (`bookid`),
  KEY `bookid_2` (`bookid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10102052 ;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`bookname`, `bookid`, `bookdept`, `ownedby`) VALUES
('CHEM Book 1', 10102040, 'CHEM', 'N100498'),
('CSE Book 2', 10102048, 'CSE', 'N100498'),
('CSE Book 3', 10102043, 'CSE', '1234567890'),
('CSE Book 5', 10102050, 'CSE', '1234567890'),
('ECE Book 2', 10102049, 'ECE', '1234567890'),
('Hello 2orld', 10102051, 'CHEM', '1234567890'),
('MECH Book 1', 10102045, 'MECH', 'N100498'),
('MECH Book 3', 10102042, 'MECH', 'N100498'),
('MME Book 1', 10102047, 'MME', 'N100498'),
('MME Book 2', 10102046, 'MME', '1234567890'),
('OTHERS Book 1', 10102041, 'OTHERS', '1234567890');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(30) NOT NULL,
  `password` varchar(15) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `type`) VALUES
('1234567', 'asdfghj', 0),
('1234567890', '1974653820', 0),
('N100496', '1234567', 0),
('N100498', 'a', 0),
('N100499', 'a', 1),
('N100500', 'a', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
