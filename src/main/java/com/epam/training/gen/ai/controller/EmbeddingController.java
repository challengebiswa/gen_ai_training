package com.epam.training.gen.ai.controller;

import com.azure.ai.openai.models.EmbeddingItem;
import com.epam.training.gen.ai.vector.SimpleVectorActions;
import io.qdrant.client.grpc.Points;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/embedding")
public class EmbeddingController {

    @Autowired
    private SimpleVectorActions simpleVectorActions;

    @RequestMapping(value = "/build", method = { RequestMethod.POST})
    public List<EmbeddingItem> build(@RequestParam String text) {
        return simpleVectorActions.getEmbeddings(text);
    }

    @PostMapping("/save")
    public Map<String, String> save(@RequestParam String text) {
        Map<String, String> response = new HashMap<>();
        try {
                simpleVectorActions.processAndSaveText(text);
                response.put("result", "Text processed and saved successfully");
        } catch (Exception e) {
            log.error("Error processing embedding request", e);
            response.put("error",  e.getMessage());
        }
        return response;
    }

    @GetMapping("/search")
    public String search(@RequestParam String text) throws ExecutionException, InterruptedException {
       return simpleVectorActions.search(text).toString();
    }

    @PostMapping("/init")
    public Map<String, String> init() {
        Map<String, String> response = new HashMap<>();
        try {
            simpleVectorActions.createCollection();
            response.put("result", "Success");
        } catch (Exception e) {
            log.error("Error processing embedding request", e);
            response.put("error",  e.getMessage());
        }

        return response;
    }
}
