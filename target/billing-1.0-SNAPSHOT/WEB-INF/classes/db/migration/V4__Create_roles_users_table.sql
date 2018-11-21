CREATE TABLE `user_roles` (
    `user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
);