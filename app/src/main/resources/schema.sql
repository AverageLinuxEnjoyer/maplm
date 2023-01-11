DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS friendships;
DROP TABLE IF EXISTS messages;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT unique_name UNIQUE (first_name, last_name)
);

CREATE TABLE friendships (
    id SERIAL PRIMARY KEY,
    user_id1 INT NOT NULL REFERENCES users(id),
    user_id2 INT NOT NULL REFERENCES users(id),
    friends_from DATE NOT NULL,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT unique_friendship UNIQUE (user_id1, user_id2)
);

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    user_id1 INT NOT NULL REFERENCES users(id),
    user_id2 INT NOT NULL REFERENCES users(id),
    sent_at TIMESTAMP NOT NULL,
    content VARCHAR(255) NOT NULL
);