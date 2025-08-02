package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.hsf302.sedo.pojo.ComputerVariant;
import sum25.hsf302.sedo.repository.ComputerVariantRepository;

import java.util.List;

@Service
public class ComputerVariantServiceImpl implements ComputerVariantService {

    @Autowired
    private ComputerVariantRepository variantRepository;

    @Override
    public ComputerVariant save(ComputerVariant variant) {
        return variantRepository.save(variant);
    }

    @Override
    public List<ComputerVariant> findAll() {
        return variantRepository.findAll();
    }

    @Override
    public ComputerVariant findById(Long id) {
        return variantRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        variantRepository.deleteById(id);
    }

    @Override
    public List<ComputerVariant> findByComputerId(Long computerId) {
        return variantRepository.findByComputerId(computerId);
    }
}
