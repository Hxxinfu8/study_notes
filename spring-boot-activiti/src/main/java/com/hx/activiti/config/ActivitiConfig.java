package com.hx.activiti.config;

import org.activiti.engine.*;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 流程配置
 *
 * @author hx
 */
@Configuration
public class ActivitiConfig {
    @Value("${spring.activiti.db-identity-used}")
    private boolean dbIdentityUsed;

    private final DataSource dataSource;

    private final PlatformTransactionManager platformTransactionManager;

    public ActivitiConfig(DataSource dataSource, PlatformTransactionManager platformTransactionManager) {
        this.dataSource = dataSource;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean
    public ProcessEngine processEngine() {
        SpringProcessEngineConfiguration spec = new SpringProcessEngineConfiguration();
        spec.setDataSource(dataSource);
        spec.setTransactionManager(platformTransactionManager);
        spec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        Resource[] resources = null;
        // 启动自动部署流程
        try {
            resources = new PathMatchingResourcePatternResolver().getResources("classpath*:processes/*.bpmn");
        } catch (IOException e) {
            e.printStackTrace();
        }
        spec.setDeploymentResources(resources);
        // 设置是否使用activiti自带的用户体系
        spec.setDbIdentityUsed(dbIdentityUsed);
        return spec.buildProcessEngine();
    }

    /**
     * 工作流仓储服务
     *
     * @param processEngine
     * @return
     */

    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }


    /**
     * 工作流运行服务
     *
     * @param processEngine
     * @return
     */

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    /**
     * 工作流任务服务
     *
     * @param processEngine
     * @return
     */

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }


    /**
     * 工作流历史数据服务
     *
     * @param processEngine
     * @return
     */

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }


    /**
     * 工作流管理服务
     *
     * @param processEngine
     * @return
     */

    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }


    /**
     * 工作流唯一服务
     *
     * @param processEngine
     * @return
     */

    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }
}
