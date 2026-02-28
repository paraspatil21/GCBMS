import random

names = [
    'Arjun Patil', 'Nikhil Yadav', 'Anjali Deshmukh', 'Suraj Jadhav', 'Rohit Shinde',
    'Pooja Kulkarni', 'Sneha Pawar', 'Omkar More', 'Tanvi Patil', 'Sagar Chavan',
    'Aditya Joshi', 'Rutuja Kadam', 'Prasad Inamdar', 'Shruti Joshi', 'Rahul Kale',
    'Neha Bhosale', 'Saurabh Salunkhe', 'Aarti Kulkarni', 'Karan Wagh', 'Riya Gawande',
    'Yash Deshmukh', 'Pallavi Mane', 'Gaurav Kadam', 'Kalyani Shirke', 'Tejas Patil',
    'Pranali Sawant', 'Akash Desai', 'Priyanka Ghuge', 'Siddharth Rane', 'Divya Mahajan',
    'Kunal Thakur', 'Gauri Bapat', 'Abhishek Gore', 'Shweta Dixit', 'Pratik Munde',
    'Monika Bagwe', 'Vishal Raut', 'Poonam Nimbalkar', 'Rohan Gokhale', 'Sonali Shelar',
    'Nitin Datar', 'Kavita Thombare', 'Vijay Dhumal', 'Swati Phadke', 'Sandeep Sutar',
    'Manasi Gaikwad', 'Amit Jagtap', 'Roshni Salve', 'Harshad Apte', 'Nisha Patil',
    'Kedar Pande', 'Vaishnavi Bhat', 'Sameer Surve', 'Megha Choudhari', 'Tushar Khade',
    'Kirti Doshi', 'Chetan Deshpande', 'Shreya Date', 'Manoj Pathak', 'Jyoti Vaidya',
    'Anil Zende', 'Deepika Shinde', 'Pradeep Sathe', 'Ankita Kulkarni', 'Sachin Mule',
    'Rajani Tawade', 'Umesh Kamat', 'Smita Gade', 'Santosh Rathi', 'Amruta Barve',
    'Prakash Mhatre', 'Rupali Dalvi', 'Ganesh Kakade', 'Vidya Saraf', 'Mahesh Wagle',
    'Asha Lokhande', 'Ramesh Bhagat', 'Ujwala Kulkarni', 'Sanjay Parab', 'Minal Karve',
    'Rajesh Sane', 'Neeta Mahadik', 'Ashok Vaze', 'Leena Pingale', 'Kishore Kulkarni',
    'Sarita Godbole', 'Sunil Oak', 'Geeta Kelkar', 'Deepak Marathe', 'Maya Kulkarni',
    'Vivek Wagh', 'Shubhangi Ranade', 'Anand Kulkarni', 'Rekha Bhide', 'Ajay Agashe',
    'Vandana Gokhale', 'Rajendra Bapat', 'Asmita Dixit', 'Dilip Kanitkar', 'Supriya Bhagwat',
    'Kiran Munde', 'Pramod More', 'Vikas Chavan', 'Nandini Patil', 'Amol Deshmukh',
    'Kishor Kadam', 'Swapnil Joshi', 'Dinesh Sutar', 'Smita Raut', 'Prajakta Bhosale'
]

with open('init.sql', 'w') as f:
    f.write('CREATE DATABASE IF NOT EXISTS gcbms;\n')
    f.write('USE gcbms;\n\n')
    
    f.write('CREATE TABLE Customers (\n')
    f.write('    customer_id INT PRIMARY KEY AUTO_INCREMENT,\n')
    f.write('    username VARCHAR(50) UNIQUE,\n')
    f.write('    password VARCHAR(100),\n')
    f.write('    name VARCHAR(100)\n')
    f.write(');\n\n')
    
    f.write('CREATE TABLE Cylinders (\n')
    f.write('    cylinder_id INT PRIMARY KEY AUTO_INCREMENT,\n')
    f.write('    category VARCHAR(50),\n')
    f.write('    weight VARCHAR(20),\n')
    f.write('    price DECIMAL(10,2)\n')
    f.write(');\n\n')
    
    f.write('CREATE TABLE Bookings (\n')
    f.write('    booking_id INT PRIMARY KEY AUTO_INCREMENT,\n')
    f.write('    customer_id INT,\n')
    f.write('    cylinder_id INT,\n')
    f.write('    payment_mode VARCHAR(20),\n')
    f.write('    upi_id VARCHAR(100) NULL,\n')
    f.write('    transaction_id VARCHAR(100) NULL,\n')
    f.write('    address TEXT,\n')
    f.write('    contact VARCHAR(15),\n')
    f.write('    status VARCHAR(50),\n')
    f.write('    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n')
    f.write('    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),\n')
    f.write('    FOREIGN KEY (cylinder_id) REFERENCES Cylinders(cylinder_id)\n')
    f.write(');\n\n')
    
    f.write('-- Insert Cylinders\n')
    f.write("INSERT INTO Cylinders (category, weight, price) VALUES\n")
    f.write("('Domestic', '5 kg LPG', 350.00),\n")
    f.write("('Domestic', '14.2 kg LPG', 950.00),\n")
    f.write("('Commercial', '19 kg LPG', 1500.00),\n")
    f.write("('Commercial', '35 kg LPG', 2500.00),\n")
    f.write("('Commercial', '47.5 kg LPG', 3400.00);\n\n")
    
    f.write('-- Insert Customers\n')
    f.write('INSERT INTO Customers (username, password, name) VALUES\n')
    customer_values = []
    for i, name in enumerate(names[:100], 1):
        customer_values.append(f"('customer{i}', 'customer123', '{name}')")
    f.write(',\n'.join(customer_values) + ';\n')
