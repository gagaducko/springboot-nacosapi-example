package service.gagaduck.nacosdiscoveryclient.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import service.gagaduck.nacosdiscoveryclient.service.DiscoveryService;

@RestController
@CrossOrigin
@RequestMapping("/discovery")
public class DiscoveryController {

    @Resource
    private DiscoveryService discoveryService;

    @GetMapping("/test")
    public String test() {
        System.out.println("test!");
        return "test";
    }


    // 获取所有服务信息
    @GetMapping(value = "/services")
    public String getAllServices(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "200") int pageSize) throws Exception {
        System.out.println("here is services all");
        return discoveryService.getAllServices(pageNo,pageSize);
    }

    // 获取单个服务的详细信息
    @GetMapping(value = "/serviceInfo")
    public String getServiceInfo(@RequestParam(value = "serviceName") String serviceName,
                                 @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "200") int pageSize) throws Exception {
        System.out.println("here is services info," + serviceName);
        return discoveryService.getServiceByName(serviceName, pageNo, pageSize);
    }

    // 获取某个服务的订阅者信息
    @GetMapping(value = "/subscriber")
    public String getServiceSubscriber(@RequestParam(value = "serviceName") String serviceName,
                                       @RequestParam(value = "groupName") String groupName,
                                       @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "200") int pageSize) throws Exception {
        System.out.println("here is service's subscriber" + serviceName);
        System.out.println(groupName);
        return discoveryService.getServiceSubscriber(serviceName, groupName, pageNo, pageSize);
    }


}