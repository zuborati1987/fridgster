DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS food CASCADE;
DROP TABLE IF EXISTS storages CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS measurements CASCADE;
DROP TABLE IF EXISTS shopping_lists CASCADE;


CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       email TEXT UNIQUE NOT NULL,
       password TEXT NOT NULL,
       admin BOOLEAN NOT NULL,
       CONSTRAINT email_not_empty CHECK (email <> ''),
       CONSTRAINT password_not_empty CHECK (password <> '')
);

CREATE TABLE storages (
      id SERIAL PRIMARY KEY,
      name TEXT NOT NULL,
      user_id INTEGER,
      CONSTRAINT name_not_empty CHECK (name <> ''),
      FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    user_id INTEGER,
    CONSTRAINT name_not_empty CHECK (name <> ''),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE measurements (
      id SERIAL PRIMARY KEY,
      name TEXT NOT NULL,
      user_id INTEGER,
      CONSTRAINT name_not_empty CHECK (name <> ''),
      FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE food (
      id SERIAL PRIMARY KEY,
      name TEXT NOT NULL,
      category_id INTEGER NOT NULL,
      measurement_id INTEGER NOT NULL,
      amount DOUBLE PRECISION NOT NULL ,
      storage_id INTEGER,
      expiry DATE,
      user_id INTEGER,
      CONSTRAINT name_not_empty CHECK (name <> ''),
      CONSTRAINT amount_not_negative CHECK (amount >= 0),
      FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
      FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
      FOREIGN KEY (measurement_id) REFERENCES measurements(id) ON DELETE CASCADE,
      FOREIGN KEY (storage_id) REFERENCES storages(id) ON DELETE CASCADE
);

CREATE TABLE shopping_lists (
    user_id INTEGER NOT NULL,
    food_id INTEGER NOT NULL,
    amount DOUBLE PRECISION,
    CONSTRAINT amount_not_zero CHECK (amount > 0),
    PRIMARY KEY (user_id, food_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (food_id) REFERENCES food(id) ON DELETE CASCADE
);

INSERT INTO users (email, password, admin) VALUES
    ('a', 'a', TRUE),       -- 1
    ('justin@xiang', 'yenlowang', FALSE), -- 2
    ('paul@atreides', 'muaddib', FALSE),    -- 3
    ('jake@armitage', 'drake', TRUE); --4

INSERT INTO storages (name, user_id) VALUES
    ('fridge', 1),             -- 1
    ('top shelf', 1),          -- 2
    ('bottom shelf', 1),       -- 3
    ('middle shelf', 1),       -- 4
    ('kitchen cupboard', 1),   -- 5
    ('fridge', 2),             -- 6
    ('top shelf', 2),          -- 7
    ('bottom shelf', 2),       -- 8
    ('bottom shelf', 3),       -- 9
    ('middle shelf', 3),       -- 10
    ('kitchen cupboard', 3);   -- 11

INSERT INTO categories (name, user_id) VALUES
    ('dairy products', 1),            -- 1
    ('bakery products', 1),           -- 2
    ('snacks', 1),                    -- 3
    ('sweets', 1),                    -- 4
    ('refreshers', 1),                -- 5
    ('alcoholic beverages', 1),       -- 6
    ('fruit', 1),                     -- 7
    ('vegetables', 1),                -- 8
    ('dry products', 1),              -- 9
    ('canned products', 1),           -- 10
    ('cold-cuts', 2),                 -- 11
    ('fresh meat', 2),                -- 12
    ('frozen products', 2);           -- 13

INSERT INTO measurements (name, user_id) VALUES
    ('kg', 1),              -- 1
    ('liter', 1),           -- 2
    ('gram', 1),            -- 3
    ('piece', 1),           -- 4
    ('can', 1),             -- 5
    ('bottle', 1),          -- 6
    ('carton', 2),          -- 7
    ('bag', 2),             -- 8
    ('bar', 2);             -- 9

INSERT INTO food (name, category_id, amount, measurement_id, storage_id, expiry, user_id) VALUES
    ('orange', 7, 1, 4, 1, '2019-06-12', 1),              -- 1
    ('milk', 1, 2, 2, 1, '2019-06-13', 1),                -- 2
    ('cheese', 1, 0.5, 1, 1, '2019-06-14', 1),              -- 3
    ('carrot', 8, 5, 4, 8, '2019-06-15', 2),              -- 4
    ('coke', 5, 1, 5, 6, '2019-06-16', 2),                -- 5
    ('pork chop', 12, 5, 1, 6, '2019-06-17', 2),           -- 6
    ('flour', 9, 2, 1, 9, '2019-06-18', 3),               -- 7
    ('rice', 9, 3, 1, 10, '2019-06-19', 3),                -- 8
    ('mars bar', 4, 1, 9, 11, '2019-06-20', 3),            -- 9
    ('cheetos', 3, 1, 8, 11, '2019-06-21', 3);             -- 10

INSERT INTO shopping_lists (user_id, food_id, amount) VALUES
(1, 1, 1),
(1, 2, 1),
(1, 3, 1),
(1, 4, 1),
(1, 5, 1),
(2, 6, 1),
(2, 7, 1),
(2, 8, 1),
(2, 9, 1),
(2, 10, 1);
