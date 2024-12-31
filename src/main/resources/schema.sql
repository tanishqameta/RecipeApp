CREATE TABLE Recipe (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    ingredients VARCHAR(MAX),
    instructions VARCHAR(MAX),
    prep_time_minutes INT,
    cook_time_minutes INT,
    servings INT,
    difficulty VARCHAR(50),
    cuisine VARCHAR(100),
    calories_per_serving INT,
    tags VARCHAR(MAX),
    image VARCHAR(255),
    rating DOUBLE,
    review_count INT,
    meal_type VARCHAR(255)
);
