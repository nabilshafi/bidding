# bidding-application

# Instruction

- git clone https://github.com/nabilshafi/bidding.git

- Go to project directory

- Build project by run following command

- mvn package 

- Run project by execute following command

- java -jar target/bidding-1.0.0.jar

- Application can also be run by just importing pom.xml file in intelliJ as a new project and simply play it. 


# Endpoints

- GET  http://localhost:8080/[id]?[key=value,...]

- eg: http://localhost:8080/4?a=5&b=3

# Bidders

- Bidders address can be set inside application.yaml 

- Bidders address can also be provided as environment variable. ex: BIDDER_SERVERS:http://localhost:8081

