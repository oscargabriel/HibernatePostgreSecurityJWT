INSERT INTO role (id,description,name) VALUES (1,'User role','USER');
INSERT INTO role (id,description,name) VALUES (2,'Employee role','EMPLOYEE');
INSERT INTO role (id,description,name) VALUES (3,'Admin role','ADMIN');

INSERT INTO users (id, username, password, document, name, last_name, email,phone) VALUES (1,'zaos1','$2a$10$uco1YTAgcVmloPKroKVfI.g3eN5oUjUVkFkYW938AxySZAOiAk5xi','987654321','oscar','zambrano','zaos1@gmail.com','+58123456789');

INSERT INTO user_role (id, role_id, user_id) VALUES (1,1,1);
INSERT INTO user_role (id, role_id, user_id) VALUES (2,3,1);