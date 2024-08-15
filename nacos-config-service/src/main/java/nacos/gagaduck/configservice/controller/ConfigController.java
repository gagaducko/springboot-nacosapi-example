package nacos.gagaduck.configservice.controller;

import nacos.gagaduck.configservice.service.ConfigServiceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Autowired
    private ConfigServiceWrapper configService;

    @PostMapping("/create")
    public String createConfig(@RequestParam("dataId") String dataId, @RequestParam(value = "group", defaultValue = "DEFAULT_GROUP") String group, @RequestParam("content") String content) {
        try {
            boolean isPublishOk = configService.createConfig(dataId, group, content);
            return isPublishOk ? "Create Success" : "Create Fail";
        } catch (Exception e) {
            return "Create Error: " + e.getMessage();
        }
    }

    @PostMapping("/update")
    public String updateConfig(@RequestParam("dataId") String dataId, @RequestParam(value = "group", defaultValue = "DEFAULT_GROUP") String group, @RequestParam("content") String content) {
        try {
            boolean isPublishOk = configService.updateConfig(dataId, group, content);
            return isPublishOk ? "Update Success" : "Update Fail";
        } catch (Exception e) {
            return "Update Error: " + e.getMessage();
        }
    }

    @DeleteMapping("/delete")
    public String deleteConfig(@RequestParam("dataId") String dataId, @RequestParam(value = "group", defaultValue = "DEFAULT_GROUP") String group) {
        try {
            boolean isRemoveOk = configService.deleteConfig(dataId, group);
            return isRemoveOk ? "Delete Success" : "Delete Fail";
        } catch (Exception e) {
            return "Delete Error: " + e.getMessage();
        }
    }

    @GetMapping("/get")
    public String getConfig(@RequestParam("dataId") String dataId, @RequestParam(value = "group", defaultValue = "DEFAULT_GROUP") String group) {
        try {
            String content = configService.getConfig(dataId, group);
            return content != null ? content : "Config Not Found";
        } catch (Exception e) {
            return "Get Error: " + e.getMessage();
        }
    }

    @GetMapping("/list")
    public String listConfigs(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "200") int pageSize) {
        try {
            return configService.listConfigs(pageNo, pageSize);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/historyOverall")
    public String listHistoryConfigs(@RequestParam("dataId") String dataId,
                                     @RequestParam(value = "group", defaultValue = "DEFAULT_GROUP") String group,
                                     @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "1000") int pageSize) {
        try {
            System.out.println(dataId);
            System.out.println(group);
            return configService.listHistoryConfigs(dataId, group, pageNo, pageSize);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/history")
    public String listHistoryConfig(@RequestParam("dataId") String dataId,
                                    @RequestParam(value = "group", defaultValue = "DEFAULT_GROUP") String group,
                                    @RequestParam("nid") String nid) {
        try {
            return configService.listHistoryConfig(dataId, group, nid);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/listenQuery")
    public String listListenQueryConfigs(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "200") int pageSize) {
        try {
            return configService.listQueryConfigs(pageNo, pageSize);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
