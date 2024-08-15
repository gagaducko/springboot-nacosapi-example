package nacos.gagaduck.configservice.service;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

@Service
public class ConfigServiceWrapper {

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String nacosServerAddr;

    @Value("${spring.cloud.nacos.config.username}")
    private String nacosUsername;

    @Value("${spring.cloud.nacos.config.password}")
    private String nacosPassword;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    private ConfigService configService;

    @PostConstruct
    public void init() {
        try {
            // 创建配置服务的属性
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, nacosServerAddr);
            properties.setProperty(PropertyKeyConst.USERNAME, nacosUsername);
            properties.setProperty(PropertyKeyConst.PASSWORD, nacosPassword);
            properties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
            // 创建ConfigService实例
            this.configService = NacosFactory.createConfigService(properties);
            // 测试连接状态
            System.out.println(configService.getServerStatus());
        } catch (NacosException e) {
            // 处理异常
            e.printStackTrace();
        }
    }

    public boolean createConfig(String dataId, String group, String content) throws NacosException {
        return configService.publishConfig(dataId, group, content);
    }

    public boolean updateConfig(String dataId, String group, String content) throws NacosException {
        return configService.publishConfig(dataId, group, content);
    }

    public boolean deleteConfig(String dataId, String group) throws NacosException {
        return configService.removeConfig(dataId, group);
    }

    public String getConfig(String dataId, String group) throws NacosException {
        return configService.getConfig(dataId, group, 5000);
    }

    public String listConfigs(int pageNo, int pageSize) throws Exception {
        String apiUrl = nacosServerAddr + "/nacos/v1/cs/configs?dataId=&group=&appName=&config_tags=&pageNo=" + pageNo + "&pageSize=" + pageSize + "&tenant=" + namespace + "&search=blur";
        return getResponseFromUrl(apiUrl);
    }

    public String listQueryConfigs(int pageNo, int pageSize) throws Exception {
        String apiUrl = nacosServerAddr + "/nacos/v1/cs/configs?dataId=&group=&appName=&config_tags=&pageNo=" + pageNo + "&pageSize=" + pageSize + "&tenant=" + namespace + "&search=blur";
        return getResponseFromUrl(apiUrl);
    }

    public String listHistoryConfigs(String dataId, String group, int pageNo, int pageSize) throws Exception {
        String apiUrl = nacosServerAddr + "/nacos/v1/cs/history?search=accurate" + "&dataId=" + dataId + "&group=" + group + "&&pageNo=" + pageNo + "&pageSize=" + pageSize + "&tenant=" + namespace;
        return getResponseFromUrl(apiUrl);
    }

    public String listHistoryConfig(String dataId, String group, String nid) throws Exception {
        String apiUrl = nacosServerAddr + "/nacos/v1/cs/history?" + "dataId=" + dataId + "&group=" + group + "&nid=" + nid + "&tenant=" + namespace;
        return getResponseFromUrl(apiUrl);
    }

    private String getResponseFromUrl(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("username", nacosUsername);
        connection.setRequestProperty("password", nacosPassword);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
