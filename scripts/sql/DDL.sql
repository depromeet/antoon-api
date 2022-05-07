CREATE DATABASE antoon_core_staging CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE antoon_core_prod CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE antoon_core_staging;
USE antoon_core_prod;

CREATE TABLE `discussion` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `created_at` datetime DEFAULT NULL,
                              `modified_at` datetime DEFAULT NULL,
                              `content` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `like_count` int NOT NULL,
                              `user_id` bigint DEFAULT NULL,
                              `webtoon_id` bigint DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `discussion_like` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `discussion_id` bigint DEFAULT NULL,
                                   `status` bit(1) DEFAULT NULL,
                                   `user_id` bigint DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `graph_score_snapshot` (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `created_at` datetime DEFAULT NULL,
                                        `modified_at` datetime DEFAULT NULL,
                                        `graph_score` double DEFAULT NULL,
                                        `score_gap` double DEFAULT NULL,
                                        `snapshot_time` datetime DEFAULT NULL,
                                        `status` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                        `webtoon_id` bigint DEFAULT NULL,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1159 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `recommendation` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `created_at` datetime DEFAULT NULL,
                                  `modified_at` datetime DEFAULT NULL,
                                  `status` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `user_id` bigint DEFAULT NULL,
                                  `webtoon_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `created_at` datetime DEFAULT NULL,
                        `modified_at` datetime DEFAULT NULL,
                        `age` int NOT NULL,
                        `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                        `image_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                        `role` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `created_at` datetime DEFAULT NULL,
                           `modified_at` datetime DEFAULT NULL,
                           `content` longtext COLLATE utf8mb4_general_ci NOT NULL,
                           `platform` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                           `status` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `thumbnail` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                           `title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                           `webtoon_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_genre` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `created_at` datetime DEFAULT NULL,
                                 `modified_at` datetime DEFAULT NULL,
                                 `genre_category` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                 `webtoon_id` bigint DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_publish_day` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `created_at` datetime DEFAULT NULL,
                                       `modified_at` datetime DEFAULT NULL,
                                       `day` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                       `webtoon_id` bigint DEFAULT NULL,
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_snapshot` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `created_at` datetime DEFAULT NULL,
                                    `modified_at` datetime DEFAULT NULL,
                                    `score` double DEFAULT NULL,
                                    `snapshot_time` date DEFAULT NULL,
                                    `webtoon_id` bigint DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_writer` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `created_at` datetime DEFAULT NULL,
                                  `modified_at` datetime DEFAULT NULL,
                                  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                  `webtoon_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `top_rank` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `created_at` datetime DEFAULT NULL,
                            `modified_at` datetime DEFAULT NULL,
                            `graph_score_snapshot_id` bigint DEFAULT NULL,
                            `rank_time` datetime DEFAULT NULL,
                            `ranking` int DEFAULT NULL,
                            `reason` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                            `webtoon_id` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci