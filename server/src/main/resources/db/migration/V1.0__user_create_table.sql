--
-- MIT License
--
-- Copyright (c) 2020 engineer365.org
--
-- Permission is hereby granted, free of charge, to any person obtaining a copy
-- of this software and associated documentation files (the "Software"), to deal
-- in the Software without restriction, including without limitation the rights
-- to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
-- copies of the Software, and to permit persons to whom the Software is
-- furnished to do so, subject to the following conditions:
--
-- The above copyright notice and this permission notice shall be included in all
-- copies or substantial portions of the Software.
--
-- THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
-- IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
-- FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
-- AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
-- LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
-- OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
-- SOFTWARE.

-- 用户模块的MySQL数据库初始化SQL

SET FOREIGN_KEY_CHECKS=0;

-- DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
    `id`                 CHAR(22)     CHARACTER SET latin1   NOT NULL,
    `created_at`         DATETIME(3)                         NOT NULL,
    `version`            INT                                 NOT NULL,
    `modified_at`        DATETIME(3)                         NOT NULL,

    `user_id`            CHAR(22)     CHARACTER SET latin1   NOT NULL,

    `type`               VARCHAR(16)  CHARACTER SET latin1   NOT NULL,
    `credential`         VARCHAR(64)   NOT NULL,

    `password`           VARCHAR(64)  CHARACTER SET latin1   NOT NULL,

    PRIMARY KEY (`id`)
) ENGINE=InnoDB, DEFAULT CHARACTER SET utf8mb4;

CREATE INDEX `idx_user_account_user_id` ON `user_account`(`user_id`(8));
CREATE UNIQUE INDEX `idx_user_account_biz_key` ON `user_account`(`credential`, `type`);


-- DROP TABLE IF EXISTS `user_user`;
CREATE TABLE `user_user` (
    `id`                 CHAR(22)     CHARACTER SET latin1   NOT NULL,
    `created_at`         DATETIME(3)                         NOT NULL,
    `version`            INT                                 NOT NULL,
    `modified_at`        DATETIME(3)                         NOT NULL,

    `name`               VARCHAR(32)   NOT NULL,
    `full_name`          VARCHAR(64)   NOT NULL,
    `primary_account_id` CHAR(22),
    `is_root`            BOOLEAN DEFAULT FALSE               NOT NULL,

    PRIMARY KEY (`id`)
) ENGINE=InnoDB, DEFAULT CHARACTER SET utf8mb4;

CREATE UNIQUE INDEX `idx_user_user_name` ON `user_user`(`name`);

