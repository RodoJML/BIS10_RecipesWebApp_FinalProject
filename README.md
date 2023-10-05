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
http://148.100.76.215:8080/BIS10_FinalProject (Link down, will be updated soon)

To use all functionalities please register a new user or use the following test user:
user: rodojml@me.com password: '123'


## Database Entity Relation UML.
![BaseDatosUML](https://github.com/RodoJML/BIS10_RecipesWebApp_FinalProject/assets/63088555/69ede605-c74b-4a98-b2f1-96d485293dd7)


# How I hosted the website on a Linux server

## Creating the Linux Server 

### Step 1
Activated a free trial account at 
        
        https://linuxone.cloud.marist.edu/#/

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

## Prep the Linux Server with all Apps needed
All steps below are expected to be run in the linux server (RED HAT 8.7), once connected. Some commands might be slightly different if running a different linux distribution.

### Step 1 
Install Java command
        
        sudo dnf install java

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
<br/> 

# Setup Tomcat Server

### Step 1
Create and setup tomcat users.

        vim /opt/tomcat/conf/tomcat-users.xml

And paste the following lines between the '<tomcat-users>' tags.

        <!-- user manager can access only manager section --> 
        <role rolename="manager-gui"/> 
        <user username="manager" password="_SECRET_PASSWORD_" roles="manager-gui" /> 

        <!-- user admin can access manager and admin section both --> 
        <role rolename="admin-gui" /> 
        <user username="admin" password="_SECRET_PASSWORD_" roles="manager-gui,admin-gui" />


### Step 2
Enable Manager and Host Manager for remote IP access.

        Manager: vim /opt/tomcat/webapps/manager/META-INF/context.xml
        HostManager: vim /opt/tomcat/webapps/host-manager/META-INF/context.xml
        
Edit both of the above files one by one and add your IP address or range of IP. You can also totally comment on these entries to allow all ip connections.


### Step 3
In your project code under "dist" folder you'll find a ".war" file. <br/>
Move your project .war file to "/opt/tomcat/webapps" folder

📂 Use this command to transfer file from Local computer to Remote Linux
            
            scp -i /Users/rodo/Documents/Developer/Linux/LinuxOne/rmeneses1.pem /Users/rodo/Documents/Developer/Java/Projects/BIS10_FinalProject/dist/BIS10_FinalProject.war linux1@123.123.123.123:~/

⚠️ Tomcat will automcatically detect the file and create a new folder for the environment

### Step 4
Create a Tomcat "start" script 

        sudo vim /etc/systemd/system/tomcat.service

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

### Step 5
Add permissions to the tomcat scripts
        
        sudo chmod +x /opt/tomcat/bin/startup.sh
        sudo chmod +x /opt/tomcat/bin/shutdown.sh

⚠️ To make sure tomcat is running run this command

        /opt/tomcat/bin/startup.sh

### Step 6
Edit the server.xml

        vim /opt/tomcat/conf/server.xml

Add between the server tags

    <Context path="/recipesApp" docBase="/var/lib/tomcat8/webapps/recipesApp">
        <Resource name="jdbc/recipesApp" auth="Container"
        type="javax.sql.DataSource"
        username="recipesApp"
        password="recipespassword"
        driverClassName="com.mysql.jdbc.Driver"
        url="jdbc:mysql://localhost:3306/recipesApp"/>
    </Context>

This will create a new context for your Java web application with the path “/myapp” and the docBase set to the directory you created earlier. It also includes a database resource that you can use to connect to a MySQL database.

# Going back to our server config... 

### Step 7
Configure firewall to allow incoming connections 

    sudo systemctl status firewalld

If not running, then run

    sudo systemctl enable firewalld
    sudo systemctl start firewalld

Setup ports ("Success" should be printed for each command)

    sudo firewall-cmd --zone=public --permanent --add-service=http
    sudo firewall-cmd --zone=public --permanent --add-port 8080/tcp
    sudo firewall-cmd --reload

Check all ports enabled
    
    firewall-cmd --list-all
    
More info about this can be found here: 
https://linuxconfig.org/redhat-8-open-and-close-ports

### Step 8
Reload system daemon

        sudo firewall-cmd --reload
        sudo systemctl daemon-reload


### Step 9 
Install the database

        sudo dnf install mysql-server
        systemctl start mysqld
        systemctl status mysqld 

## Step 10
Run this command to reveal the default sql root password

        sudo grep 'temporary password' /var/log/mysql/mysqld.log


# Setup MySQL

### Step 1 
Acces the SQL command line.
Default password is empty, just hit enter. 

        mysql -uroot -p

### Step 2
Set a new password inmediatedly 
        
        ALTER USER 'root'@'localhost' IDENTIFIED BY 'myrjmlcommonpassword';

### Step 3
Now create a separate mysql user for your app (so we don’t use root)

        CREATE USER 'recipesApp'@'localhost' IDENTIFIED BY 'recipespassword';

### Step 4
Create the schema for our app, this case is "BIS10_DB2"

        CREATE DATABASE BIS10_DB2;	
        SHOW DATABASES;

## Step 5
Give permissions to the new user 

        GRANT ALL PRIVILEGES ON BIS10_DB2.* TO 'recipesApp'@'localhost';

or 

        GRANT ALL PRIVILEGES ON BIS10_DB2.* TO 'recipesApp'@'localhost' IDENTIFIED BY 'myrjmlcommonpassword';


## Step 6
Open your database 

        USE BIS10_DB2 

Add all your tables

