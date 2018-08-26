<?php
$host="localhost";
$mysql_user="xyz";
$mysql_pass="xyz";
$db_name="xyz";


$conn = mysqli_connect($host,$mysql_user,$mysql_pass,$db_name);

$user_name=$_POST["user_name"];
$user_pass=$_POST["password"];
$mysql_qry="SELECT * from employee_data WHERE username like'$user_name'and password like '$user_pass';";
$result=mysqli_query($conn,$mysql_qry);
if(mysqli_num_rows($result)>0)
{
echo "login success";
}
else
{
echo "not success";
}
?>
