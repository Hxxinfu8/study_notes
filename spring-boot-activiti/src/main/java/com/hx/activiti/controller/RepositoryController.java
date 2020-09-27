package com.hx.activiti.controller;

import com.hx.activiti.domain.vo.ResultVO;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 仓储服务
 *
 * @author hx
 */
@RestController
@RequestMapping("/repository")
public class RepositoryController {
    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/list")
    public ResultVO listRepository(@RequestParam("name") String name) {
        DeploymentQuery query = repositoryService.createDeploymentQuery();
        List<Deployment> list =  query.deploymentNameLike("%" + name + "%").list();
        return ResultVO.success(list);
    }
}
