package org.hx.gateway.dto;

import lombok.Data;

import java.util.Set;
import java.util.SortedMap;

/**
 * 一致性hash环
 * @author hx
 */
@Data
public class ConsistentHashCircleDTO {
    /**
     * 真实节点
     */
    public Set<String> realNodes;

    /**
     * 虚拟节点
     */
    public SortedMap<Integer, String> virtualNodes;
}
