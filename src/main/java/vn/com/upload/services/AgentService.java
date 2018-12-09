package vn.com.upload.services;

import vn.com.upload.viewmodels.Agent;

import java.util.List;

/**
 * @author NhanVT3
 */
public interface AgentService {
    Agent saveAgent(Agent agent);
    List<Agent> findAll();
}
