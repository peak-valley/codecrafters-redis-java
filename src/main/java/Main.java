import com.zyf.Constant.Constants;
import com.zyf.ThreadPool;
import com.zyf.handle.Handler;
import com.zyf.cluster.ClusterInformation;
import com.zyf.cluster.Master;
import com.zyf.cluster.Slave;
import com.zyf.infomation.RedisInformation;
import com.zyf.rdb.RDBCache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
//    --port 6379 --replicaof localhost 6380
//    --port 6380
    public static void main(String[] args) throws InterruptedException {
        // --port <PORT> --replicaof <MASTER_HOST> <MASTER_PORT>
        resolve(args);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(RedisInformation.getPort());
            serverSocket.setReuseAddress(true);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client conn：IP:" + clientSocket.getInetAddress() + ", port:" + clientSocket.getPort());
                Thread.sleep(1000);
                ThreadPool.execute(() -> {
                    Handler handler = new Handler(clientSocket);
                    handler.handle();
                });
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }

    public static void resolve(String[] args) {
        if (args.length == 0) {
            return;
        }
        boolean isSlave = false;
        for (int i = 0; i < args.length; i++) {
            if ("--port".equals(args[i])) {
                try {
                    RedisInformation.setPort(Integer.parseInt(args[++i]));
                } catch (NumberFormatException e) {
                    System.out.printf("The second parameter is not a number,args[1]:" + args[i]);
                }
            } else if ("--replicaof".equalsIgnoreCase(args[i])) {
                isSlave = true;
                RedisInformation.setInfo("role", "slave");
                if (args[i + 1].startsWith("--")) {
                    continue;
                }
                //resolve master host
                ClusterInformation.put(Constants.MASTER_HOST, args[++i]);
                ClusterInformation.put(Constants.MASTER_PORT, args[++i]);
                try {
                    Slave slave = new Slave();
                    slave.init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if ("--dir".equals(args[i])) {
                RDBCache.put("dir", args[++i]);
            } else if ("--dbfilename".equals(args[i])) {
                RDBCache.put("dbfilename", args[++i]);
            }
        }

        if (!isSlave) {
            Master.init();
        }

    }
}
