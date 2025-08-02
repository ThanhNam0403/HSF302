package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.hsf302.sedo.pojo.*;
import sum25.hsf302.sedo.repository.CartDetailRepository;
import sum25.hsf302.sedo.repository.CartRepository;
import sum25.hsf302.sedo.repository.PCBuidRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class PCBuidServiceImpl implements PCBuidService {
    @Autowired
    private PCBuidRepository buidRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public void savePCBuild(PCBuild pcBuild) {
        buidRepository.save(pcBuild);
    }

    public PCBuild getLatestBuildByUser(User user) {
        return buidRepository.findFirstByUserOrderByCreatedAtDesc(user)
                .orElse(null); // hoặc throw exception nếu cần
    }


}
