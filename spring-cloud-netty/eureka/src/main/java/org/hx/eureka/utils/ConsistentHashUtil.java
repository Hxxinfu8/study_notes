package org.hx.eureka.utils;

import com.alibaba.fastjson.JSONArray;
import org.hx.eureka.dto.ConsistentHashCircleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一致性hash工具类
 * @author hx
 */
@Component
public class ConsistentHashUtil {
    private static final String WS_SERVICE = "ws-service";

    /**
     * 虚拟节点个数
     */
    private static final int VIRTUAL_NODES = 5;

    /**
     * 真实节点
     */
    public static Set<String> realNodes = ConcurrentHashMap.newKeySet();

    /**
     * 虚拟节点
     */
    public static SortedMap<Integer, String> virtualNodes = new TreeMap<>();


    private static JmsMessagingTemplate messagingTemplate;
    @Autowired
    public void setRedisTemplate(JmsMessagingTemplate messagingTemplate) {
        ConsistentHashUtil.messagingTemplate = messagingTemplate;
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     * key哈希到一个具有2^32次方个桶的空间中，即0~(2^32)-1的数字空间中
      * @param str
     * @return
     */
    private static int getHash(String str){
        final int p = 16777619;
        int hash = (int)2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    /**
     * 增加真实节点
     * @param node
     */
    public static void addRealNodes(String node) {
        if (!node.contains(WS_SERVICE) || realNodes.contains(node)) {
            return;
        }
        realNodes.add(node);
        addVirtualNode(node);
    }

    /**
     * 增加虚拟节点
     * @param node
     */
    private static void addVirtualNode(String node) {
        for (int i = 0; i < VIRTUAL_NODES; i ++) {
            String virtualNodeName = node + "&&VN" + i;
            int hash = getHash(virtualNodeName);
            System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + hash);
            virtualNodes.put(hash, virtualNodeName);
        }
        sendJms();
    }

    /**
     * 移除真实节点
     * @param node
     */
    public static void removeNode(String node) {
        if (!node.contains(WS_SERVICE)) {
            return;
        }
        realNodes.remove(node);
        removeVirtualNode(node);
    }

    /**
     * 移除虚拟节点
     * @param node
     */
    private static void removeVirtualNode(String node) {
        virtualNodes.entrySet().removeIf(integerStringEntry -> integerStringEntry.getValue().startsWith(node));
        sendJms();
    }

    private static void sendJms() {
        ConsistentHashCircleDTO circleDTO = new ConsistentHashCircleDTO(realNodes, virtualNodes);
        messagingTemplate.convertAndSend("ConsistentHashCircle", JSONArray.toJSONString(circleDTO));
    }

}
