package vn.com.upload.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.com.upload.viewmodels.Agent;

/**
 * @author NhanVT3
 */
@Repository
public interface AgentRepository extends MongoRepository<Agent, String> {
}
