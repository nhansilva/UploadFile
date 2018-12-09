package vn.com.upload.services.iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.upload.repository.AgentRepository;
import vn.com.upload.services.AgentService;
import vn.com.upload.viewmodels.Agent;

import java.util.List;

/**
 * @author NhanVT3
 */
@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    AgentRepository agentRepository;

    @Override
    public Agent saveAgent(Agent agent) {
        agentRepository.save(agent);
        return agent;
    }

    @Override
    public List<Agent> findAll() {
        return agentRepository.findAll();
    }
}
