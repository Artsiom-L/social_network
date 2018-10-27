USE social;

INSERT INTO users VALUES
  (1, '2012-04-01 00:00:00', 0, 'First', '$2a$10$efe0NgCuntRMMO077T1UK.G.OiqqomNEn.MlOpAg9/mv6cVkXsrc2', NULL, 'Smith', 'user', 'there', 0),
  (2, '2014-07-01 00:00:00', 0, 'Second', '$2a$10$NRpGHsPcgK71O9E2R1jXZ.3JB1mfPdV6IHqHtUBTytbFJ6ucL.zM6', 'patronymic', 'Bond', 'user1', 'there', 0),
  (3, '2016-04-01 00:00:00', 0, 'Third', '$2a$10$efe0NgCuntRMMO077T1UK.G.OiqqomNEn.MlOpAg9/mv6cVkXsrc2', NULL, 'Smith', 'user2', 'there', 0);
INSERT INTO friends VALUES (1, 2);
INSERT INTO groups VALUES (1, 1, 'FirstSteps', 'none');
INSERT INTO group_messages VALUES (1, 1, 'hello world 1', '2018-04-01 01:00:00', 1);
INSERT INTO group_messages VALUES (2, 1, 'hello world 2', '2018-04-01 01:00:10', 1);
INSERT INTO user_groups VALUES (2, 1);

INSERT INTO messages VALUES (1, '2018-04-01 01:00:11', 'message0', 2, 1);
INSERT INTO messages VALUES (2, '2018-04-01 01:00:12', 'message1', 1, 2);
INSERT INTO messages VALUES (3, '2018-04-01 01:00:13', 'message2', 2, 1);
INSERT INTO messages VALUES (4, '2018-04-01 01:00:14', 'message3', 1, 2);
INSERT INTO messages VALUES (5, '2018-04-01 01:00:15', 'message4', 2, 1);
INSERT INTO messages VALUES (6, '2018-04-01 01:00:21', 'message5', 1, 2);
INSERT INTO messages VALUES (7, '2018-04-01 01:00:22', 'message6', 2, 1);
INSERT INTO messages VALUES (8, '2018-04-01 01:00:23', 'message7', 1, 2);

INSERT INTO presents VALUES (1, '2018-04-01 00:00:00', 'first', 2, 1, 'first sign');

INSERT INTO settings VALUES (1, 1, 1, 0, 1, 1, 1, 1, 1, 0);
INSERT INTO settings VALUES (2, 1, 1, 1, 1, 1, 1, 1, 1, 0);
INSERT INTO settings VALUES (3, 1, 1, 0, 1, 1, 1, 1, 1, 0);