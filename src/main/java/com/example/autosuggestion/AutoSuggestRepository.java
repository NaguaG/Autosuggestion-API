package com.example.autosuggestion;

import com.redis.om.spring.autocomplete.Suggestion;
import com.redis.om.spring.repository.query.autocomplete.AutoCompleteOptions;
import com.redis.om.spring.repository.RedisDocumentRepository;

import java.util.List;

public interface AutoSuggestRepository extends RedisDocumentRepository<Location, String>{
    List<Suggestion> autoCompleteName(String query);
    List<Suggestion> autoCompleteName(String query, AutoCompleteOptions options);
}
