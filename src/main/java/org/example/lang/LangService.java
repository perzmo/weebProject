package org.example.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;

class LangService {
    private final Logger logger = LoggerFactory.getLogger(LangService.class);
    private LangRepository repository;

    LangService(LangRepository repository) {
        this.repository = repository;
    }

    LangService() {
        this(new LangRepository());
    }


    List<LangDTO> findAll() {
        return repository.findAll().stream().map(LangDTO::new).collect(toList());
    }
}
