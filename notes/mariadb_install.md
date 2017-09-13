* run "sudo yum install -y mariadb*"
* run "sudo yum enable mariadb.service"
* run "sudo yum start mariadb.service"
* run "mysql_secure_installation"
* --specify the password to be "chen0469" for the root user
* --allow remote and disable anonimous login 
* run "mysql -uroot -pchen0469" and login as root user
* run the following sql queries:
* --CREATE USER 'root'@'%' IDENTIFIED BY 'chen0469';
* --GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
* --GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' WITH GRANT OPTION;
* --CREATE DATABASE spring_boot_slingshot CHARACTER SET utf8 COLLATE utf8_unicode_ci;
* --SET GLOBAL max_allowed_packet=268435456;
