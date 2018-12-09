package vn.com.upload.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.com.upload.services.AgentService;
import vn.com.upload.viewmodels.Agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NhanVT3
 */
@Controller
public class AgentController {
    @Autowired
    private AgentService agentService;

    @RequestMapping(value = "/upload/get-agents", method = RequestMethod.GET)
    public @ResponseBody
    List<Agent> getProducts() {
        return agentService.findAll();
    }

    @RequestMapping(value = "/upload/count-agents", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Integer> countProducts() {
        Map<String, Integer> result = new HashMap<>();
        result.put("count", agentService.findAll().size());
        return result;
    }
}
