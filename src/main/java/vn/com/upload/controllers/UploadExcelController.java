package vn.com.upload.controllers;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.com.upload.services.AgentService;
import vn.com.upload.services.CorpusService;
import vn.com.upload.services.IntentService;
import vn.com.upload.utils.DataRow;
import vn.com.upload.utils.DataTable;
import vn.com.upload.utils.ExcelTable;
import vn.com.upload.viewmodels.Agent;
import vn.com.upload.viewmodels.Corpus;
import vn.com.upload.viewmodels.Intent;
import vn.com.upload.viewmodels.UploadEvent;

import javax.servlet.ServletException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;

import static java.util.Arrays.asList;


/**
 * @author NhanVT3
 */
@Controller
public class UploadExcelController {

    private static final Logger logger = LoggerFactory.getLogger(UploadExcelController.class);
    @Autowired
    CorpusService corpusService;
    private ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    @Autowired
    private AgentService agentService;
    @Autowired
    private IntentService intentService;
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;


    @PostMapping(value = "/upload/upload-excel")
    public @ResponseBody
    Map<String, Object> uploadExcel(
            @RequestParam("file") MultipartFile file)
            throws ServletException, IOException {

        Map<String, Object> result = new HashMap<>();

        final String label = UUID.randomUUID().toString() + ".xlsx";
        final String filepath = "/temp/" + label;
        byte[] bytes = file.getBytes();
        File fh = new File("/temp/");
        if (!fh.exists()) {
            fh.mkdir();
        }

        try {

            FileOutputStream writer = new FileOutputStream(filepath);
            writer.write(bytes);
            writer.close();

            executor.submit(() -> {
                try {
                    UploadEvent event = new UploadEvent();
                    event.setState("Uploaded filed received on server");
                    event.setEventType("start");
                    brokerMessagingTemplate.convertAndSend("/topics/event", JSON.toJSONString(event, SerializerFeature.BrowserCompatible));

                    final FileInputStream inputStream = new FileInputStream(filepath);
                    DataTable table = ExcelTable.load(() -> inputStream);
                    int rowCount = table.rowCount();
                    int numberOfAgent = 0;
                    List<Agent> nameOfAgents = new ArrayList<>();
                    List<String> phrasesOfAgents = new ArrayList<>();
                    List<Corpus> corpuses = new ArrayList<>();
                    String agentId = "";
                    String phrases = "";
                    for (int i = 0; i < rowCount; ++i) {
                        DataRow row = table.row(i);
                        String id = row.cell("ID");
                        String name = row.cell("name");
                        String phrase = row.cell("phrases");
                        String description = row.cell("description");
                        String responses = row.cell("responses");
                        String corpus = row.cell("corpuses");
                        Corpus newCorpus = new Corpus();
                        newCorpus.setIntent(phrase);
                        newCorpus.setPhrase(corpus);
                        corpuses.add(newCorpus);
                        if (!name.equals("")) {
                            agentId = name;
                            Agent agent = new Agent();
                            agent.setName(name);
                            if (!id.equals("")) {
                                agent.setId(id);
                            }
                            nameOfAgents.add(agent);
                            phrasesOfAgents.add(phrases);
                            numberOfAgent++;
                            phrases = phrase + ",";
                            if (i == rowCount - 1)
                                phrasesOfAgents.add(phrases);
                        } else {
                            if (i == rowCount - 1) {
                                phrases += phrase + ",";
                                phrasesOfAgents.add(phrases);
                            } else
                                phrases += phrase + ",";
                        }
                        Intent intent = new Intent();
                        intent.setName(phrase);
                        intent.setAgent(agentId);
                        intent.setResponses(asList(responses.split(",")));
                        intent.setDescription(description);
                        intent.setType("open");
                        intentService.saveIntent(intent);
                        event = new UploadEvent();
                        event.setState(phrases);
                        event.setEventType("progress");
                        brokerMessagingTemplate.convertAndSend("/topics/event", JSON.toJSONString(event, SerializerFeature.BrowserCompatible));
                        Thread.sleep(500L);
                    }
                    for (int i = 0; i < numberOfAgent; i++) {
                        List<String> phrasesOfAgent;
                        String phrasess = phrasesOfAgents.get(i + 1);
                        phrasesOfAgent = asList(phrasess.split(","));
                        nameOfAgents.get(i).setPhrases(phrasesOfAgent);
                        agentService.saveAgent(nameOfAgents.get(i));
                    }
                    for (int i = 0; i < corpuses.size(); i++) {
                        List<String> phrase = asList(corpuses.get(i).getPhrase().split(","));
                        String intentId = intentService.findByName(corpuses.get(i).getIntent()).getId();
                        for (int j = 0; j < phrase.size(); j++) {
                            Corpus corpus = new Corpus();
                            corpus.setIntent(intentId);
                            corpus.setPhrase(phrase.get(j));
                            corpusService.saveCorpus(corpus);
                        }

                    }
                    event = new UploadEvent();
                    event.setState("Uploaded filed deleted on server");
                    fh.delete();
                    event.setEventType("end");
                    brokerMessagingTemplate.convertAndSend("/topics/event", JSON.toJSONString(event, SerializerFeature.BrowserCompatible));

                } catch (Exception ex) {
                    logger.error("Failed on saving product", ex);
                }
            });

            result.put("success", true);
            result.put("id", label);
            result.put("error", "");
            result.put("message", "Upload Success");

            return result;
        } catch (IOException ex) {
            logger.error("Failed to process the uploaded image", ex);
            result.put("success", false);
            result.put("id", "");
            result.put("error", ex.getMessage());
            return result;
        }

    }
}
