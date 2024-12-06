-- Dumping data for table `user`
INSERT INTO "user" VALUES
    (1, '$2a$12$dFRrg89fT6VlJRkatGlv/eXJZKtHWtE9bsMRfZRGynztXxa4J8FtO', 'test', 'test', 'test');


-- Dumping data for table `role_entity`
INSERT INTO role_entity VALUES
                            (1, 'ADMIN'),
                            (2, 'DOCTOR');


-- Dumping data for table `user_role`
INSERT INTO user_role VALUES
                          (1, 1),
                          (1, 2);


-- Dumping data for table `user_seq`
INSERT INTO user_seq VALUES (1);