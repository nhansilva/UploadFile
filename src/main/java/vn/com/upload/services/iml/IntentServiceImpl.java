package vn.com.upload.services.iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.upload.repository.IntentRepository;
import vn.com.upload.services.IntentService;
import vn.com.upload.viewmodels.Intent;

import java.util.List;

/**
 * @author NhanVT3
 */
@Service
public class IntentServiceImpl implements IntentService {
    @Autowired
    IntentRepository repository;

    @Override
    public Intent saveIntent(Intent intent) {
        return repository.save(intent);
    }

    @Override
    public List<Intent> findAll() {
        return repository.findAll();
    }

    @Override
    public Intent findByName(String name) {
        return repository.findByNameLike(name);
    }
}
