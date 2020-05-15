-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 15-05-2020 a las 18:54:30
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.2.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `api_accounting`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `category`
--

CREATE TABLE `category` (
  `ide_category` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `enable` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clients`
--

CREATE TABLE `clients` (
  `ide_client` int(11) NOT NULL,
  `name` varchar(90) DEFAULT NULL,
  `last_name` varchar(90) DEFAULT NULL,
  `cellphone` varchar(45) DEFAULT NULL,
  `identification` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `address` varchar(90) DEFAULT NULL,
  `enable` varchar(2) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clients`
--

INSERT INTO `clients` (`ide_client`, `name`, `last_name`, `cellphone`, `identification`, `city`, `address`, `enable`, `avatar`) VALUES
(1, 'Alexander Andres', 'Londoño Espejo', '3122195522', '1038114508', 'MEDELLIN', 'CALLE 65D #92-119 URB SANTA CATALINA', 'S', NULL),
(2, 'hfghfg', 'hfhfg', 'string', '66456', 'string', 'string', 'S', NULL),
(3, 'hfghfg55', 'hfhfg', '4564', '6645556', 'string', 'string', 'S', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orders`
--

CREATE TABLE `orders` (
  `ide_order` int(11) NOT NULL,
  `ide_client` int(11) NOT NULL,
  `ide_state` int(11) DEFAULT NULL,
  `date_order` date DEFAULT NULL,
  `date_payment` date DEFAULT NULL,
  `date_of_delivery` date DEFAULT NULL,
  `observations` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orders_detail`
--

CREATE TABLE `orders_detail` (
  `ide_order_detail` int(11) NOT NULL,
  `ide_order` int(11) NOT NULL,
  `ide_product` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `final_price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `payments`
--

CREATE TABLE `payments` (
  `ide_payment` int(11) NOT NULL,
  `ide_order` int(11) NOT NULL,
  `date_of_delivery` date NOT NULL,
  `value` double DEFAULT NULL,
  `observations` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `payments_type`
--

CREATE TABLE `payments_type` (
  `ide_payment_type` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products`
--

CREATE TABLE `products` (
  `ide_product` int(11) NOT NULL,
  `ide_category` int(11) NOT NULL,
  `name` varchar(150) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `image` varchar(300) DEFAULT NULL,
  `enable` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `states`
--

CREATE TABLE `states` (
  `ide_state` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`ide_category`);

--
-- Indices de la tabla `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`ide_client`);

--
-- Indices de la tabla `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`ide_order`),
  ADD KEY `fk_orders_clients1_idx` (`ide_client`),
  ADD KEY `fk_orders_states1_idx` (`ide_state`);

--
-- Indices de la tabla `orders_detail`
--
ALTER TABLE `orders_detail`
  ADD PRIMARY KEY (`ide_order_detail`,`ide_order`),
  ADD KEY `fk_orders_detail_products1_idx` (`ide_product`),
  ADD KEY `fk_orders_detail_orders1_idx` (`ide_order`);

--
-- Indices de la tabla `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`ide_payment`),
  ADD KEY `fk_payments_orders1_idx` (`ide_order`);

--
-- Indices de la tabla `payments_type`
--
ALTER TABLE `payments_type`
  ADD PRIMARY KEY (`ide_payment_type`);

--
-- Indices de la tabla `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ide_product`),
  ADD KEY `fk_products_category_idx` (`ide_category`);

--
-- Indices de la tabla `states`
--
ALTER TABLE `states`
  ADD PRIMARY KEY (`ide_state`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `category`
--
ALTER TABLE `category`
  MODIFY `ide_category` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `clients`
--
ALTER TABLE `clients`
  MODIFY `ide_client` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `orders`
--
ALTER TABLE `orders`
  MODIFY `ide_order` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `orders_detail`
--
ALTER TABLE `orders_detail`
  MODIFY `ide_order_detail` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `payments`
--
ALTER TABLE `payments`
  MODIFY `ide_payment` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `payments_type`
--
ALTER TABLE `payments_type`
  MODIFY `ide_payment_type` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `products`
--
ALTER TABLE `products`
  MODIFY `ide_product` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `states`
--
ALTER TABLE `states`
  MODIFY `ide_state` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `fk_orders_clients1` FOREIGN KEY (`ide_client`) REFERENCES `clients` (`ide_client`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_orders_states1` FOREIGN KEY (`ide_state`) REFERENCES `states` (`ide_state`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `orders_detail`
--
ALTER TABLE `orders_detail`
  ADD CONSTRAINT `fk_orders_detail_orders1` FOREIGN KEY (`ide_order`) REFERENCES `orders` (`ide_order`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_orders_detail_products1` FOREIGN KEY (`ide_product`) REFERENCES `products` (`ide_product`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `fk_payments_orders1` FOREIGN KEY (`ide_order`) REFERENCES `orders` (`ide_order`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `fk_products_category` FOREIGN KEY (`ide_category`) REFERENCES `category` (`ide_category`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
