
--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`user_id`, `avatar_id`, `first_name`, `hash`, `last_name`, `last_visit_date`, `username`) VALUES
(1, 0, 'Britney', 1, 'Spears', '2019-11-20 00:00:00', 'britneyspears'),
(2, 0, 'Elizabeth', 2, 'Hurley', '2019-11-19 00:00:00', 'elizabethhurley'),
(3, 0, 'Salma', 3, 'Hayek', '2019-11-18 00:00:00', 'salmahayek');

--
-- Дамп данных таблицы `post`
--

INSERT INTO `post` (`post_id`, `date`, `title`, `user_id`) VALUES
(1, '2019-11-21 00:00:00', 'Britney Spears latest outfit', 1),
(2, '2019-11-20 00:00:00', 'Elizabeth Hurley latest outfit', 2),
(3, '2019-11-18 00:00:00', 'Salma Hayek latest outfit', 3);

--
-- Дамп данных таблицы `view`
--

INSERT INTO `view` (`view_id`, `post_id`, `user_id`) VALUES
(3, 3, 1),
(1, 1, 2),
(2, 2, 3);

--
-- Дамп данных таблицы `tbl_like`
--

INSERT INTO `tbl_like` (`like_id`, `post_id`, `user_id`) VALUES
(3, 3, 1),
(1, 1, 2),
(2, 2, 3);

