package com.cubmask.distributed_startup;

import jakarta.annotation.PostConstruct;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartupCoordinator {

    private static final String LOCK_PATH = "/startup_lock";
    private static final String STARTUP_FLAG = "/startup_completed";

    @Autowired
    private CuratorFramework curatorFramework;

    @PostConstruct
    public void coordinateStartup() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, LOCK_PATH);

        try {
            lock.acquire();

            if (curatorFramework.checkExists().forPath(STARTUP_FLAG) == null) {
                System.out.println("We are started!");
                curatorFramework.create().forPath(STARTUP_FLAG);
            }
        } finally {
            lock.release();
        }
    }
}
