-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Dec 30, 2021 at 04:25 PM
-- Server version: 5.7.34
-- PHP Version: 7.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kps`
--

-- --------------------------------------------------------

--
-- Table structure for table `english`
--

CREATE TABLE `english` (
  `reg_no` varchar(6) NOT NULL,
  `year` varchar(4) NOT NULL,
  `term` varchar(1) NOT NULL DEFAULT '1',
  `class` varchar(3) NOT NULL,
  `BOT` varchar(3) NOT NULL DEFAULT '00',
  `MOT` varchar(3) NOT NULL DEFAULT '00',
  `EOT` varchar(3) NOT NULL DEFAULT '00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `mathematics`
--

CREATE TABLE `mathematics` (
  `reg_no` varchar(6) NOT NULL,
  `year` varchar(4) NOT NULL,
  `term` varchar(1) NOT NULL DEFAULT '1',
  `class` varchar(3) NOT NULL,
  `BOT` varchar(3) NOT NULL DEFAULT '00',
  `MOT` varchar(3) NOT NULL DEFAULT '00',
  `EOT` varchar(3) NOT NULL DEFAULT '00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `science`
--

CREATE TABLE `science` (
  `reg_no` varchar(6) NOT NULL,
  `year` varchar(4) NOT NULL,
  `term` varchar(1) NOT NULL DEFAULT '1',
  `class` varchar(3) NOT NULL,
  `BOT` varchar(3) NOT NULL DEFAULT '00',
  `MOT` varchar(3) NOT NULL DEFAULT '00',
  `EOT` varchar(3) NOT NULL DEFAULT '00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `social_studies`
--

CREATE TABLE `social_studies` (
  `reg_no` varchar(6) NOT NULL,
  `year` varchar(4) NOT NULL,
  `term` varchar(1) NOT NULL DEFAULT '1',
  `class` varchar(3) NOT NULL,
  `BOT` varchar(3) NOT NULL DEFAULT '00',
  `MOT` varchar(3) NOT NULL DEFAULT '00',
  `EOT` varchar(3) NOT NULL DEFAULT '00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `reg_no` varchar(6) NOT NULL DEFAULT '0',
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `gender` enum('M','F') NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `class` varchar(5) NOT NULL,
  `user_name` varchar(250) NOT NULL,
  `password` varchar(250) DEFAULT 'pass1234'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`reg_no`, `first_name`, `last_name`, `gender`, `date_of_birth`, `class`, `user_name`, `password`) VALUES
('210002', 'Tonny', 'Baw', 'M', '2009-12-16', 'P7', 'btonny', 'pass1234'),
('210003', 'Vien', 'Okoth', 'M', '2007-07-17', 'P6', 'ovien', 'pass1234'),
('210004', 'David', 'Olal', 'M', '2009-07-08', 'P5', 'odavid', 'pass1234'),
('210005', 'Vanessa', 'Awiner', 'F', '2008-08-12', 'P5', 'avanessa', 'pass1234'),
('210007', 'Oran', 'Tendo', 'F', '2004-05-06', 'P7', 'toran', 'pass1234'),
('210008', 'Test', 'P1', 'M', '2014-06-17', 'P1', 'ptest', 'pass1234');

--
-- Triggers `students`
--
DELIMITER $$
CREATE TRIGGER `tg_students_regno_insert` BEFORE INSERT ON `students` FOR EACH ROW BEGIN
  INSERT INTO student_regno_seq VALUES (NULL);
  SET NEW.reg_no = CONCAT(DATE_FORMAT(NOW(),'%y'), LPAD(LAST_INSERT_ID(), 4, '0'));
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `student_regno_seq`
--

CREATE TABLE `student_regno_seq` (
  `id` int(4) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student_regno_seq`
--

INSERT INTO `student_regno_seq` (`id`) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8);

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

CREATE TABLE `teachers` (
  `id` varchar(5) NOT NULL DEFAULT '0',
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `user_name` varchar(250) NOT NULL,
  `password` varchar(250) DEFAULT 'password'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`id`, `first_name`, `last_name`, `subject`, `user_name`, `password`) VALUES
('T0005', 'English', 'Teacher', 'English', 'tenglish', 'password'),
('T0006', 'Math', 'Teacher', 'Mathematics', 'tmath', 'password'),
('T0007', 'Science', 'Teacher', 'Science', 'tscience', 'password'),
('T0008', 'Sst', 'Teacher', 'Social Studies', 'tsst', 'password');

--
-- Triggers `teachers`
--
DELIMITER $$
CREATE TRIGGER `tg_teachers_regno_insert` BEFORE INSERT ON `teachers` FOR EACH ROW BEGIN
  INSERT INTO teacher_id_seq VALUES (NULL);
  SET NEW.id = CONCAT('T', LPAD(LAST_INSERT_ID(), 4, '0'));
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `teacher_id_seq`
--

CREATE TABLE `teacher_id_seq` (
  `id` int(4) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `teacher_id_seq`
--

INSERT INTO `teacher_id_seq` (`id`) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`reg_no`);

--
-- Indexes for table `student_regno_seq`
--
ALTER TABLE `student_regno_seq`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `teacher_id_seq`
--
ALTER TABLE `teacher_id_seq`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `student_regno_seq`
--
ALTER TABLE `student_regno_seq`
  MODIFY `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `teacher_id_seq`
--
ALTER TABLE `teacher_id_seq`
  MODIFY `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
