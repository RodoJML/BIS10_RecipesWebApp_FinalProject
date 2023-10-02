-- Creacion de tablas 

CREATE TABLE `ingredients` (
  `idingredient` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idingredient`)
) 

CREATE TABLE `recipeIngredients` (
  `id_recipess_FK` int NOT NULL,
  `id_userss_FK` int NOT NULL,
  `id_ingredientss_FK` int NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id_recipess_FK`,`id_userss_FK`,`id_ingredientss_FK`),
  KEY `id_userss_FK_idx` (`id_userss_FK`),
  KEY `id_ingredientss_FK_idx` (`id_ingredientss_FK`),
  CONSTRAINT `id_recipess_FK` FOREIGN KEY (`id_recipess_FK`) REFERENCES `recipes` (`idrecipe`),
  CONSTRAINT `id_userss_FK` FOREIGN KEY (`id_userss_FK`) REFERENCES `userRecipe` (`id`)
)

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
)

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
)


-- Agrega ingrediente a la base de datos 
INSERT INTO ingredients(name) VALUES (?)

-- Borrar ingrediente 
DELETE FROM ingredients WHERE idingredient = ?

-- Borra todos los ingredientes
TRUNCATE TABLE recipeIngredients
COMMIT
DELETE FROM ingredients

-- Carga todos los ingredientes desde la base de datos
SELECT idingredient, name FROM ingredients

-- Lista todos los id de los ingredientes desde la base de datos 
SELECT idingredient, name FROM ingredients

-- Carga el nombre del ingrediente basado en el ID del ingrediente
SELECT name
FROM ingredients
WHERE idingredient = ?

-- Cuenta la cantidad de ingredientes en la base de datos
SELECT COUNT(*) AS \"count\" FROM ingredients

-- Carga todas las recetas desde la base de datos, independientemente de su estado "activa" o "oculta" 
SELECT id_recipess_FK, id_userss_FK, id_ingredientss_FK, quantity
FROM recipeIngredients
WHERE id_recipess_FK = ?

-- Agrega el ingrediente a la receta 
INSERT INTO recipeIngredients(id_recipess_FK, id_userss_FK, id_ingredientss_FK, quantity)
VALUES (?,?,?,?)

-- Actualiza la cantidad de cada ingredientes en la receta
UPDATE recipeIngredients
SET quantity = ?
WHERE id_recipess_FK = ?
AND id_userss_FK = ?
AND id_ingredientss_FK = ?

-- Remueve 1 ingrediente seleccionado, de la receta
DELETE FROM recipeIngredients
WHERE id_recipess_FK = ?

-- Agrega la informacion general de la receta
INSERT INTO recipes(id_user_FK, name, duration, description, status, image)
VALUES (?, ?, ?, ?, 1, 'default.png'

-- Carga todas las recetas desde la base de datos, independientemente de su estado "activa" o "oculta" 
SELECT idrecipe, id_user_FK, name, duration, description, status, image
FROM recipes

-- Carga todas las recetas desde la base de datos que tenga su estado en "activa"
SELECT idrecipe, id_user_FK, name, duration, description, status, image
FROM recipes
WHERE status = 1

-- Carga la informacion de 1 receta en especifico
SELECT idrecipe, id_user_FK, name, duration, description, status, image
FROM recipes
WHERE idrecipe = ?

-- Carga desde la base de datos el ID de la receta mas reciente
SELECT idrecipe FROM recipes ORDER BY idrecipe DESC LIMIT 1

-- Actualiza la informacion de 1 receta, su nombre, descripcion, descripcion y estado
UPDATE recipes
SET name = ?, duration = ?, description = ?, status = ?
WHERE idrecipe = ?
AND id_user_FK = ?

-- Lista las recetas en la base de datos, basado en el ID de usuario asociado
SELECT idrecipe, id_user_FK, name, duration, description, status, image
FROM recipes
WHERE id_user_FK = ?

-- Actualiza el estado de la receta
UPDATE recipes
SET status = ?
WHERE idrecipe = ?
AND id_user_FK = ?

-- Cuenta la cantidad de recetas en la base de datos 
SELECT COUNT(*) AS \"count\" FROM recipes WHERE status = 1

--Cuenta la cantidad de recetas asociadas a 1 usuario en particular 
SELECT COUNT(*) AS \"count\" FROM recipes WHERE id_user_FK = ?

-- Agrega el nombre de la imagen a la receta para luego cargar la imagen
UPDATE recipes
SET image = ?
WHERE idrecipe = ?

-- Agrega 1 paso a 1 receta en la base de datos, con la descripcion vacia como "placeholder"
INSERT INTO steps(id_recipe_FK, id_users_FK, stepNumber)
VALUES (?, ?, ?)

-- Agrega 1 paso a la receta, completo, esta ves con su descripcion
UPDATE steps
SET stepDescription = ?
WHERE id_recipe_FK = ?
AND id_users_FK = ?
AND stepNumber = ?

-- Carga los pasos de las recetas desde la base de datos 
SELECT idstep, id_recipe_FK, id_users_FK, stepNumber, stepDescription
FROM steps

-- Carga todos los pasos asociados a una receta
SELECT idstep, id_recipe_FK, id_users_FK, stepNumber, stepDescription
FROM steps WHERE id_recipe_FK = ?

-- Remueve todos los pasos de 1 receta en particular
DELETE FROM steps
WHERE id_recipe_FK = ?

-- Agrega o registra el usuario en la base de datos 
INSERT INTO userRecipe(name, lastName, age, email, password)
VALUES (?, ?, ?, ?, ?)

-- Valida el usuario en la base de datos, cargando la informacion de un usuario existente en la base. 
SELECT id, name, lastName, age, email, password
FROM userRecipe
WHERE email =?
AND password =?

-- Carga el nombre de un usuario desde la base de datos basado en su ID
SELECT name, lastName
FROM userRecipe
WHERE id = ?

-- Lista todos los usuarios en la base de datos 
SELECT id, name, lastName
FROM userRecipe