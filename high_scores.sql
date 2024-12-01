CREATE DATABASE tron_high_scores;

USE tron_high_scores;

CREATE TABLE tron_high_scores (
    player_name VARCHAR(255) PRIMARY KEY,
    points INT NOT NULL
);