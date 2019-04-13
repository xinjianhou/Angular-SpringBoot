INSERT INTO USERS(ID, USERNAME, PASSWORD, EMAIL, ENABLED, LASTPASSWORDRESETDATE) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin@admin.com', 1, now());
INSERT INTO USERS(ID, USERNAME, PASSWORD, EMAIL, ENABLED, LASTPASSWORDRESETDATE) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'enabled@user.com', 1, now());
INSERT INTO USERS(ID, USERNAME, PASSWORD, EMAIL, ENABLED, LASTPASSWORDRESETDATE) VALUES (3, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'disabled@user.com', 0, now());

INSERT INTO AUTHORITY (ID, AUTHORITY_NAME) VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (ID, AUTHORITY_NAME) VALUES (2, 'ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (3, 1);

-- INSERT INTO KNOWLEDGE (ID, KNOWLEDGENAME, KNOWLEDGECONTENT) VALUES(KNOWLEDGE_SEQ.nextVal, 'Assert','computer');
-- INSERT INTO KNOWLEDGE (ID, KNOWLEDGENAME, KNOWLEDGECONTENT) VALUES(KNOWLEDGE_SEQ.nextVal,'ProjectDoc','requirment');
