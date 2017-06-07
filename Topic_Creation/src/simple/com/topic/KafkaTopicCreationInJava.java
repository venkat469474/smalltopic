package simple.com.topic;

import java.util.List;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.kafka.common.requests.MetadataResponse.TopicMetadata;
import org.apache.zookeeper.ZooKeeper;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
////hello this is topic creation.
public class KafkaTopicCreationInJava
{
    public static void main(String[] args) throws Exception {
        ZkClient zkClient = null;
        ZkUtils zkUtils = null;
        try {
            String zookeeperHosts = "hostip:2181"; // If multiple zookeeper then -> String zookeeperHosts = "ip1:2181,ip2:2181";
            int sessionTimeOutInMs = 15 * 1000; // 15 secs
            int connectionTimeOutInMs = 10 * 1000; // 10 secs

            zkClient = new ZkClient(zookeeperHosts, sessionTimeOutInMs, connectionTimeOutInMs, ZKStringSerializer$.MODULE$);
            zkUtils = new ZkUtils(zkClient, new ZkConnection(zookeeperHosts), false);

            String topicName = "testTopic_srinivas";
           // int noOfPartitions = 2;
           // int noOfReplication = 2;
            //Properties topicConfiguration = new Properties();

//            AdminUtils.createTopic(zkUtils, topicName, noOfPartitions, noOfReplication, topicConfiguration, RackAwareMode.Disabled$.MODULE$);
            TopicMetadata metadata=AdminUtils.fetchTopicMetadataFromZk(topicName, zkUtils);
//            System.out.println("Topic Exists: "+AdminUtils.topicExists(zkUtils, topicName));
            System.out.println("Partition count:"+metadata.partitionMetadata().get(1).partition());
//            System.out.println("Kafka Topic Created"); 
 
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zkClient != null) {
                zkClient.close();
            }
        }
    }
    
    public static void printBrokerHosts() throws Exception {
        ZooKeeper zk = new ZooKeeper("localhost:2181", 10000, null);
        List<String> ids = zk.getChildren("/brokers/ids", false);
        for (String id : ids) {
            String brokerInfo = new String(zk.getData("/brokers/ids/" + id, false, null));
            System.out.println(id + ": " + brokerInfo);
        }
    }
//    1: {"jmx_port":-1,"timestamp":"1428512949385","host":"192.168.0.11","version":1,"port":9093}
//    2: {"jmx_port":-1,"timestamp":"1428512955512","host":"192.168.0.11","version":1,"port":9094}
//    3: {"jmx_port":-1,"timestamp":"1428512961043","host":"192.168.0.11","version":1,"port":9095
}
