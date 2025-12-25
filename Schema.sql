-- Step 1: Create the database
CREATE DATABASE attendance_db;

-- Step 2: Use the database
USE attendance_db;

-- Step 3: Create the 'students' table
CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    phone VARCHAR(15)
);

-- Step 4: Create the 'attendance' table
CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    date DATE,
    status VARCHAR(10),
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);
