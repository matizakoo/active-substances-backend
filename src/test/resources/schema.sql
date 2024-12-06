-- Table structure for table `active_substance`
DROP TABLE IF EXISTS active_substance;
CREATE TABLE active_substance (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  description VARCHAR(255) NOT NULL,
                                  dosage VARCHAR(255) NOT NULL,
                                  name VARCHAR(255) NOT NULL,
                                  pregnance BOOLEAN NOT NULL
);

-- Table structure for table `user`
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
                        id BIGINT PRIMARY KEY,
                        password VARCHAR(255),
                        username VARCHAR(255) UNIQUE,
                        name VARCHAR(255),
                        surname VARCHAR(255)
);

-- Table structure for table `disease`
DROP TABLE IF EXISTS disease;
CREATE TABLE disease (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         description VARCHAR(255),
                         name VARCHAR(255) NOT NULL
);

-- Table structure for table `cure`
DROP TABLE IF EXISTS cure;
CREATE TABLE cure (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      active_substance_id INT,
                      disease_id INT,
                      FOREIGN KEY (active_substance_id) REFERENCES active_substance(id),
                      FOREIGN KEY (disease_id) REFERENCES disease(id)
);

-- Table structure for table `patient`
DROP TABLE IF EXISTS patient;
CREATE TABLE patient (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         age INT,
                         gender VARCHAR(255),
                         name VARCHAR(255),
                         surname VARCHAR(255),
                         unique_id VARCHAR(255),
                         user_id BIGINT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES "user"(id)
);



-- Table structure for table `role_entity`
DROP TABLE IF EXISTS role_entity;
CREATE TABLE role_entity (
                             id BIGINT PRIMARY KEY,
                             name VARCHAR(255)
);

-- Table structure for table `user_role`
DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
                           user_id BIGINT NOT NULL,
                           role_id BIGINT NOT NULL,
                           PRIMARY KEY (user_id, role_id),
                           FOREIGN KEY (user_id) REFERENCES "user"(id),
                           FOREIGN KEY (role_id) REFERENCES role_entity(id)
);


-- Table structure for table `patient_disease_substance`
DROP TABLE IF EXISTS patient_disease_substance;
CREATE TABLE patient_disease_substance (
                                           id INT PRIMARY KEY AUTO_INCREMENT,
                                           active_substance_id INT,
                                           disease_id INT,
                                           patient_id INT,
                                           user_id BIGINT,
                                           FOREIGN KEY (active_substance_id) REFERENCES active_substance(id),
                                           FOREIGN KEY (disease_id) REFERENCES disease(id),
                                           FOREIGN KEY (patient_id) REFERENCES patient(id),
                                           FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- Table structure for table `substance_conflict`
DROP TABLE IF EXISTS substance_conflict;
CREATE TABLE substance_conflict (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    activesubstance2_id INT,
                                    activesubstance1_id INT,
                                    FOREIGN KEY (activesubstance2_id) REFERENCES active_substance(id),
                                    FOREIGN KEY (activesubstance1_id) REFERENCES active_substance(id)
);

-- Dumping data for table `substance_conflict`

-- Table structure for table `user_seq`
DROP TABLE IF EXISTS user_seq;
CREATE TABLE user_seq (
    next_val BIGINT
);


SELECT * FROM "user";
