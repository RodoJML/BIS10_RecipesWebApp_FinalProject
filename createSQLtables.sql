CREATE TABLE `userRecipe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

This has status
CREATE TABLE `recipes` (
  `idrecipe` int NOT NULL AUTO_INCREMENT,
  `id_user_FK` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `duration` int NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `image` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idrecipe`,`id_user_FK`),
  KEY `id_user(FK)_idx` (`id_user_FK`),
  CONSTRAINT `id_user(FK)` FOREIGN KEY (`id_user_FK`) REFERENCES `userRecipe` (`id`)
)

-- CREATE TABLE `recipes` (
--   `idrecipe` int NOT NULL AUTO_INCREMENT,
--   `id_user_FK` int NOT NULL,
--   `name` varchar(45) NOT NULL,
--   `duration` int NOT NULL,
--   `description` varchar(45) DEFAULT NULL,
--   PRIMARY KEY (`idrecipe`,`id_user_FK`),
--   KEY `id_user(FK)_idx` (`id_user_FK`),
--   CONSTRAINT `id_user(FK)` FOREIGN KEY (`id_user_FK`) REFERENCES `userRecipe` (`id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `steps` (
  `idstep` int NOT NULL AUTO_INCREMENT,
  `id_recipe_FK` int NOT NULL,
  `id_users_FK` int NOT NULL,
  `stepNumber` int NOT NULL,
  `stepDescription` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idstep`,`id_recipe_FK`,`id_users_FK`),
  KEY `id_recipe(FK)_idx` (`id_recipe_FK`),
  KEY `id_users(FK)_idx` (`id_users_FK`),
  CONSTRAINT `id_recipe(FK)` FOREIGN KEY (`id_recipe_FK`) REFERENCES `recipes` (`idrecipe`),
  CONSTRAINT `id_users(FK)` FOREIGN KEY (`id_users_FK`) REFERENCES `recipes` (`id_user_FK`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci



CREATE TABLE `ingredients` (
  `idingredient` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idingredient`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `recipeIngredients` (
  `id_recipess_FK` int NOT NULL,
  `id_userss_FK` int NOT NULL,
  `id_ingredientss_FK` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id_recipess_FK`,`id_userss_FK`,`id_ingredientss_FK`),
  KEY `id_userss_FK_idx` (`id_userss_FK`),
  KEY `id_ingredientss_FK_idx` (`id_ingredientss_FK`),
  CONSTRAINT `id_ingredientss_FK` FOREIGN KEY (`id_ingredientss_FK`) REFERENCES `ingredients` (`idingredient`),
  CONSTRAINT `id_recipess_FK` FOREIGN KEY (`id_recipess_FK`) REFERENCES `recipes` (`idrecipe`),
  CONSTRAINT `id_userss_FK` FOREIGN KEY (`id_userss_FK`) REFERENCES `userRecipe` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
