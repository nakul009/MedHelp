<?php
$host="localhost";
$db_name="ixyz";
$mysql_user="xyz";
$mysql_pass="xyz";
//$server_name="localhost";

$con = mysqli_connect($host,$mysql_user,$mysql_pass,$db_name);
if(!$con)
{
echo "Connection Error ....".mysqli_connect_error();
}
else
{
echo "<h3>Database connection Success ...</h3>";
}

?>
