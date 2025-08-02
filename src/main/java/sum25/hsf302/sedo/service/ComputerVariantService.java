package sum25.hsf302.sedo.service;

import sum25.hsf302.sedo.pojo.ComputerVariant;

import java.util.List;

public interface ComputerVariantService {
    ComputerVariant save(ComputerVariant variant);
    List<ComputerVariant> findAll();
    ComputerVariant findById(Long id);
    void deleteById(Long id);
    List<ComputerVariant> findByComputerId(Long computerId);
}
