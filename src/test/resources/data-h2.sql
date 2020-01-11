
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
(1, '2019-12-01 00:00:00', 'Britney Spears latest outfit', 1),
(2, '2019-12-02 00:00:00', 'Elizabeth Hurley latest outfit', 2),
(3, '2019-12-03 00:00:00', 'Salma Hayek latest outfit', 3),
(4, '2019-12-04 00:00:00', 'Фото Галь Гадот, обработанное приложением Deep Nude. Опубликовано на сайте Vice', 3),
(5, '2019-12-05 00:00:00', 'Фото актрисы Натали Портман, обработанной программой DeepNude. С сайта Vice', 3),
(6, '2019-12-06 00:00:00', 'Deep Nude Online Premium Version', 3),
(7, '2019-12-07 00:00:00', 'Самые лучшие фейковые снимки получались со спортивных сайтов или с фотографий, сделанных в купальниках. ', 3),
(8, '2019-12-08 00:00:00', 'Best Nudifier, better than Deep Nude! Undress with Photoshop', 3),
(9, '2019-12-09 00:00:00', 'Deep Nude: Как зарабатывать на создании фейковых фотографий от 30 000 рублей!', 3),
(10, '2019-12-10 00:00:00', 'Programa Deep Nude Oficial V2 Versao Premium - Windows 10', 3),
(11, '2019-12-11 00:00:00', 'Programa Deep Nude Oficial V2 Versao Premium - Windows 10', 3),
(12, '2019-12-12 00:00:00', 'Programa Deep Nude Oficial V2 Versao Premium - Windows 10', 3),
(13, '2019-12-13 00:00:00', 'Programa Deep Nude Oficial V2 Versao Premium - Windows 10', 3),
(14, '2019-12-14 00:00:00', 'Programa Deep Nude Oficial V2 Versao Premium - Windows 10', 3);

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

