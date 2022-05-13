-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 13 mai 2022 à 08:26
-- Version du serveur :  8.0.21
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `kmn_apptodolist_javafx`
--
CREATE DATABASE IF NOT EXISTS `kmn_apptodolist_javafx` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `kmn_apptodolist_javafx`;

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

DROP TABLE IF EXISTS `compte`;
CREATE TABLE IF NOT EXISTS `compte` (
  `id_compte` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mdp` varchar(255) NOT NULL,
  PRIMARY KEY (`id_compte`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`id_compte`, `nom`, `prenom`, `email`, `mdp`) VALUES
(1, 'Maignan', 'Kyllian', 'k.maignan@lprs.fr', 'lprs');

-- --------------------------------------------------------

--
-- Structure de la table `etat`
--

DROP TABLE IF EXISTS `etat`;
CREATE TABLE IF NOT EXISTS `etat` (
  `id_etat` int NOT NULL AUTO_INCREMENT,
  `etat` varchar(255) NOT NULL,
  PRIMARY KEY (`id_etat`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `etat`
--

INSERT INTO `etat` (`id_etat`, `etat`) VALUES
(1, 'Fini');

-- --------------------------------------------------------

--
-- Structure de la table `gere`
--

DROP TABLE IF EXISTS `gere`;
CREATE TABLE IF NOT EXISTS `gere` (
  `ref_tache` int NOT NULL,
  `ref_compte` int NOT NULL,
  `accepte` tinyint(1) NOT NULL,
  PRIMARY KEY (`ref_tache`,`ref_compte`),
  KEY `fk_gere_compte` (`ref_compte`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `infoliste`
-- (Voir ci-dessous la vue réelle)
--
DROP VIEW IF EXISTS `infoliste`;
CREATE TABLE IF NOT EXISTS `infoliste` (
`id_liste` int
,`titre` varchar(255)
,`compteTache` bigint
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `infotache`
-- (Voir ci-dessous la vue réelle)
--
DROP VIEW IF EXISTS `infotache`;
CREATE TABLE IF NOT EXISTS `infotache` (
`id_tache` int
,`ref_liste` int
,`nom` varchar(255)
,`description` varchar(255)
,`difficulte` varchar(255)
,`date_debut` date
,`date_fin` date
,`date_butoir` date
,`type` varchar(255)
,`etat` varchar(255)
,`nomgerant` varchar(255)
,`prenomgerant` varchar(255)
);

-- --------------------------------------------------------

--
-- Structure de la table `liste`
--

DROP TABLE IF EXISTS `liste`;
CREATE TABLE IF NOT EXISTS `liste` (
  `id_liste` int NOT NULL AUTO_INCREMENT,
  `titre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_liste`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `tache`
--

DROP TABLE IF EXISTS `tache`;
CREATE TABLE IF NOT EXISTS `tache` (
  `id_tache` int NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `difficulte` varchar(255) NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  `date_butoir` date NOT NULL,
  `ref_type` int DEFAULT NULL,
  `ref_etat` int DEFAULT NULL,
  `ref_liste` int DEFAULT NULL,
  PRIMARY KEY (`id_tache`),
  KEY `fk_tache_type` (`ref_type`),
  KEY `fk_tache_etat` (`ref_etat`),
  KEY `fk_tache_liste` (`ref_liste`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

--
-- Déclencheurs `tache`
--
DROP TRIGGER IF EXISTS `deleteListeTache`;
DELIMITER $$
CREATE TRIGGER `deleteListeTache` AFTER DELETE ON `tache` FOR EACH ROW BEGIN
DECLARE compteTache INT;
SET compteTache = (SELECT COUNT(*) as compteTache FROM tache WHERE tache.ref_liste = old.ref_liste);
IF compteTache <= 0
	THEN DELETE FROM liste WHERE liste.id_liste = old.ref_liste;
END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `type`
--

DROP TABLE IF EXISTS `type`;
CREATE TABLE IF NOT EXISTS `type` (
  `id_type` int NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) NOT NULL,
  `ref_type` int DEFAULT NULL,
  PRIMARY KEY (`id_type`),
  KEY `fk_type_type` (`ref_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `type`
--

INSERT INTO `type` (`id_type`, `libelle`, `ref_type`) VALUES
(1, 'Survie', NULL);

-- --------------------------------------------------------

--
-- Structure de la vue `infoliste`
--
DROP TABLE IF EXISTS `infoliste`;

DROP VIEW IF EXISTS `infoliste`;
CREATE ALGORITHM=UNDEFINED DEFINER=`apptodolist`@`%` SQL SECURITY DEFINER VIEW `infoliste`  AS  select `liste`.`id_liste` AS `id_liste`,`liste`.`titre` AS `titre`,count(distinct `tache`.`ref_liste`) AS `compteTache` from (`liste` join `tache`) ;

-- --------------------------------------------------------

--
-- Structure de la vue `infotache`
--
DROP TABLE IF EXISTS `infotache`;

DROP VIEW IF EXISTS `infotache`;
CREATE ALGORITHM=UNDEFINED DEFINER=`apptodolist`@`%` SQL SECURITY DEFINER VIEW `infotache`  AS  select `tache`.`id_tache` AS `id_tache`,`tache`.`ref_liste` AS `ref_liste`,`tache`.`libelle` AS `nom`,`tache`.`description` AS `description`,`tache`.`difficulte` AS `difficulte`,`tache`.`date_debut` AS `date_debut`,`tache`.`date_fin` AS `date_fin`,`tache`.`date_butoir` AS `date_butoir`,`type`.`libelle` AS `type`,`etat`.`etat` AS `etat`,`compte`.`nom` AS `nomgerant`,`compte`.`prenom` AS `prenomgerant` from ((((`tache` join `gere`) join `etat`) join `compte`) join `type`) where ((`gere`.`ref_tache` = `tache`.`id_tache`) and (`gere`.`ref_compte` = `compte`.`id_compte`) and (`etat`.`id_etat` = `tache`.`ref_etat`) and (`type`.`id_type` = `tache`.`ref_type`)) ;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `gere`
--
ALTER TABLE `gere`
  ADD CONSTRAINT `fk_gere_compte` FOREIGN KEY (`ref_compte`) REFERENCES `compte` (`id_compte`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_gere_tache` FOREIGN KEY (`ref_tache`) REFERENCES `tache` (`id_tache`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `tache`
--
ALTER TABLE `tache`
  ADD CONSTRAINT `fk_tache_etat` FOREIGN KEY (`ref_etat`) REFERENCES `etat` (`id_etat`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_tache_liste` FOREIGN KEY (`ref_liste`) REFERENCES `liste` (`id_liste`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_tache_type` FOREIGN KEY (`ref_type`) REFERENCES `type` (`id_type`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
