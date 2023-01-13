# book-cab app
Project for cab booking
This application simulates the operations for a cab booking. Once successfully up and running it provides the APIs for <br><br>

1. Get the trip details by username - <b>/cab/api/trip/trip-username</b>
2. Get all the trips details - <b>/cab/api/trip/trips-all</b>
3. Complete a cab booking and get trip details for rider - <b>/cab/api/rider/complete-trip</b>
4. Cancel a cab booking and get refund details for rider - <b>/cab/api/rider/cancel-trip</b>
5. Book a cab and get trip details for rider - <b>/cab/api/rider/book-trip</b>
6. Get the wallet balance for rider - <b>/cab/api/rider/balance</b>
7. Get all the rider account holder details - <b>/cab/api/rider/account-all</b>
8. Get the cab details by cab number - <b>/cab/api/cab/cab-cabnumber</b>
9. Get the cab details by cab name - <b>/cab/api/cab/cab-cabname</b>
10. Get all the cab details - <b>/cab/api/cab/cab-all</b>
11. Get all locations footfall count - <b>/cab/api/location/location-footfall</b>

<h2>Description</h2>
This project uses following features to achieve the desired functionality.
<br><br>
<OL>
<LI>Spring Based Exceptional Handling</LI>
<LI>Swagger 3.0 Open API</LI>
<LI>Javax Validation</LI>
<LI>Internally Uses H2 database for simulating Run time DB</LI>
<LI>JUnit 5 Based test Cases</LI>
<LI>Jacoco Code Coverage</LI>
<LI>bcrypt for Encryption. [For one way PIN encryption]</LI>
</OL>

<h2>Getting Started</h2>

Perform the following steps to run this on local

<h3>Dependencies</h3>
Following dependencies must be installed.

<OL><br>
<LI>Maven</LI>
<LI>Java 11</LI>
</OL>

<h3>Installing</h3>
To run this repo on local
<OL><br>
<LI>clone this repo 
<b>$ git clone https://github.com/ggarg0/book-cab</b></LI> <br>

<LI>Once cloned, go inside the book-cab directory and execute <br>
<b>$ mvn clean install</b><br>
This will build the project</LI> <br>

<LI>Once project is built, Copy the property file
[https://github.com/ggarg0/book-cab/blob/main/src/main/resources/application.properties] in a
temp folder and modify the below property to put the logs at your system desired folder <br>
<b>logging.file.path= path_to_generate_log_file</b><br>
example:<br>
<b>logging.file.path= /Users/Work/book-cab/logs</b><br>
this should be a valid path.</LI><br>

<LI>to execute the project, provide the following command.<br>
<b>$ java -jar ~/book-cab/target/book-cab-0.0.1-SNAPSHOT.jar --spring.config.location=file:///Users/...../application.properties</b></LI><br>

<LI>Once the application is up and running, open chrome and put below url in the address bar.<br>
<a href="http://localhost:8888/book-cab-api.html">http://localhost:8888/book-cab-api.html</a><br>
This is swagger API docs.</LI><br>

<LI>You can try the APIs directly from there or use below curl command to test the application

  <b>Get the trip details by username </b>

curl -X 'POST' \
  'http://localhost:8888/cab/api/trip/trip-username' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "userName": "1380295942",
  "walletPin": "4945"
}'

  <b>Get all the trip details</b>

 curl -X 'GET' \
  'http://localhost:8888/cab/api/trip/trips-all' \
  -H 'accept: application/json'

  <b>Complete a cab booking and get trip details for rider</b>

curl -X 'POST' \
  'http://localhost:8888/cab/api/rider/complete-trip' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "userName": "3884373861",
  "walletPin": "0463",
  "bookingId": 0
}'

  <b>Cancel a cab booking and get refund details for rider</b>

curl -X 'POST' \
  'http://localhost:8888/cab/api/rider/cancel-trip' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "userName": "1708551873",
  "walletPin": "5613",
  "bookingId": 0
}'

  <b>Book a cab and get trip details for rider</b>

curl -X 'POST' \
  'http://localhost:8888/cab/api/rider/book-trip' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "userName": "4990013927",
  "walletPin": "4386",
  "cabName": "string",
  "pickUp": "B4",
  "drop": "I8"
}'

  <b>Get the wallet balance for rider</b>

curl -X 'POST' \
  'http://localhost:8888/cab/api/rider/balance' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "userName": "6998227213",
  "walletPin": "3080"
}'

  <b>Get all the rider account holder details</b>

curl -X 'GET' \
  'http://localhost:8888/cab/api/rider/account-all' \
  -H 'accept: application/json'

  <b>Get the cab details by cab number</b>

curl -X 'POST' \
  'http://localhost:8888/cab/api/cab/cab-cabnumber' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "cabNumber": "STF0"
}'
  
   <b>Get the cab details by cab name</b>

curl -X 'POST' \
  'http://localhost:8888/cab/api/cab/cab-cabname' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "cabName": "string"
}'
  
   <b>Get all the cab details</b>

curl -X 'GET' \
  'http://localhost:8888/cab/api/cab/cab-all' \
  -H 'accept: application/json'
  
   <b>Get all locations footfall count</b>

curl -X 'GET' \
  'http://localhost:8888/cab/api/location/location-footfall' \
  -H 'accept: application/json'

</LI>
</OL>
<h2>Code Coverage</h2>
This project uses Junit5, mockito and Jacoco to provide the unit testing. Unit tests are extensively done for service module only as of now. To view the code coverage, use the following commands<br><br>

<b>$ mvn clean test</b><br>
or<br>
<b>$ mvn clean install site --offline</b>

Code coverage reports can be found at.

* ~/book-cab/target/site/jacoco/index.html 
* ~/book-cab/target/site/jacoco/com.demo.bookcab.service.business.riderdetails/RiderDetailsServiceImpl.html 
* ~/book-cab/target/site/jacoco/com.demo.bookcab.service.business.cabdetails/CabDetailsServiceImpl.html
* ~/book-cab/target/site/jacoco/com.demo.bookcab.service.business.riderdetails/RiderDetailsServiceImpl.html
* ~/book-cab/target/site/jacoco/com.demo.bookcab.service.business.tripdetails/TripDetailsServiceImpl.html

<h2>Authors</h2>
Contributor names and contact info Gaurav Garg [ggarg.bhilai@gmail.com]

<h2>Version History</h2>
<UL>
<LI>1.0 - Initial Release</LI>
</UL>
<h2>License</h2>

This project is licensed under the MIT License - see the LICENSE.md file for details
