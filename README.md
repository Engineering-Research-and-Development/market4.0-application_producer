# MARKET 4.0 Producer Application 
The Producer Application is part of the Marker 4.0 Producer Connector. Main features:
* rest service for accepting connection request by a consumer
* DAPS interaction for getting and validating token
* producing data (FIWARE Device Data Model) to the ActiveMQ queue
* messages compliant to the IDS Information Model
* IDS Clearing House interaction (WIP) 
* IDS Broker interaction (TODO)

# How to install and run #
1. Get app from the GitHub:
	https://github.com/Engineering-Research-and-Development/market4.0-application_producer
2. Unpuck and copy in the .m2\repository the file de.7z from the folder with the name: Maven - RESOURCE
3. Import the app as Maven project in the Eclipse	
4. Maven update
5. Change in the all app the IP: 192.168.56.102 with the you IP of the Virtual Box Ubuntu.
6. Set the Tomcat server v8.5 and add on it base-producer
Port:
	Tomcat admin port: 8005
	HTTP/1.1: 8080
	AJP/1.3: 8009
7. Start the the Tomcat with market4.0-application_producer