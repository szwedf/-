<?php
$user = 'syouta';
$pass = 'Seed246790';
 
    if (empty($_GET['id'])) {
     echo 'IDを正しく入力してください。';
     exit;
 }
 try {
     $id = (int)$_GET['id'];
     $dbh = new PDO('mysql:host=localhost;dbname=db1;charset=utf8', $user, $pass);
     $dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
     $sql= 'delete from recipes where id = ?';
     $stmt = $dbh->prepare($sql);
     $stmt->bindValue(1, $id, PDO::PARAM_INT);
     $stmt->execute();
     $dbh =null;
     echo 'ID: ' . htmlspecialchars($id, ENT_QUOTES) . 'の削除が完了しました。<br>';
     echo '<a href="index.php">トップページへ戻る</a>';
    } catch (PDOException $e) {
         echo 'エラー発生: ' . htmlspecialchars($e->getMessage(), ENT_QUOTES) .
         '<br>';
        }
?>