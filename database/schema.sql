BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, game, user_game, trade, portfolio, chat, stock, stock_ticker;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE game (
    game_id SERIAL,
    game_name varchar(50) NOT NULL UNIQUE,
    start_date timestamp NOT NULL,
    end_date timestamp NOT NULL,
    creator_id integer NOT NULL,
    winner varchar(50),
    CONSTRAINT PK_game PRIMARY KEY (game_id),
    CONSTRAINT FK_creator FOREIGN KEY (creator_id) REFERENCES users (user_id)
);

CREATE TABLE user_game (
    user_id integer NOT NULL,
    game_id integer NOT NULL,
    game_balance NUMERIC,
    CONSTRAINT PK_user_game PRIMARY KEY (user_id, game_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK_game FOREIGN KEY (game_id) REFERENCES game (game_id)
);

CREATE TABLE stock_trade (
    trade_id SERIAL,
    user_id int NOT NULL,
    game_id int NOT NULL,
    trade_date timestamp NOT NULL,
    stock_ticker varchar(10) NOT NULL,
    trade_type varchar(10) NOT NULL,
    trade_quantity numeric NOT NULL,
    stock_trade_price numeric NOT NULL,
    CONSTRAINT PK_trade PRIMARY KEY (trade_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK_game FOREIGN KEY (game_id) REFERENCES game (game_id)
);

CREATE TABLE portfolio (
    portfolio_id SERIAL,
    user_id int NOT NULL,
    game_id int NOT NULL,
    stock_ticker VARCHAR(10) NOT NULL,
    stock_quantity NUMERIC NOT NULL,
    trade_price NUMERIC,
    CONSTRAINT PK_portfolio PRIMARY KEY (portfolio_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK_game FOREIGN KEY (game_id) REFERENCES game (game_id),
    CONSTRAINT Unique_Ticker UNIQUE (user_id, game_id, stock_ticker)
);

CREATE TABLE chat (
    chat_id SERIAL,
    user_id int NOT NULL,
    game_id int NOT NULL,
    chat_date timestamp NOT NULL,
    chat_content varchar(500),
    CONSTRAINT PK_chat PRIMARY KEY (chat_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK_game FOREIGN KEY (game_id) REFERENCES game (game_id)
);

CREATE TABLE stock (
    ticker varchar(10) NOT NULL UNIQUE,
    close_price NUMERIC NOT NULL,
    open_price NUMERIC NOT NULL,
    highest_price NUMERIC NOT NULL,
    lowest_price NUMERIC NOT NULL,
    view_date timestamp NOT NULL,
    day_trading_volume NUMERIC NOT NULL,
    weighted_average_price NUMERIC NOT NULL,
    prices NUMERIC[] NOT NULL,
    CONSTRAINT PK_stock PRIMARY KEY (ticker)
);

CREATE TABLE stock_ticker (
    ticker varchar(10) NOT NULL UNIQUE,
    stock_company_name varchar(1000) NOT NULL,
    stock_type varchar(10) NOT NULL,
    CONSTRAINT PK_stock_ticker PRIMARY KEY (ticker)
);

COMMIT TRANSACTION;
