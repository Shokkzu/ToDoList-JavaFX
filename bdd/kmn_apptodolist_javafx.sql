-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 14 mai 2022 à 15:07
-- Version du serveur : 5.7.36
-- Version de PHP : 7.4.26

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
  `id_compte` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mdp` varchar(255) NOT NULL,
  PRIMARY KEY (`id_compte`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `etat`
--

DROP TABLE IF EXISTS `etat`;
CREATE TABLE IF NOT EXISTS `etat` (
  `id_etat` int(11) NOT NULL AUTO_INCREMENT,
  `etat` varchar(255) NOT NULL,
  PRIMARY KEY (`id_etat`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `etat`
--

INSERT INTO `etat` (`id_etat`, `etat`) VALUES
(1, 'Fini'),
(2, 'En attente'),
(3, 'Pas commencé'),
(4, 'En pause'),
(5, 'Abandonnée');

-- --------------------------------------------------------

--
-- Structure de la table `gere`
--

DROP TABLE IF EXISTS `gere`;
CREATE TABLE IF NOT EXISTS `gere` (
  `ref_tache` int(11) NOT NULL,
  `ref_compte` int(11) NOT NULL,
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
`id_liste` bigint(11)
,`titre` varchar(255)
,`compteTache` bigint(21)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `infotache`
-- (Voir ci-dessous la vue réelle)
--
DROP VIEW IF EXISTS `infotache`;
CREATE TABLE IF NOT EXISTS `infotache` (
`nomListe` varchar(255)
,`id_tache` int(11)
,`ref_compte` int(11)
,`ref_liste` int(11)
,`accepte` tinyint(1)
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
  `id_liste` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_liste`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `tache`
--

DROP TABLE IF EXISTS `tache`;
CREATE TABLE IF NOT EXISTS `tache` (
  `id_tache` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `difficulte` varchar(255) NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  `date_butoir` date NOT NULL,
  `ref_type` int(11) DEFAULT NULL,
  `ref_etat` int(11) DEFAULT NULL,
  `ref_liste` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_tache`),
  KEY `fk_tache_type` (`ref_type`),
  KEY `fk_tache_etat` (`ref_etat`),
  KEY `fk_tache_liste` (`ref_liste`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

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
  `id_type` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) NOT NULL,
  `ref_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_type`),
  KEY `fk_type_type` (`ref_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `type`
--

INSERT INTO `type` (`id_type`, `libelle`, `ref_type`) VALUES
(1, 'Survie', NULL),
(2, 'Managériale', NULL),
(3, 'Projet', NULL);

-- --------------------------------------------------------

--
-- Structure de la vue `infoliste`
--
DROP TABLE IF EXISTS `infoliste`;

DROP VIEW IF EXISTS `infoliste`;
CREATE ALGORITHM=UNDEFINED DEFINER=`apptodolist`@`%` SQL SECURITY DEFINER VIEW `infoliste`  AS SELECT `liste`.`id_liste` AS `id_liste`, `liste`.`titre` AS `titre`, count(distinct `tache`.`ref_liste`) AS `compteTache` FROM (`liste` join `tache`) ;

-- --------------------------------------------------------

--
-- Structure de la vue `infotache`
--
DROP TABLE IF EXISTS `infotache`;

DROP VIEW IF EXISTS `infotache`;
CREATE ALGORITHM=UNDEFINED DEFINER=`apptodolist`@`%` SQL SECURITY DEFINER VIEW `infotache`  AS SELECT `liste`.`titre` AS `nomListe`, `tache`.`id_tache` AS `id_tache`, `gere`.`ref_compte` AS `ref_compte`, `tache`.`ref_liste` AS `ref_liste`, `gere`.`accepte` AS `accepte`, `tache`.`libelle` AS `nom`, `tache`.`description` AS `description`, `tache`.`difficulte` AS `difficulte`, `tache`.`date_debut` AS `date_debut`, `tache`.`date_fin` AS `date_fin`, `tache`.`date_butoir` AS `date_butoir`, `type`.`libelle` AS `type`, `etat`.`etat` AS `etat`, `compte`.`nom` AS `nomgerant`, `compte`.`prenom` AS `prenomgerant` FROM (((((`tache` join `gere`) join `etat`) join `compte`) join `type`) join `liste`) WHERE ((`gere`.`ref_tache` = `tache`.`id_tache`) AND (`gere`.`ref_compte` = `compte`.`id_compte`) AND (`etat`.`id_etat` = `tache`.`ref_etat`) AND (`type`.`id_type` = `tache`.`ref_type`) AND (`liste`.`id_liste` = `tache`.`ref_liste`)) ;

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
