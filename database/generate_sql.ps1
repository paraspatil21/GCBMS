$firstNames = @("Aarav", "Rahul", "Amit", "Rohit", "Nikhil", "Ramesh", "Sagar", "Priya", "Sneha", "Karan", "Vikram", "Neha", "Pooja", "Anjali", "Riya")
$lastNames = @("Patel", "Sharma", "Singh", "Shinde", "Yadav", "Patil", "Jadhav", "Gupta", "Verma", "Reddy")
$out = "USE gcbms;`nSET FOREIGN_KEY_CHECKS = 0;`nTRUNCATE TABLE Bookings;`nTRUNCATE TABLE Customers;`nSET FOREIGN_KEY_CHECKS = 1;`n"
for($i=1; $i -le 100; $i++) {
    $name = $($firstNames | Get-Random) + " " + $($lastNames | Get-Random)
    if ($i -eq 5) { $name = "Rohit Shinde" }
    if ($i -eq 6) { $name = "Nikhil Yadav" }
    if ($i -eq 7) { $name = "Ramesh Patil" }
    if ($i -eq 25) { $name = "Sagar Jadhav" }
    $out += "INSERT INTO Customers (username, password, full_name) VALUES ('customer$i', 'customer123', '$name');`n"
}
[IO.File]::WriteAllText("c:\Users\admin\Desktop\GCBMS\database\init.sql", $out)
