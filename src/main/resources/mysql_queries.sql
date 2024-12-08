USE mydb

-- Create the 'users' table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY, -- Primary Key
    name VARCHAR(100) NOT NULL,             -- User's Name
    email VARCHAR(100) UNIQUE NOT NULL,     -- User's Email (unique)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Creation Timestamp
);

-- Create the 'orders' table
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY, -- Primary Key
    user_id INT NOT NULL,                    -- Foreign Key referencing 'users'
    order_date DATE NOT NULL,                -- Date of the Order
    order_amount INT NOT NULL,    -- Total Amount of the Order
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Creation Timestamp
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert users
INSERT INTO users (name, email) VALUES
('Alice', 'alice@example.com'),
('Bob', 'bob@example.com');

DESCRIBE users;
DESCRIBE orders;

-- Insert orders
INSERT INTO orders (user_id, order_date, order_amount) VALUES
(1, '2024-11-30', 120),
(1, '2024-12-01', 75),
(2, '2024-12-01', 50);

-- Fetch all orders with user details
SELECT o.order_id,
        u.name,
        o.order_date,
        o.order_amount,
        u.email,
        u.user_id
FROM orders o INNER JOIN users u
ON o.user_id = u.user_id;
