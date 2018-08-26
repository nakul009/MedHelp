<?php
require "init.php";
$name=$_POST["name"];
$email=$_POST["email"];
$mobile=$_POST["mobile"];

$sql_query = "insert into product_info values('$name','$email','$mobile');";

if(mysqli_query($con,$sql_query))
{
echo "<h3> Data Insertion Success...</h3>";
}
else{
echo "Data insertion error..".mysqli.error($con);
}

?>
