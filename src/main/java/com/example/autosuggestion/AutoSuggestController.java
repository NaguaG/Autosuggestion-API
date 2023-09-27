package com.example.autosuggestion;

import com.redis.om.spring.autocomplete.Suggestion;
import com.redis.om.spring.repository.query.autocomplete.AutoCompleteOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin( //
        methods = { POST, GET, OPTIONS, DELETE, PATCH }, //
        maxAge = 3600, //
        allowedHeaders = { //
                "x-requested-with", "origin", "content-type", "accept", "accept-patch" //
        }, //
        origins = "*" //
)
@RestController
@RequestMapping("/autosuggestion")
public class AutoSuggestController {
    @Autowired
    private AutoSuggestRepository autoSuggestRepository;

    @GetMapping("/search/{q}")
    public List<Suggestion> query(@PathVariable("q") String query) {
        List<Suggestion> suggestions = autoSuggestRepository.autoCompleteName(query, AutoCompleteOptions.get().withPayload());
        return suggestions;
    }
}
