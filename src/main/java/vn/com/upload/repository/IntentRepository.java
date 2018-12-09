package vn.com.upload.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.com.upload.viewmodels.Intent;

/**
 * @author NhanVT3
 */
@Repository
public interface IntentRepository extends MongoRepository<Intent, String> {
    Intent findByNameLike(String name);
}
