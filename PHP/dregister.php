<?php

$host="localhost";
$mysql_user="xyz";
$mysql_pass="xyz";
$db_name="xyz";


$conn = mysqli_connect($host,$mysql_user,$mysql_pass,$db_name);

$username=$_POST["username"];
$city=$_POST["city"];
$email=$_POST["email"];
$password=$_POST["password"];
$phoneno=$_POST["phoneno"];
$mysql_qry="INSERT into doctor_data (username,city,email,password,PhoneNo)values('$username','$city','$email','$password','$phoneno')";
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
