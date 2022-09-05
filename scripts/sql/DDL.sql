CREATE DATABASE antoon_core_staging CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE antoon_core_prod CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE antoon_core_staging;
USE antoon_core_prod;

CREATE TABLE `ant_coin_history`
(
    `id`                bigint NOT NULL AUTO_INCREMENT,
    `created_at`        datetime                                DEFAULT NULL,
    `modified_at`       datetime                                DEFAULT NULL,
    `amount`            bigint                                  DEFAULT NULL,
    `remittance_status` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `remittance_type`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `user_id`           bigint                                  DEFAULT NULL,
    `wallet_id`         bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `ant_coin_wallet`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `status`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `user_id`     bigint                                  DEFAULT NULL,
    `wallet`      bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `discussion`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `content`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `like_count`  int    NOT NULL,
    `user_id`     bigint                                  DEFAULT NULL,
    `webtoon_id`  bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `discussion_like`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `created_at`    datetime DEFAULT NULL,
    `modified_at`   datetime DEFAULT NULL,
    `discussion_id` bigint   DEFAULT NULL,
    `status`        bit(1)   DEFAULT NULL,
    `user_id`       bigint   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `feedback`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `admin_id`    bigint                                  DEFAULT NULL,
    `answer`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `content`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `score`       int                                     DEFAULT NULL,
    `status`      int                                     DEFAULT NULL,
    `user_id`     bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `graph_score_snapshot`
(
    `id`                bigint NOT NULL AUTO_INCREMENT,
    `created_at`        datetime                                DEFAULT NULL,
    `modified_at`       datetime                                DEFAULT NULL,
    `graph_score`       int    NOT NULL,
    `score_gap`         int    NOT NULL,
    `score_gap_percent` double NOT NULL,
    `snapshot_time`     datetime                                DEFAULT NULL,
    `status`            varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `webtoon_id`        bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_status`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `status`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `user_id`     bigint                                  DEFAULT NULL,
    `webtoon_id`  bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_status_count`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime DEFAULT NULL,
    `modified_at` datetime DEFAULT NULL,
    `join_count`  int    NOT NULL,
    `leave_count` int    NOT NULL,
    `webtoon_id`  bigint   DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `i_webtoon_id` (`webtoon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `top_rank`
(
    `id`                      bigint NOT NULL AUTO_INCREMENT,
    `created_at`              datetime                                DEFAULT NULL,
    `modified_at`             datetime                                DEFAULT NULL,
    `graph_score_snapshot_id` bigint                                  DEFAULT NULL,
    `rank_time`               datetime                                DEFAULT NULL,
    `ranking`                 int                                     DEFAULT NULL,
    `reason`                  varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `webtoon_id`              bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `user`
(
    `id`          bigint                                  NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `age`         int                                     NOT NULL,
    `email`       varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `gender`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `image_url`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `name`        varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `role`        varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon`
(
    `id`          bigint                                  NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `content`     longtext COLLATE utf8mb4_general_ci     NOT NULL,
    `platform`    varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `status`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `thumbnail`   varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `title`       varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `webtoon_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_p3gaynlges3v2cwaqfxhdg24c` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_genre`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `created_at`     datetime                                DEFAULT NULL,
    `modified_at`    datetime                                DEFAULT NULL,
    `genre_category` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `webtoon_id`     bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY              `i_webtoon_id` (`webtoon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_publish_day`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `day`         varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `webtoon_id`  bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `i_webtoon_id` (`webtoon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_snapshot`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `created_at`    datetime DEFAULT NULL,
    `modified_at`   datetime DEFAULT NULL,
    `score`         double   DEFAULT NULL,
    `snapshot_time` date     DEFAULT NULL,
    `webtoon_id`    bigint   DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `i_webtoon_id` (`webtoon_id`,`snapshot_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_writer`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `name`        varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `webtoon_id`  bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `i_webtoon_id` (`webtoon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `characters` (
   `id`          bigint NOT NULL AUTO_INCREMENT,
   `created_at`  datetime                                 DEFAULT NULL,
   `modified_at` datetime                                 DEFAULT NULL,
   `coin_amount` bigint                                   DEFAULT NULL,
   `name`        varchar(255) COLLATE utf8mb4_general_ci  DEFAULT NULL,
   `type`        varchar(255) COLLATE utf8mb4_general_ci  DEFAULT NULL,
   `webtoon_id`  bigint                                   DEFAULT NULL,
   `content`     varchar(255) COLLATE utf8mb4_general_ci  DEFAULT NULL,
   `color`       varchar(255) COLLATE utf8mb4_general_ci  DEFAULT NULL,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `character_history` (
   `id`          bigint NOT NULL AUTO_INCREMENT,
   `created_at`  datetime                                 DEFAULT NULL,
   `modified_at` datetime                                 DEFAULT NULL,
   `character_id`  bigint                                  NOT NULL,
   `user_id`     bigint                                   NOT NULL,
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `character_image` (
  `id`          bigint NOT NULL AUTO_INCREMENT,
  `created_at`  datetime                                 DEFAULT NULL,
  `modified_at` datetime                                 DEFAULT NULL,
  `image_url`    varchar(255) COLLATE utf8mb4_general_ci  NOT NULL,
  `type`        varchar(255) COLLATE utf8mb4_general_ci  NOT NULL,
  `character_id`     bigint                               NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci