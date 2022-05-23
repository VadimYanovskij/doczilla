CREATE TABLE `student` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `name` varchar(45) NOT NULL,
                           `lastname` varchar(45) NOT NULL,
                           `middle_name` varchar(45) NOT NULL,
                           `birthday` date NOT NULL,
                           `group` varchar(45) NOT NULL,
                           PRIMARY KEY (`id`)
);