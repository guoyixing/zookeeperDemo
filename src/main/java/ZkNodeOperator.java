import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class ZkNodeOperator implements Watcher {
    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    private ZooKeeper zooKeeper = null;

    private static final String path = "120.79.145.6:2181,120.79.145.6:2182,120.79.145.6:2183";
    private static final int timeOut = 3000;

    public ZkNodeOperator() {
    }

    public ZkNodeOperator(String connectString) {
        try {
            this.zooKeeper = new ZooKeeper(connectString, timeOut, new ZkNodeOperator());
        } catch (IOException e) {
            e.printStackTrace();
            if (zooKeeper != null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void createZkNode(String path, byte[] data, List<ACL> acls) {
        String result = "";
        /*
        path：创建的路径
        data：存储的数据
        acl：控制权限策略
            Ids.OPEN_ACL_UNSAFE-->world:anyone:cdrwa
            CREATOR_ALL_ACL-->auth:user:password:cdrwa
        createMode:节点类型是枚举类型
            PERSISTENT:持久节点啊
            PERSISTENT_SEQUENTIAL:持久顺序节点
            EPHEMERAL:临时节点
            EPHEMERAL_SEQUENTIAL:临时顺序节点
         */
        try {
            //创建一个回调节点
//            String ctx = "{'create':'success'}";
//            zooKeeper.create(path, data, acls, CreateMode.PERSISTENT, new CreateCallBack(), ctx);

            //创建节点
            result = zooKeeper.create(path,data,acls, CreateMode.PERSISTENT);
            System.out.println("创建节点：\t" + result + "\t成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("接收到watch通知：" + watchedEvent);
    }

    public static void main(String[] args) throws Exception {
        ZkNodeOperator zkNodeOperator = new ZkNodeOperator(path);
        //创建节点
//        zkNodeOperator.createZkNode("/testnode", "testnode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
        //修改节点数据
//        Stat stat = zkNodeOperator.getZooKeeper().setData("/testnode", "abac".getBytes(), 0);
//        获取当前节点的版本号
//        System.out.println(stat.getVersion());
        //删除节点
         zkNodeOperator.getZooKeeper().delete("/testnode",1);
    }
}
