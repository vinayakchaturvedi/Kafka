# FirstKafka
Getting started with kafka
Steps to download and install kafka on windows.

1. http://archive.apache.org/dist/kafka/1.0.1/kafka_2.12-1.0.1.tgz  (or any latest version -- binary not source
2. Create folder kafka in C:/
3. Extract downloaded zip under the newly created kafka folder

----Steps to install----
1. create 2 new folders inside kafka folder --- zookeeper_data and kafka logs
2. open /config/zookeeper.properties and edit datadir = zookeeper_data folder path
3. open /config/server.properties
	a. edit logs.dir = the path of kafka logs folder
	b. also edit (for single node kafka cluster)
					offsets.topic.num.partitions=1
					offsets.topic.replication.factor=1
					transaction.state.log.replication.factor=1
					transaction.state.log.min.isr=1
					min.insync.replicas=1
					default.replication.factor=1
					
4. Update path in environment variables: C:\Kafka\kafka_2.12-1.0.1\bin\windows

---- Run single node kafka cluster
1. paste in cmd --> zookeeper-server-start.bat C:\Kafka\kafka_2.12-1.0.1\config\zookeeper.properties
		this will initialize zookeeper and let it run
2. Open another cmd and paste --> kafka-server-start.bat C:\Kafka\kafka_2.12-1.0.1\config\server.properties
		this will run kafka server 
3. zookeeper-shell.bat localhost:2181 ls /brokers/ids
		this will give list of broker ids
4. kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
		this will create a kafka topic with name as test
5. kafka-console-producer.bat --broker-list localhost:9092 --topic
		this will create kafka producer and also you can send messages on to the topic
6. kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning
		this will create kafka consumer and you can also start receiving messages from the topic on this consumer
		
-------Remarks
1. How to check the configuration of all the topics in a broker ?
Windows

kafka-topics.bat --describe --zookeeper localhost:2181

2. How to check the configuration of a particular topic?
For Windows

Go to \bin\windows and then execute the below
kafka-topics.bat --describe --topic my-topic --zookeeper localhost:2181

3. How to check the list of topics
For Windows:

Go to \bin\windows and then execute the below
kafka-topics.bat --list --zookeeper localhost:2181

4. How to delete a topic?
For Windows

kafka-topics.bat --delete --zookeeper localhost:2181 --topic <topic_name>
