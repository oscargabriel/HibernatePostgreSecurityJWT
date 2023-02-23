INSERT INTO role (id,description,name) VALUES (1,'User role','USER');
INSERT INTO role (id,description,name) VALUES (2,'Employee role','EMPLOYEE');
INSERT INTO role (id,description,name) VALUES (3,'Admin role','ADMIN');

INSERT INTO users (id, username, password, document, name, last_name, email,phone) VALUES (1,'zaos','$2a$10$ro9NsWv3in8THg42pR5iT.hg8oZVX5pfhTxrr.U26I3yEWiS2PoLu','123456789','gabriel','pinto','zaos@gmail.com','+58123456789');
INSERT INTO users (id, username, password, document, name, last_name, email,phone) VALUES (2,'zaos1','$2a$10$uco1YTAgcVmloPKroKVfI.g3eN5oUjUVkFkYW938AxySZAOiAk5xi','987654321','zaos','zambrano','zaos1@gmail.com','+58123456789');
INSERT INTO users (id, username, password, document, name, last_name, email,phone) VALUES (3,'maria','$2a$10$0hJNdCGt9mYsFJmruBATsOF2i0N2QjcxIio1H8Y.0tHDuHH8R7Rju','159357','maria','chacon','maria@gmail.com','+58123456789');
INSERT INTO users (id, username, password, document, name, last_name, email,phone) VALUES (4,'tere','$2a$10$AqP/D7m7/zg89N.Nk7oN6eRa/7Iosx4sxjUIE9ThDTwIq3zI0N6cC','852456','tere','guerrero','tere@gmail.com','+58123456789');



INSERT INTO user_role (id, role_id, user_id) VALUES (1,1,1);
INSERT INTO user_role (id, role_id, user_id) VALUES (2,3,1);

INSERT INTO user_role (id, role_id, user_id) VALUES (3,1,2);
INSERT INTO user_role (id, role_id, user_id) VALUES (4,2,2);

INSERT INTO user_role (id, role_id, user_id) VALUES (5,1,3);
INSERT INTO user_role (id, role_id, user_id) VALUES (6,3,3);

INSERT INTO user_role (id, role_id, user_id) VALUES (7,1,4);
INSERT INTO user_role (id, role_id, user_id) VALUES (8,2,4);