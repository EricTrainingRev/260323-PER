# JMeter Remote Testing Setup

## Setup Test App
1. connect to your vm that will host the test app
2. download the app (typically a git clone, or similar version control download)
3. Build and run the app

## Setup Remote Server
1. download test engine (JMeter will be used for this example)
    - `wget https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.6.3.tgz`
2. unpack the engine if needed
    - `tar -xf https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.6.3.tgz`
3. update any configs so the engine can connect to its controller and the test app
    - see `server.properties` for minimal JMeter server config
4. run the engine
    - `./jmeter-server -q server.properties`

## Setup Controller
1. configure properties so your controller can connect to your remote engine
    - see `controller.properties` for minimal JMeter controller config
2. start the controller with the configs enabled
    - `./jmeter -q controller.properties`
3. setup your test and then run it through the remote engine