package vn.com.upload.services.iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.upload.repository.CorpusRepository;
import vn.com.upload.services.CorpusService;
import vn.com.upload.viewmodels.Corpus;

import java.util.List;

/**
 * @author NhanVT3
 */
@Service
public class CorpusServiceImpl implements CorpusService {
    @Autowired
    CorpusRepository repository;
    @Override
    public Corpus saveCorpus(Corpus corpus) {
        return repository.save(corpus);
    }

    @Override
    public List<Corpus> findAll() {
        return repository.findAll();
    }
}
