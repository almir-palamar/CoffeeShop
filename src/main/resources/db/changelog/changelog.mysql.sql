-- liquibase formatted sql

-- changeset almirpalamar:1729496170755-1
CREATE TABLE barista
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    name                VARCHAR(255) NULL,
    espresso_machine_id BIGINT NULL,
    status              ENUM('AVAILABLE', 'MAKING_COFFEE', 'FILLING_GRINDER') NULL,
    CONSTRAINT PK_BARISTA PRIMARY KEY (id),
    UNIQUE (espresso_machine_id)
);

-- changeset almirpalamar:1729496170755-2
CREATE TABLE coffee
(
    id BIGINT     AUTO_INCREMENT NOT NULL,
    brew_time     INT NULL,
    caffeine_gram INT NULL,
    price         FLOAT(12) NULL,
    image_path    VARCHAR(255) NULL,
    name          VARCHAR(255) NOT NULL,
    CONSTRAINT PK_COFFEE PRIMARY KEY (id),
    UNIQUE (name)
);

-- changeset almirpalamar:1729496170755-3
CREATE TABLE espresso_machine
(
    grinder INT NULL,
    id      BIGINT AUTO_INCREMENT NOT NULL,
    brand   VARCHAR(255) NULL,
    CONSTRAINT PK_ESPRESSO_MACHINE PRIMARY KEY (id)
);

-- changeset almirpalamar:1729496170755-4
CREATE TABLE orders
(
    barista_id BIGINT NULL,
    id BIGINT AUTO_INCREMENT NOT NULL,
    status ENUM('PENDING', 'DELIVERED') NULL,
    type ENUM('TO_GO', 'WEB_UI') NULL,
    CONSTRAINT PK_ORDERS PRIMARY KEY (id)
);

-- changeset almirpalamar:1729496170755-5
CREATE TABLE orders_coffees
(
    coffees_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL
);

-- changeset almirpalamar:1729496170755-6
CREATE TABLE users
(
    id BIGINT AUTO_INCREMENT NOT NULL,
    email VARCHAR(255) NULL,
    first_name VARCHAR(255) NULL,
    last_name VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    username VARCHAR(255) NULL,
    `role` ENUM('ADMIN', 'USER') NULL,
    CONSTRAINT PK_USERS PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (username)
);

-- changeset almirpalamar:1729496170755-7
CREATE INDEX FK58klq7phpyj5r096mk5ytuym8 ON orders_coffees(coffees_id);

-- changeset almirpalamar:1729496170755-8
CREATE INDEX FKhr1s9jcg9jyv394t5rwav5dqd ON orders_coffees(order_id);

-- changeset almirpalamar:1729496170755-9
CREATE INDEX FKiin4rl2xhp11fu6oavebqs3lj ON orders(barista_id);

-- changeset almirpalamar:1729496170755-10
ALTER TABLE orders_coffees
    ADD CONSTRAINT FK58klq7phpyj5r096mk5ytuym8 FOREIGN KEY (coffees_id) REFERENCES coffee (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset almirpalamar:1729496170755-11
ALTER TABLE orders_coffees
    ADD CONSTRAINT FKhr1s9jcg9jyv394t5rwav5dqd FOREIGN KEY (order_id) REFERENCES orders (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset almirpalamar:1729496170755-12
ALTER TABLE orders
    ADD CONSTRAINT FKiin4rl2xhp11fu6oavebqs3lj FOREIGN KEY (barista_id) REFERENCES barista (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

-- changeset almirpalamar:1729496170755-13
ALTER TABLE barista
    ADD CONSTRAINT FKt4vbn41o00wpmowxn11apcpiv FOREIGN KEY (espresso_machine_id) REFERENCES espresso_machine (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

