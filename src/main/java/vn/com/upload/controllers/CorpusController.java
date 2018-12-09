package vn.com.upload.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.com.upload.services.CorpusService;
import vn.com.upload.viewmodels.Corpus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NhanVT3
 */
@Controller
public class CorpusController {
    @Autowired
    private CorpusService corpusService;

    @RequestMapping(value = "/upload/get-corpuses", method = RequestMethod.GET)
    public @ResponseBody
    List<Corpus> getCorpuses() {
        return corpusService.findAll();
    }

    @RequestMapping(value = "/upload/count-corpuses", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Integer> countCorpuses() {
        Map<String, Integer> result = new HashMap<>();
        result.put("count", corpusService.findAll().size());
        return result;
    }
}
