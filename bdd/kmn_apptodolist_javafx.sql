-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 10 mai 2022 à 07:42
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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

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
  `id_etat` int(11) NOT NULL AUTO_INCREMENT,
  `etat` varchar(255) NOT NULL,
  PRIMARY KEY (`id_etat`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

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
  `ref_tache` int(11) NOT NULL,
  `ref_compte` int(11) NOT NULL,
  `accepte` tinyint(1) NOT NULL,
  PRIMARY KEY (`ref_tache`,`ref_compte`),
  KEY `fk_gere_compte` (`ref_compte`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `gere`
--

INSERT INTO `gere` (`ref_tache`, `ref_compte`, `accepte`) VALUES
(1, 1, 1);

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
`ref_liste` int(11)
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
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `liste`
--

INSERT INTO `liste` (`id_liste`, `titre`) VALUES
(1, 'Ma Première Liste'),
(3, 'Deus');

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
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `tache`
--

INSERT INTO `tache` (`id_tache`, `libelle`, `description`, `difficulte`, `date_debut`, `date_fin`, `date_butoir`, `ref_type`, `ref_etat`, `ref_liste`) VALUES
(1, 'Manger', 'Il faut manger.', 'Très Dur', '2022-05-06', '2022-05-07', '2022-05-14', 1, 1, 1),
(2, 'Dormir', 'Il faut dormir.', 'Moyen', '2022-05-08', '2022-05-10', '2022-05-11', 1, 1, NULL);

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
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

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
CREATE ALGORITHM=UNDEFINED DEFINER=`apptodolist`@`%` SQL SECURITY DEFINER VIEW `infoliste`  AS SELECT `liste`.`id_liste` AS `id_liste`, `liste`.`titre` AS `titre`, count(distinct `tache`.`ref_liste`) AS `compteTache` FROM (`liste` join `tache`) ;

-- --------------------------------------------------------

--
-- Structure de la vue `infotache`
--
DROP TABLE IF EXISTS `infotache`;

DROP VIEW IF EXISTS `infotache`;
CREATE ALGORITHM=UNDEFINED DEFINER=`apptodolist`@`%` SQL SECURITY DEFINER VIEW `infotache`  AS SELECT `tache`.`ref_liste` AS `ref_liste`, `tache`.`libelle` AS `nom`, `tache`.`description` AS `description`, `tache`.`difficulte` AS `difficulte`, `tache`.`date_debut` AS `date_debut`, `tache`.`date_fin` AS `date_fin`, `tache`.`date_butoir` AS `date_butoir`, `type`.`libelle` AS `type`, `etat`.`etat` AS `etat`, `compte`.`nom` AS `nomgerant`, `compte`.`prenom` AS `prenomgerant` FROM ((((`tache` join `gere`) join `etat`) join `compte`) join `type`) WHERE ((`gere`.`ref_tache` = `tache`.`id_tache`) AND (`gere`.`ref_compte` = `compte`.`id_compte`) AND (`etat`.`id_etat` = `tache`.`ref_etat`) AND (`type`.`id_type` = `tache`.`ref_type`)) ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
