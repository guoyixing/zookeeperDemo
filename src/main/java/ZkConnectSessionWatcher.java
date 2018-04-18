import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * zookeeper会话重连Demo
 */
public class ZkConnectSessionWatcher implements Watcher{
    private static final String path="120.79.145.6:2181,120.79.145.6:2182,120.79.145.6:2183";
    private static final int timeOut=3000;

    public static void main(String[] args) throws Exception {
        ZooKeeper zk=new ZooKeeper(path,timeOut,new ZkConnectSessionWatcher());
        //获取sessionId和sessionPasswd用于重新连接
        long sessionId = zk.getSessionId();
        byte[] sessionPasswd = zk.getSessionPasswd();

        System.out.println("客户端开始连接zookeeper");
        System.out.println("连接状态："+zk.getState());
        new Thread().sleep(3000);
        System.out.println("连接状态："+zk.getState());
        new Thread().sleep(200);
        //开始会话重连
        System.out.println("客户端开始会话重连");
        ZooKeeper zkSession = new ZooKeeper(path,timeOut,new ZkConnectSessionWatcher(),sessionId,sessionPasswd);
        System.out.println("连接状态："+zk.getState());
        new Thread().sleep(3000);
        System.out.println("连接状态："+zk.getState());

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("接收到watch通知："+watchedEvent);
    }
}
