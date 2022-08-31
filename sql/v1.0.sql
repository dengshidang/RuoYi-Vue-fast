CREATE TABLE `model_upload_back` (
                                     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '上传id',
                                     `original_name` varchar(255) NOT NULL COMMENT '源文件名称',
                                     `file_path` varchar(255) NOT NULL DEFAULT '' COMMENT '路径',
                                     `file_name` varchar(255) NOT NULL DEFAULT '' COMMENT '文件名称',
                                     `file_md5` varchar(255) DEFAULT NULL COMMENT '文件唯一id',
                                     `file_type` varchar(64) NOT NULL,
                                     `handler` tinyint(1) NOT NULL DEFAULT '0',
                                     `model_code` varchar(255) DEFAULT '',
                                     `model_name` varchar(128) DEFAULT NULL,
                                     `size` bigint(21) DEFAULT NULL,
                                     `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `create_by` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
                                     `update_by` varchar(255) NOT NULL DEFAULT '' COMMENT '更新人',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=524 DEFAULT CHARSET=utf8mb4;
