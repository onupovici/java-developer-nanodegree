CREATE TABLE product (
    id INT NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(500) NOT NULL,
    created_time TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE review (
    review_id INT NOT NULL AUTO_INCREMENT,
    review_star INT NOT NULL,
    review_text VARCHAR(1000) NOT NULL,
    review_user VARCHAR(500) NOT NULL,
    review_created_time TIMESTAMP NOT NULL,
    product_id INT,
    PRIMARY KEY (review_id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE comment (
    comment_id INT NOT NULL AUTO_INCREMENT,
    comment_text VARCHAR(1000) NOT NULL,
    comment_user VARCHAR(500) NOT NULL,
    comment_created_time TIMESTAMP NOT NULL,
    review_id INT,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (review_id) REFERENCES review(review_id)
);