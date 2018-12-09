package vn.com.upload.services;

import vn.com.upload.viewmodels.Corpus;

import java.util.List;

/**
 * @author NhanVT3
 */
public interface CorpusService {
    Corpus saveCorpus(Corpus intent);
    List<Corpus> findAll();
}
