package com.epam.training.gen.ai.plugins;

import com.epam.training.gen.ai.vector.SimpleVectorActions;
import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class RAGPlugin {

    @Autowired
    private SimpleVectorActions simpleVectorActions;

    @DefineKernelFunction(name = "query", description = "knowledge base.")
    public String search(
            @KernelFunctionParameter(description = "text to search", name = "text") String text) {
        log.info("RAG plugin was called with query: [{}]", text);
        String result = "";
        try {
             result =  simpleVectorActions.search(text).toString();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
