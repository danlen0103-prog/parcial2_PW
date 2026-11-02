CREATE TABLE users (
    user_id  SERIAL PRIMARY KEY,
    username VARCHAR(45)  NOT NULL UNIQUE,
    password VARCHAR(64)  NOT NULL,
    enabled  BOOLEAN      NOT NULL DEFAULT TRUE
);


CREATE TABLE roles (
    role_id SERIAL PRIMARY KEY,
    name    VARCHAR(45) NOT NULL UNIQUE
);


CREATE TABLE users_roles (
    user_id INT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    role_id INT NOT NULL REFERENCES roles(role_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);


INSERT INTO roles (name) VALUES ('USER'), ('CREATOR'), ('EDITOR'), ('ADMIN');


INSERT INTO users (username, password, enabled) VALUES
('admin',   '$2b$12$ifxGamsC4QfliZiAnBaDmurVh2ZtSAEu63/nAwnS86nandsZkCSL2', TRUE),
('creator', '$2b$12$x10ouQJhikgWpmviJahIJ.9uPszD..6ZBX0lJ8v7r4/a0ejhqi.Ey', TRUE),
('editor',  '$2b$12$ArWwBBNPNZz/ikibjuaZPevzneiXe8rE4koAcEYqBebBX.ct5GRN2', TRUE),
('user',    '$2b$12$SxELHIHbwh25BUJ.UzEp0uD15UkfbN8T2yyphrXMyBHhIoG72POEa', TRUE);


INSERT INTO users_roles (user_id, role_id)
SELECT u.user_id, r.role_id FROM users u, roles r
WHERE (u.username = 'admin'   AND r.name = 'ADMIN')
   OR (u.username = 'creator' AND r.name IN ('USER', 'CREATOR'))   -- dos roles
   OR (u.username = 'editor'  AND r.name = 'EDITOR')
   OR (u.username = 'user'    AND r.name = 'USER');