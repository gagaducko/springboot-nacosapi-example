package service.gagaduck.nacosdiscoveryclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class DiscoveryService {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String nacosServerAddr;

    @Value("${spring.cloud.nacos.discovery.username}")
    private String nacosUsername;

    @Value("${spring.cloud.nacos.discovery.password}")
    private String nacosPassword;

    @Value("${spring.cloud.nacos.discovery.namespace}")
    private String namespace;

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

    public String getAllServices(int pageNo, int pageSize) throws Exception {
        String apiUrl = nacosServerAddr + "/nacos/v1/ns/catalog/services?hasIpCount=false&withInstances=false&pageNo=" + pageNo + "&pageSize=" + pageSize + "&serviceNameParam=&groupNameParam=&namespaceId=" + namespace;
        return getResponseFromUrl(apiUrl);
    }

    public String getServiceByName(String serviceName, int pageNo, int pageSize) throws Exception {
        String apiUrl = nacosServerAddr + "/nacos/v1/ns/catalog/instances?serviceName=" + serviceName + "&clusterName=DEFAULT&groupName=DEFAULT_GROUP&pageSize=" + pageSize + "&pageNo=" + pageNo + "&namespaceId=" + namespace;
        return getResponseFromUrl(apiUrl);
    }

    public String getServiceSubscriber(String serviceName, String groupName, int pageNo, int pageSize) throws Exception {
        String apiUrl = nacosServerAddr + "/nacos/v1/ns/service/subscribers?serviceName=" + serviceName + "&groupName=" + groupName + "&pageSize=10&pageNo=1&namespaceId=" + namespace;
        System.out.println(apiUrl);
        return getResponseFromUrl(apiUrl);
    }
}
