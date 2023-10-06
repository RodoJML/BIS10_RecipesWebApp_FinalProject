# BIS10_RecipesProject
A website to share food recipes, you can add, update or delete them. 
Each recipe can hold several steps and ingredients, users can see others recipes (If set visible), but can only update their own.

Project made using Java Web Stack technologies:

    -Java Server Faces
    -HTML
    -mySQL
    -Tomcat
    -Linux

User interface made using Java Prime Faces
https://www.primefaces.org/

Hosted on a linux1 server here:
http://148.100.77.243:8080/BIS10_FinalProject/

To use all functionalities please register a new user or use the following test user:
user: rodojml@me.com password: '123'


## Database Entity Relation UML.
![BaseDatosUML](https://github.com/RodoJML/BIS10_RecipesWebApp_FinalProject/assets/63088555/69ede605-c74b-4a98-b2f1-96d485293dd7)


# How I hosted the website on a Linux server

![linux_logo_icon_171222](https://github.com/RodoJML/BIS10_RecipesWebApp_FinalProject/assets/63088555/1365e919-ef2b-4ee6-85a8-ab1afe9ef511)

## Creating the Linux Server 

### Step 1
Activated a free trial account at 
        
        https://linuxone.cloud.marist.edu/#/
        Note to self: I used my newpaltz email and password for linuxone

 ### Step 2
Select Manage Instances >> Create one
Create a SSH Key Pair (Downloads a file .pem extension)
In the same setup wizard make sure you select the SSH Key just created.
From the linuxone website get the IP Address to use it later to connect via SSH

### Step 3
On your computer, change permission of .pem file using the following command
        
        chmod 600 /Users/rodo/Documents/menesesr1.pem

### Step 4 
Connect via ssh putting the .pem file first and then ssh command as follows
        
        ssh -i /Users/rodo/Documents/menesesr1.pem linux1@123.123.123.123

<br/><br/>

# Prep the Linux Server with all Apps needed
All steps below are expected to be run in the linux server (RED HAT 8.7), once connected. Some commands might be slightly different if running a different linux distribution.

### Step 1 
Install Java command
        
        sudo dnf install java

Make sure you see "Complete!" to ensure successfull installation. 

### Step 2
⚠️ DO NOT run your server from the root user.<br/>For security reasons, create a separate user for Tomcat server. In this case user was name "tomcat" but you can name it different. 

        sudo useradd -m -d /opt/tomcat -U -s /bin/false tomcat

### Step 3
Install wget if this is not preinstalled on the server. 

        sudo dnf install wget
        
### Step 4
Download and Install Tomcat (https://tomcat.apache.org/).<br/>
You might need to go to their wbsite and get the latest "tar.gz" link address. 

        wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.70/bin/apache-tomcat-9.0.70.tar.gz

### Step 5
Extract downloaded file and then move it to user created for sec reasons (Step 2).

        tar xzf apache-tomcat-9.0.70.tar.gz
        sudo mv apache-tomcat-9.0.70/* /opt/tomcat/

### Step 6
Set proper ownership permissions

        sudo chown -R tomcat:tomcat /opt/tomcat/
<br/><br/>

# Setup Tomcat Server
### Step 1
Enable Manager and Host Manager for remote IP access.

        vim /opt/tomcat/webapps/manager/META-INF/context.xml
        vim /opt/tomcat/webapps/host-manager/META-INF/context.xml

<img width="682" alt="image" src="https://github.com/RodoJML/BIS10_RecipesWebApp_FinalProject/assets/63088555/38ff7fbe-55ea-4c2b-b422-dd77b2dda7f3">

Edit both of the above files one by one and add your IP address or range of IP.

### Step 2
Create and setup tomcat users.

        vim /opt/tomcat/conf/tomcat-users.xml

And paste the following lines between the '<tomcat-users>' tags.

        <!-- user manager can access only manager section -->
        <role rolename="manager-gui" />
        <user username="manager" password="_SECRET_PASSWORD_" roles="manager-gui" />
        
        <!-- user admin can access manager and admin section both -->
        <role rolename="admin-gui" />
        <user username="admin" password="_SECRET_PASSWORD_" roles="manager-gui,admin-gui" />


### Step 3
Create a Tomcat "start" script 
Add lines below:

        [Unit]
        Description=Tomcat 9
        After=network.target 

        [Service] 
        Type=forking 
        User=tomcat
        Group=tomcat 
        
        Environment="JAVA_HOME=/usr/lib/jvm/jre"
        Environment="JAVA_OPTS=-Djava.security.egd=file:///dev/urandom"
        Environment="CATALINA_BASE=/opt/tomcat"
        Environment="CATALINA_HOME=/opt/tomcat"
        Environment="CATALINA_PID=/opt/tomcat/temp/tomcat.pid"
        Environment="CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC"
        
        ExecStart=/opt/tomcat/bin/startup.sh
        ExecStop=/opt/tomcat/bin/shutdown.sh
        
        [Install]
        WantedBy=multi-user.target


### Step 4
Reload daemon

        sudo systemctl daemon-reload


### Step 5
Enable and start Tomcat service

        sudo systemctl enable tomcat.service
        sudo systemctl start tomcat.service
        

### Step 6
Configure firewall to allow incoming connections 

    sudo systemctl status firewalld

If not running, then run

    sudo systemctl enable firewalld
    sudo systemctl start firewalld

Setup ports ("Success" should be printed for each command)

    sudo firewall-cmd --zone=public --permanent --add-service=http
    sudo firewall-cmd --zone=public --permanent --add-port 8080/tcp
    sudo firewall-cmd --reload
    sudo systemctl daemon-reload

Check all ports enabled
    
    firewall-cmd --list-all

✅ At this point TOMCAT should work if accessed remotely 123.123.123.123:8080
<img width="1029" alt="image" src="https://github.com/RodoJML/BIS10_RecipesWebApp_FinalProject/assets/63088555/5591bcb9-fa06-43fd-b1fe-8216e6ba1037">

Some of the steps (not all) to setup tomcat were extracted from here:
<br/>
https://linuxconfig.org/redhat-8-open-and-close-ports 
<br/>
https://tecadmin.net/install-tomcat-8-on-centos-8/

### Step 7 - Final Tomcat Step 
In your project code under "dist" folder you'll find a ".war" file. <br/>
Move your project .war file to "/opt/tomcat/webapps" folder

📂 Use this command to transfer .war file from Local computer to Remote Linux
            
            scp -i /Users/rodo/Documents/Developer/Linux/LinuxOne/rmeneses1.pem /Users/rodo/Documents/Developer/Java/Projects/BIS10_FinalProject/dist/BIS10_FinalProject.war linux1@123.123.123.123:~/

⚠️ Tomcat will automcatically detect the file and create a new folder for the environment

### _OPTIONAL ONLY IF PREVIOUS DONT WORK_
    Add permissions to the tomcat scripts and tomcat user 
        sudo chmod +x /opt/tomcat/bin/startup.sh
        sudo chmod +x /opt/tomcat/bin/shutdown.sh
        sudo sh -c 'chmod +x /opt/tomcat/bin/*.sh'

    ⚠️ To make sure tomcat is running run this command
        /opt/tomcat/bin/startup.sh

    Now to start or step the service you can use:
        sudo systemctl enable tomcat.service
        sudo systemctl start tomcat.service

<br/><br/>


# Setup MySQL
### Step 1
Install the database

        sudo dnf install mysql-server
        systemctl start mysqld
        systemctl status mysqld

### Step 2
Run this command to reveal the default sql root password

        sudo grep 'temporary password' /var/log/mysql/mysqld.log
        
### Step 3
Acces the SQL command line.
Default password is empty, just hit enter. 

        mysql -uroot -p

### Step 4
Set a new password inmediatedly 
        
        ALTER USER 'root'@'localhost' IDENTIFIED BY 'myrjmlcommonpassword';

### Step 5
Now create a separate mysql user for your app (so we don’t use root)

        CREATE USER 'recipesApp'@'localhost' IDENTIFIED BY 'recipespassword';

### Step 6
Create the schema for our app, this case is "BIS10_DB2"

        CREATE DATABASE BIS10_DB2;	
        SHOW DATABASES;

### Step 7
Give permissions to the new user 

        GRANT ALL PRIVILEGES ON BIS10_DB2.* TO 'recipesApp'@'localhost';

or 

        GRANT ALL PRIVILEGES ON BIS10_DB2.* TO 'recipesApp'@'localhost' IDENTIFIED BY 'myrjmlcommonpassword';


### Step 8
Open your database 

        USE BIS10_DB2 

### Step 9
Add all your tables to the database, add them in the order listed in file
        
        Use the createSQLtables.sql file on this repo

