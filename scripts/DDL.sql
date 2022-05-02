CREATE DATABASE antoon_core_staging CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE DATABASE antoon_core_prod CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE antoon_core;

CREATE TABLE `discussion`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `content`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `like_count`  int    NOT NULL,
    `member_id`   bigint                                  DEFAULT NULL,
    `webtoon_id`  bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `discussion_like`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `discussion_id` bigint DEFAULT NULL,
    `flag`          bit(1) DEFAULT NULL,
    `user_id`       bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `graph_score_snapshot`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `created_at`    datetime                                DEFAULT NULL,
    `modified_at`   datetime                                DEFAULT NULL,
    `graph_score`   double                                  DEFAULT NULL,
    `graph_status`  varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `snapshot_time` datetime                                DEFAULT NULL,
    `webtoon_id`    bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `recommendation`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `status`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `user_id`     bigint                                  DEFAULT NULL,
    `webtoon_id`  bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `user`
(
    `id`          bigint                                  NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `email`       varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `image_url`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `name`        varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `role`        varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon`
(
    `id`               bigint                                  NOT NULL AUTO_INCREMENT,
    `created_at`       datetime                                DEFAULT NULL,
    `modified_at`      datetime                                DEFAULT NULL,
    `content`          longtext COLLATE utf8mb4_general_ci     NOT NULL,
    `join_user_count`  int                                     NOT NULL,
    `leave_user_count` int                                     NOT NULL,
    `platform`         varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `status`           varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `thumbnail`        varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `title`            varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    `url`              varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_genre`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `category`    varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `webtoon_id`  bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_publish_day`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `day`         varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `webtoon_id`  bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_snapshot`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `created_at`    datetime DEFAULT NULL,
    `modified_at`   datetime DEFAULT NULL,
    `score`         double   DEFAULT NULL,
    `snapshot_time` date     DEFAULT NULL,
    `webtoon_id`    bigint   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

CREATE TABLE `webtoon_writer`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                DEFAULT NULL,
    `modified_at` datetime                                DEFAULT NULL,
    `name`        varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `webtoon_id`  bigint                                  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci