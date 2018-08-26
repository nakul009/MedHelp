<?php

$host="localhost";
$mysql_user="xyz";
$mysql_pass="xyz";
$db_name="xyz";


$conn = mysqli_connect($host,$mysql_user,$mysql_pass,$db_name);

$name=$_POST["name"];
$surname=$_POST["surname"];
$email=$_POST["email"];
$username=$_POST["username"];
$password=$_POST["password"];
$mysql_qry="INSERT into employee_data (name,surname,email,username,password)values('$name','$surname','$email','$username','$password')";
if($conn->query($mysql_qry)===TRUE)
{
echo "insert success";
}
else
{
echo "Error :".$mysql_qry."<br>".$conn->error;
}
$conn->close();
?>
