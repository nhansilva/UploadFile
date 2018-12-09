package vn.com.upload.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.com.upload.services.IntentService;
import vn.com.upload.viewmodels.Intent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NhanVT3
 */
@Controller
public class IntentController {
    @Autowired
    private IntentService intentService;

    @GetMapping(value = "/upload/get-intents")
    public @ResponseBody
    List<Intent> getProducts() {
        return intentService.findAll();
    }

    @GetMapping(value = "/upload/count-intents")
    public @ResponseBody
    Map<String, Integer> countProducts() {
        Map<String, Integer> result = new HashMap<>();
        result.put("count", intentService.findAll().size());
        return result;
    }
}
