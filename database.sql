-- Buat database
CREATE DATABASE employee_management;


-- Buat tabel users
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Buat tabel employees
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    user_id INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Buat indeks pada kolom user_id di tabel employees untuk meningkatkan performa query
CREATE INDEX idx_employees_user_id ON employees(user_id);

-- Tambahkan beberapa data contoh (opsional)
INSERT INTO users (username, password) VALUES
('aidil_adam', 'huhui1'),
('budi', 'huhui1');

INSERT INTO employees (name, user_id) VALUES
('Aidil Adam', 1),
('Budi', 2),
('Joko', 1);

-- Verifikasi data
SELECT * FROM users;

SELECT * FROM employees;
truncate employees;

-- Contoh query untuk mendapatkan karyawan beserta informasi usernya
SELECT e.id, e.name, u.username
FROM employees e
JOIN users u ON e.user_id = u.id;