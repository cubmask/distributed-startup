Distributed Services using Spring Boot Framework and Apache Zookeeper

This Spring Boot application uses Apache ZooKeeper to ensure that a message "We are started!" is printed exactly once across all nodes in a distributed system, regardless of when or how many nodes start. 
It leverages ZooKeeper's ephemeral nodes to coordinate and prevent duplicate messages.

*The ZooKeeperConfig class sets up the connection to ZooKeeper using the Curator framework.

The StartupCoordinator uses a distributed lock (InterProcessMutex) to ensure that only one node performs the startup action. When a node acquires the lock, it checks if the STARTUP_FLAG exists in ZooKeeper. 
If not, it prints "We are started!" and creates the flag. Subsequent nodes will see that the flag already exists and won't print the message.
This approach handles all the distributed scenarios mentioned, including nodes starting at different times or restarting.

The DistributedStartupApplication is the entry point for the Spring Boot application.

*Zookeeper is deployed locally with the following config: 
tickTime=2000 
dataDir=/var/lib/zookeeper 
clientPort=2181 
initLimit=5 
syncLimit=2
