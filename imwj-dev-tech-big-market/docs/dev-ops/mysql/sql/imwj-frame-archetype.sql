/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80300
 Source Host           : localhost:3306
 Source Schema         : big_market

 Target Server Type    : MySQL
 Target Server Version : 80300
 File Encoding         : 65001

 Date: 11/07/2024 16:27:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for award
-- ----------------------------
DROP TABLE IF EXISTS `award`;
CREATE TABLE `award`  (
                          `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                          `award_id` int NOT NULL COMMENT '抽奖奖品ID - 内部流转使用',
                          `award_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品对接标识 - 每一个都是一个对应的发奖策略',
                          `award_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品配置信息',
                          `award_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品内容描述',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of award
-- ----------------------------
INSERT INTO `award` VALUES (1, 101, 'user_credit_random', '1,100', '用户积分【优先透彻规则范围，如果没有则走配置】', '2023-12-09 11:07:06', '2023-12-09 11:21:31');
INSERT INTO `award` VALUES (2, 102, 'openai_use_count', '5', 'OpenAI 增加使用次数', '2023-12-09 11:07:06', '2023-12-09 11:12:59');
INSERT INTO `award` VALUES (3, 103, 'openai_use_count', '10', 'OpenAI 增加使用次数', '2023-12-09 11:07:06', '2023-12-09 11:12:59');
INSERT INTO `award` VALUES (4, 104, 'openai_use_count', '20', 'OpenAI 增加使用次数', '2023-12-09 11:07:06', '2023-12-09 11:12:58');
INSERT INTO `award` VALUES (5, 105, 'openai_model', 'gpt-4', 'OpenAI 增加模型', '2023-12-09 11:07:06', '2023-12-09 11:12:01');
INSERT INTO `award` VALUES (6, 106, 'openai_model', 'dall-e-2', 'OpenAI 增加模型', '2023-12-09 11:07:06', '2023-12-09 11:12:08');
INSERT INTO `award` VALUES (7, 107, 'openai_model', 'dall-e-3', 'OpenAI 增加模型', '2023-12-09 11:07:06', '2023-12-09 11:12:10');
INSERT INTO `award` VALUES (8, 108, 'openai_use_count', '100', 'OpenAI 增加使用次数', '2023-12-09 11:07:06', '2023-12-09 11:12:55');
INSERT INTO `award` VALUES (9, 109, 'openai_model', 'gpt-4,dall-e-2,dall-e-3', 'OpenAI 增加模型', '2023-12-09 11:07:06', '2023-12-09 11:12:39');
INSERT INTO `award` VALUES (10, 100, 'user_credit_blacklist', '1', '黑名单积分', '2024-01-06 12:30:40', '2024-01-06 12:30:46');

-- ----------------------------
-- Table structure for raffle_activity
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity`;
CREATE TABLE `raffle_activity`  (
                                    `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                    `activity_id` bigint NOT NULL COMMENT '活动ID',
                                    `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
                                    `activity_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动描述',
                                    `begin_date_time` datetime NOT NULL COMMENT '开始时间',
                                    `end_date_time` datetime NOT NULL COMMENT '结束时间',
                                    `stock_count` int NOT NULL COMMENT '库存总量',
                                    `stock_count_surplus` int NOT NULL COMMENT '剩余库存',
                                    `activity_count_id` bigint NOT NULL COMMENT '活动参与次数配置',
                                    `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
                                    `state` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动状态',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `uq_activity_id`(`activity_id` ASC) USING BTREE,
                                    INDEX `idx_begin_date_time`(`begin_date_time` ASC) USING BTREE,
                                    INDEX `idx_end_date_time`(`end_date_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_account
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account`;
CREATE TABLE `raffle_activity_account`  (
                                            `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                            `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
                                            `activity_id` bigint NOT NULL COMMENT '活动ID',
                                            `total_count` int NOT NULL COMMENT '总次数',
                                            `total_count_surplus` int NOT NULL COMMENT '总次数-剩余',
                                            `day_count` int NOT NULL COMMENT '日次数',
                                            `day_count_surplus` int NOT NULL COMMENT '日次数-剩余',
                                            `month_count` int NOT NULL COMMENT '月次数',
                                            `month_count_surplus` int NOT NULL COMMENT '月次数-剩余',
                                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            UNIQUE INDEX `uq_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_account_flow
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_flow`;
CREATE TABLE `raffle_activity_account_flow`  (
                                                 `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                                 `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
                                                 `activity_id` bigint NOT NULL COMMENT '活动ID',
                                                 `total_count` int NOT NULL COMMENT '总次数',
                                                 `day_count` int NOT NULL COMMENT '日次数',
                                                 `month_count` int NOT NULL COMMENT '月次数',
                                                 `flow_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '流水ID - 生成的唯一ID',
                                                 `flow_channel` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'activity' COMMENT '流水渠道（activity-活动领取、sale-购买、redeem-兑换、free-免费赠送）',
                                                 `biz_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务ID（外部透传，活动ID、订单ID）',
                                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                                 PRIMARY KEY (`id`) USING BTREE,
                                                 UNIQUE INDEX `uq_flow_id`(`flow_id` ASC) USING BTREE,
                                                 UNIQUE INDEX `uq_biz_id`(`biz_id` ASC) USING BTREE,
                                                 INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动账户流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_flow
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_count
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_count`;
CREATE TABLE `raffle_activity_count`  (
                                          `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                          `activity_count_id` bigint NOT NULL COMMENT '活动次数编号',
                                          `total_count` int NOT NULL COMMENT '总次数',
                                          `day_count` int NOT NULL COMMENT '日次数',
                                          `month_count` int NOT NULL COMMENT '月次数',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          PRIMARY KEY (`id`) USING BTREE,
                                          UNIQUE INDEX `uq_activity_count_id`(`activity_count_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动次数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_count
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_order
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order`;
CREATE TABLE `raffle_activity_order`  (
                                          `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                          `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
                                          `activity_id` bigint NOT NULL COMMENT '活动ID',
                                          `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
                                          `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
                                          `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
                                          `order_time` datetime NOT NULL COMMENT '下单时间',
                                          `state` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单状态（not_used、used、expire）',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                          PRIMARY KEY (`id`) USING BTREE,
                                          UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
                                          INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC, `state` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order
-- ----------------------------

-- ----------------------------
-- Table structure for rule_tree
-- ----------------------------
DROP TABLE IF EXISTS `rule_tree`;
CREATE TABLE `rule_tree`  (
                              `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                              `tree_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则树ID',
                              `tree_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则树名称',
                              `tree_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规则树描述',
                              `tree_node_rule_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则树根入口规则',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `uq_tree_id`(`tree_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rule_tree
-- ----------------------------
INSERT INTO `rule_tree` VALUES (1, 'tree_lock_1', '规则树', '规则树', 'rule_lock', '2024-01-27 10:01:59', '2024-02-15 07:49:59');
INSERT INTO `rule_tree` VALUES (2, 'tree_luck_award', '规则树-兜底奖励', '规则树-兜底奖励', 'rule_stock', '2024-02-15 07:35:06', '2024-02-15 07:50:20');
INSERT INTO `rule_tree` VALUES (3, 'tree_lock_2', '规则树', '规则树', 'rule_lock', '2024-01-27 10:01:59', '2024-02-15 07:49:59');

-- ----------------------------
-- Table structure for rule_tree_node
-- ----------------------------
DROP TABLE IF EXISTS `rule_tree_node`;
CREATE TABLE `rule_tree_node`  (
                                   `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                   `tree_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则树ID',
                                   `rule_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则Key',
                                   `rule_desc` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则描述',
                                   `rule_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规则比值',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rule_tree_node
-- ----------------------------
INSERT INTO `rule_tree_node` VALUES (1, 'tree_lock_1', 'rule_lock', '限定用户已完成N次抽奖后解锁', '1', '2024-01-27 10:03:09', '2024-02-15 07:50:57');
INSERT INTO `rule_tree_node` VALUES (2, 'tree_lock_1', 'rule_luck_award', '兜底奖品随机积分', '101:1,100', '2024-01-27 10:03:09', '2024-02-15 07:51:00');
INSERT INTO `rule_tree_node` VALUES (3, 'tree_lock_1', 'rule_stock', '库存扣减规则', NULL, '2024-01-27 10:04:43', '2024-02-15 07:51:02');
INSERT INTO `rule_tree_node` VALUES (4, 'tree_luck_award', 'rule_stock', '库存扣减规则', NULL, '2024-02-15 07:35:55', '2024-02-15 07:39:19');
INSERT INTO `rule_tree_node` VALUES (5, 'tree_luck_award', 'rule_luck_award', '兜底奖品随机积分', '101:1,100', '2024-02-15 07:35:55', '2024-02-15 07:39:23');
INSERT INTO `rule_tree_node` VALUES (6, 'tree_lock_2', 'rule_lock', '限定用户已完成N次抽奖后解锁', '2', '2024-01-27 10:03:09', '2024-02-15 07:52:20');
INSERT INTO `rule_tree_node` VALUES (7, 'tree_lock_2', 'rule_luck_award', '兜底奖品随机积分', '101:1,100', '2024-01-27 10:03:09', '2024-02-08 19:59:43');
INSERT INTO `rule_tree_node` VALUES (8, 'tree_lock_2', 'rule_stock', '库存扣减规则', NULL, '2024-01-27 10:04:43', '2024-02-03 10:40:21');

-- ----------------------------
-- Table structure for rule_tree_node_line
-- ----------------------------
DROP TABLE IF EXISTS `rule_tree_node_line`;
CREATE TABLE `rule_tree_node_line`  (
                                        `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                        `tree_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则树ID',
                                        `rule_node_from` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则Key节点 From',
                                        `rule_node_to` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则Key节点 To',
                                        `rule_limit_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围];',
                                        `rule_limit_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '限定值（到下个节点）',
                                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rule_tree_node_line
-- ----------------------------
INSERT INTO `rule_tree_node_line` VALUES (1, 'tree_lock_1', 'rule_lock', 'rule_stock', 'EQUAL', 'ALLOW', '0000-00-00 00:00:00', '2024-02-15 07:55:08');
INSERT INTO `rule_tree_node_line` VALUES (2, 'tree_lock_1', 'rule_lock', 'rule_luck_award', 'EQUAL', 'TAKE_OVER', '0000-00-00 00:00:00', '2024-02-15 07:55:11');
INSERT INTO `rule_tree_node_line` VALUES (3, 'tree_lock_1', 'rule_stock', 'rule_luck_award', 'EQUAL', 'ALLOW', '0000-00-00 00:00:00', '2024-02-15 07:55:13');
INSERT INTO `rule_tree_node_line` VALUES (4, 'tree_luck_award', 'rule_stock', 'rule_luck_award', 'EQUAL', 'ALLOW', '2024-02-15 07:37:31', '2024-02-15 07:39:28');
INSERT INTO `rule_tree_node_line` VALUES (5, 'tree_lock_2', 'rule_lock', 'rule_stock', 'EQUAL', 'ALLOW', '0000-00-00 00:00:00', '2024-02-15 07:55:08');
INSERT INTO `rule_tree_node_line` VALUES (6, 'tree_lock_2', 'rule_lock', 'rule_luck_award', 'EQUAL', 'TAKE_OVER', '0000-00-00 00:00:00', '2024-02-15 07:55:11');
INSERT INTO `rule_tree_node_line` VALUES (7, 'tree_lock_2', 'rule_stock', 'rule_luck_award', 'EQUAL', 'ALLOW', '0000-00-00 00:00:00', '2024-02-15 07:55:13');

-- ----------------------------
-- Table structure for strategy
-- ----------------------------
DROP TABLE IF EXISTS `strategy`;
CREATE TABLE `strategy`  (
                             `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                             `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
                             `strategy_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖策略描述',
                             `rule_models` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规则模型，rule配置的模型同步到此表，便于使用',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `idx_strategy_id`(`strategy_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of strategy
-- ----------------------------
INSERT INTO `strategy` VALUES (1, 100001, '抽奖策略', 'rule_blacklist,rule_weight', '2023-12-09 09:37:19', '2024-01-20 11:39:23');
INSERT INTO `strategy` VALUES (2, 100003, '抽奖策略-验证lock', 'rule_blacklist', '2024-01-13 10:34:06', '2024-01-20 15:03:19');
INSERT INTO `strategy` VALUES (3, 100002, '抽奖策略-非完整1概率', NULL, '2023-12-09 09:37:19', '2024-02-03 10:14:17');
INSERT INTO `strategy` VALUES (4, 100004, '抽奖策略-随机抽奖', NULL, '2023-12-09 09:37:19', '2024-01-20 19:21:03');
INSERT INTO `strategy` VALUES (5, 100005, '抽奖策略-测试概率计算', NULL, '2023-12-09 09:37:19', '2024-01-21 21:54:58');
INSERT INTO `strategy` VALUES (6, 100006, '抽奖策略-规则树', NULL, '2024-02-03 09:53:40', '2024-02-03 09:53:40');

-- ----------------------------
-- Table structure for strategy_award
-- ----------------------------
DROP TABLE IF EXISTS `strategy_award`;
CREATE TABLE `strategy_award`  (
                                   `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                   `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
                                   `award_id` int NOT NULL COMMENT '抽奖奖品ID - 内部流转使用',
                                   `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖奖品标题',
                                   `award_subtitle` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '抽奖奖品副标题',
                                   `award_count` int NOT NULL DEFAULT 0 COMMENT '奖品库存总量',
                                   `award_count_surplus` int NOT NULL DEFAULT 0 COMMENT '奖品库存剩余',
                                   `award_rate` decimal(6, 4) NOT NULL COMMENT '奖品中奖概率',
                                   `rule_models` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规则模型，rule配置的模型同步到此表，便于使用',
                                   `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `idx_strategy_id_award_id`(`strategy_id` ASC, `award_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of strategy_award
-- ----------------------------
INSERT INTO `strategy_award` VALUES (1, 100001, 101, '随机积分', NULL, 80000, 80000, 0.3000, 'tree_luck_award', 1, '2023-12-09 09:38:31', '2024-02-15 07:42:09');
INSERT INTO `strategy_award` VALUES (2, 100001, 102, '5次使用', NULL, 10000, 10000, 0.2000, 'tree_luck_award', 2, '2023-12-09 09:39:18', '2024-02-15 07:42:11');
INSERT INTO `strategy_award` VALUES (3, 100001, 103, '10次使用', NULL, 5000, 5000, 0.2000, 'tree_luck_award', 3, '2023-12-09 09:42:36', '2024-02-15 07:42:12');
INSERT INTO `strategy_award` VALUES (4, 100001, 104, '20次使用', NULL, 4000, 4000, 0.1000, 'tree_luck_award', 4, '2023-12-09 09:43:15', '2024-02-15 07:42:12');
INSERT INTO `strategy_award` VALUES (5, 100001, 105, '增加gpt-4对话模型', NULL, 600, 600, 0.1000, 'tree_luck_award', 5, '2023-12-09 09:43:47', '2024-02-15 07:42:13');
INSERT INTO `strategy_award` VALUES (6, 100001, 106, '增加dall-e-2画图模型', NULL, 200, 200, 0.0500, 'tree_luck_award', 6, '2023-12-09 09:44:20', '2024-02-15 07:42:14');
INSERT INTO `strategy_award` VALUES (7, 100001, 107, '增加dall-e-3画图模型', '抽奖1次后解锁', 200, 200, 0.0400, 'tree_luck_award', 7, '2023-12-09 09:45:38', '2024-02-15 07:42:17');
INSERT INTO `strategy_award` VALUES (8, 100001, 108, '增加100次使用', '抽奖2次后解锁', 199, 199, 0.0099, 'tree_luck_award', 8, '2023-12-09 09:46:02', '2024-02-15 07:42:21');
INSERT INTO `strategy_award` VALUES (9, 100001, 109, '解锁全部模型', '抽奖6次后解锁', 1, 1, 0.0001, 'tree_luck_award', 9, '2023-12-09 09:46:39', '2024-02-15 07:42:26');
INSERT INTO `strategy_award` VALUES (10, 100002, 101, '随机积分', NULL, 1, 1, 0.5000, 'tree_luck_award', 1, '2023-12-09 09:46:39', '2024-02-15 07:42:29');
INSERT INTO `strategy_award` VALUES (11, 100002, 102, '5次使用', NULL, 1, 1, 0.1000, 'tree_luck_award', 2, '2023-12-09 09:46:39', '2024-02-15 07:42:32');
INSERT INTO `strategy_award` VALUES (12, 100002, 106, '增加dall-e-2画图模型', NULL, 1, 1, 0.0100, 'tree_luck_award', 3, '2023-12-09 09:46:39', '2024-02-15 07:42:35');
INSERT INTO `strategy_award` VALUES (13, 100003, 107, '增加dall-e-3画图模型', '抽奖1次后解锁', 200, 200, 0.0400, 'tree_luck_award', 7, '2023-12-09 09:45:38', '2024-02-15 07:42:38');
INSERT INTO `strategy_award` VALUES (14, 100003, 108, '增加100次使用', '抽奖2次后解锁', 199, 199, 0.0099, 'tree_luck_award', 8, '2023-12-09 09:46:02', '2024-02-15 07:42:41');
INSERT INTO `strategy_award` VALUES (15, 100003, 109, '解锁全部模型', '抽奖6次后解锁', 1, 1, 0.0001, 'tree_luck_award', 9, '2023-12-09 09:46:39', '2024-02-15 07:42:44');
INSERT INTO `strategy_award` VALUES (16, 100004, 109, '解锁全部模型', '抽奖6次后解锁', 1, 1, 1.0000, 'tree_luck_award', 9, '2023-12-09 09:46:39', '2024-02-15 07:42:46');
INSERT INTO `strategy_award` VALUES (17, 100005, 101, '随机积分', NULL, 80000, 80000, 0.0300, 'tree_luck_award', 1, '2023-12-09 09:38:31', '2024-02-15 07:42:47');
INSERT INTO `strategy_award` VALUES (18, 100005, 102, '随机积分', NULL, 80000, 80000, 0.0300, 'tree_luck_award', 1, '2023-12-09 09:38:31', '2024-02-15 07:42:48');
INSERT INTO `strategy_award` VALUES (19, 100005, 103, '随机积分', NULL, 80000, 80000, 0.0300, 'tree_luck_award', 1, '2023-12-09 09:38:31', '2024-02-15 07:42:50');
INSERT INTO `strategy_award` VALUES (20, 100005, 104, '随机积分', NULL, 80000, 80000, 0.0300, 'tree_luck_award', 1, '2023-12-09 09:38:31', '2024-02-15 07:42:51');
INSERT INTO `strategy_award` VALUES (21, 100005, 105, '随机积分', NULL, 80000, 80000, 0.0010, 'tree_luck_award', 1, '2023-12-09 09:38:31', '2024-02-15 07:42:52');
INSERT INTO `strategy_award` VALUES (22, 100006, 101, '随机积分', NULL, 100, 81, 0.0200, 'tree_luck_award', 1, '2023-12-09 09:38:31', '2024-07-11 15:47:43');
INSERT INTO `strategy_award` VALUES (23, 100006, 102, '7等奖', NULL, 100, 50, 0.0300, 'tree_luck_award', 2, '2023-12-09 09:38:31', '2024-07-11 15:57:34');
INSERT INTO `strategy_award` VALUES (24, 100006, 103, '6等奖', NULL, 100, 66, 0.0300, 'tree_luck_award', 3, '2023-12-09 09:38:31', '2024-07-11 15:51:18');
INSERT INTO `strategy_award` VALUES (25, 100006, 104, '5等奖', NULL, 100, 67, 0.0300, 'tree_luck_award', 4, '2023-12-09 09:38:31', '2024-05-23 16:06:47');
INSERT INTO `strategy_award` VALUES (26, 100006, 105, '4等奖', NULL, 100, 69, 0.0300, 'tree_luck_award', 5, '2023-12-09 09:38:31', '2024-07-11 15:51:31');
INSERT INTO `strategy_award` VALUES (27, 100006, 106, '3等奖', '抽奖1次后解锁', 100, 68, 0.0300, 'tree_lock_1', 6, '2023-12-09 09:38:31', '2024-02-15 12:34:00');
INSERT INTO `strategy_award` VALUES (28, 100006, 107, '2等奖', '抽奖1次后解锁', 100, 72, 0.0300, 'tree_lock_1', 7, '2023-12-09 09:38:31', '2024-02-15 12:33:50');
INSERT INTO `strategy_award` VALUES (29, 100006, 108, '1等奖', '抽奖2次后解锁', 100, 74, 0.0300, 'tree_lock_2', 8, '2023-12-09 09:38:31', '2024-02-15 12:32:55');

-- ----------------------------
-- Table structure for strategy_rule
-- ----------------------------
DROP TABLE IF EXISTS `strategy_rule`;
CREATE TABLE `strategy_rule`  (
                                  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                  `strategy_id` int NOT NULL COMMENT '抽奖策略ID',
                                  `award_id` int NULL DEFAULT NULL COMMENT '抽奖奖品ID【规则类型为策略，则不需要奖品ID】',
                                  `rule_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '抽象规则类型；1-策略规则、2-奖品规则',
                                  `rule_model` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖(兜底奖品)】',
                                  `rule_value` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖规则比值',
                                  `rule_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖规则描述',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  INDEX `idx_strategy_id_award_id`(`strategy_id` ASC, `award_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of strategy_rule
-- ----------------------------
INSERT INTO `strategy_rule` VALUES (1, 100001, 101, 2, 'rule_random', '1,1000', '随机积分策略', '2023-12-09 10:05:30', '2023-12-09 12:55:52');
INSERT INTO `strategy_rule` VALUES (2, 100001, 107, 2, 'rule_lock', '1', '抽奖1次后解锁', '2023-12-09 10:16:41', '2023-12-09 12:55:53');
INSERT INTO `strategy_rule` VALUES (3, 100001, 108, 2, 'rule_lock', '2', '抽奖2次后解锁', '2023-12-09 10:17:43', '2023-12-09 12:55:54');
INSERT INTO `strategy_rule` VALUES (4, 100001, 109, 2, 'rule_lock', '6', '抽奖6次后解锁', '2023-12-09 10:17:43', '2023-12-09 12:55:54');
INSERT INTO `strategy_rule` VALUES (5, 100001, 107, 2, 'rule_luck_award', '1,100', '兜底奖品100以内随机积分', '2023-12-09 10:30:12', '2023-12-09 12:55:55');
INSERT INTO `strategy_rule` VALUES (6, 100001, 108, 2, 'rule_luck_award', '1,100', '兜底奖品100以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:55:56');
INSERT INTO `strategy_rule` VALUES (7, 100001, 101, 2, 'rule_luck_award', '1,10', '兜底奖品10以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:55:57');
INSERT INTO `strategy_rule` VALUES (8, 100001, 102, 2, 'rule_luck_award', '1,20', '兜底奖品20以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:55:57');
INSERT INTO `strategy_rule` VALUES (9, 100001, 103, 2, 'rule_luck_award', '1,30', '兜底奖品30以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:55:58');
INSERT INTO `strategy_rule` VALUES (10, 100001, 104, 2, 'rule_luck_award', '1,40', '兜底奖品40以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:55:59');
INSERT INTO `strategy_rule` VALUES (11, 100001, 105, 2, 'rule_luck_award', '1,50', '兜底奖品50以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:56:00');
INSERT INTO `strategy_rule` VALUES (12, 100001, 106, 2, 'rule_luck_award', '1,60', '兜底奖品60以内随机积分', '2023-12-09 10:30:43', '2023-12-09 12:56:00');
INSERT INTO `strategy_rule` VALUES (13, 100001, NULL, 1, 'rule_weight', '4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109', '消耗6000分，必中奖范围', '2023-12-09 10:30:43', '2023-12-31 14:51:50');
INSERT INTO `strategy_rule` VALUES (14, 100001, NULL, 1, 'rule_blacklist', '101:user001,user002,user003', '黑名单抽奖，积分兜底', '2023-12-09 12:59:45', '2024-02-14 18:16:20');
INSERT INTO `strategy_rule` VALUES (15, 100003, 107, 2, 'rule_lock', '1', '抽奖1次后解锁', '2023-12-09 10:16:41', '2023-12-09 12:55:53');
INSERT INTO `strategy_rule` VALUES (16, 100003, 108, 2, 'rule_lock', '2', '抽奖2次后解锁', '2023-12-09 10:17:43', '2024-01-13 10:56:48');
INSERT INTO `strategy_rule` VALUES (17, 100003, 109, 2, 'rule_lock', '6', '抽奖6次后解锁', '2023-12-09 10:17:43', '2023-12-09 12:55:54');

SET FOREIGN_KEY_CHECKS = 1;
