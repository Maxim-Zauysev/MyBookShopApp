DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
<<<<<<< HEAD

=======
>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056

CREATE TABLE  books(
id BIGSERIAL PRIMARY KEY,
author VARCHAR(250) NOT NULL,
title VARCHAR(250) NOT NULL,
priceOld  VARCHAR(250) DEFAULT NULL,
price VARCHAR(250) DEFAULT NULL
);

CREATE TABLE  authors(
<<<<<<< HEAD
    id BIGSERIAL PRIMARY KEY,
    firstName VARCHAR(250) NOT NULL,
    lastName VARCHAR(250) NOT NULL
=======
id INT AUTO_INCREMENT PRIMARY KEY,
firstName VARCHAR(250) NOT NULL,
lastName VARCHAR(250) NOT NULL
>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056
);