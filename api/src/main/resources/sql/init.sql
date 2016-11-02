/*用户*/
INSERT INTO `microbbs`.`sys_user` (`id`, `u_cellphone`, `u_channel`, `u_client`, `u_email`, `u_gender`, `u_is_active`, `u_last_login_time`, `u_login_times`, `u_nick`, `u_password`, `u_register_time`, `u_username`) VALUES ('1', '18600010001', 'ADMIN', 'WEB', 'meiqiu@meiqiu.me', 1, 1, '2016-09-11', '0', '小明', '111111', '2016-09-11', 'xiaoming');
INSERT INTO `microbbs`.`sys_user` (`id`, `u_cellphone`, `u_channel`, `u_client`, `u_email`, `u_gender`, `u_is_active`, `u_last_login_time`,
                                   `u_login_times`, `u_nick`, `u_password`, `u_register_time`, `u_username`) VALUES ('2', '18600010002', 'ADMIN',
                                                                                                                          'WEB', 'meiqiu@meiqiu
                                                                                                                          .me', 1, 1, '2016-09-12',
                                                                                                                          '0', '张小黑', '111111',
                                                                                                                     '2016-09-12', 'xiaohei');

/*tags*/
INSERT INTO `microbbs`.`m_tag` (`id`, `t_title`) VALUES ('1', 'Java');
INSERT INTO `microbbs`.`m_tag` (`id`, `t_title`) VALUES ('2', '前端');
INSERT INTO `microbbs`.`m_tag` (`id`, `t_title`) VALUES ('3', '后端');
INSERT INTO `microbbs`.`m_tag` (`id`, `t_title`) VALUES ('4', 'Spring');
INSERT INTO `microbbs`.`m_tag` (`id`, `t_title`) VALUES ('5', '分布式');

/*post*/
INSERT INTO `microbbs`.`m_post` (`id`,   `p_content`, `p_created_time`, `p_last_author_name`, `p_last_reply_time`, `p_reply`, `p_status`,
                                 `p_summary`, `p_title`, `p_updated_time`, `p_author`, `p_last_author`) VALUES ('1',  'abc', '2016-09-13', '张小黑', '2016-09-13', 1, 'actived', 'hello world', '你好，楼主', null, '1', '2');
INSERT INTO `microbbs`.`m_post` (`id`,   `p_content`, `p_created_time`, `p_last_author_name`, `p_last_reply_time`, `p_reply`, `p_status`,
                                 `p_summary`, `p_title`, `p_updated_time`, `p_author`, `p_last_author`) VALUES ('2',  'abc', '2016-09-13', '张小黑',
                                                                                                                      '2016-09-13', 1, 'actived',
                                                                                                                      'hello world', '你好，楼主', null, '2', '2');

/*post_tag*/
INSERT INTO `microbbs`.`m_posts_tags` (`post_id`, `tag_id`) VALUES ('1', '1');
INSERT INTO `microbbs`.`m_posts_tags` (`post_id`, `tag_id`) VALUES ('1', '2');
INSERT INTO `microbbs`.`m_posts_tags` (`post_id`, `tag_id`) VALUES ('1', '3');
INSERT INTO `microbbs`.`m_posts_tags` (`post_id`, `tag_id`) VALUES ('2', '1');
INSERT INTO `microbbs`.`m_posts_tags` (`post_id`, `tag_id`) VALUES ('2', '5');
INSERT INTO `microbbs`.`m_posts_tags` (`post_id`, `tag_id`) VALUES ('2', '4');