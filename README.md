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

And paste the following lines between the '<tomcat-users> </tomcat-users>' tags.

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
Create an instance for your webapp, this is the environment where the app will run. 

        mkdir /opt/tomcat/webapps/recipesApp


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
