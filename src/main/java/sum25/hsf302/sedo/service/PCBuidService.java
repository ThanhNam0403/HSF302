package sum25.hsf302.sedo.service;

import org.springframework.stereotype.Service;
import sum25.hsf302.sedo.pojo.PCBuild;
import sum25.hsf302.sedo.pojo.User;

public interface PCBuidService {
    void savePCBuild(PCBuild pcBuild);
    public PCBuild getLatestBuildByUser(User user);

}
