package io.github.julianobrl.ctos.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.julianobrl.ctos.dao.TokensRepository;
import io.github.julianobrl.ctos.model.TokensEntity;

@Service
public class TokensService {
	
	@Autowired
    private TokensRepository tokensRepository;

    public TokensEntity save(TokensEntity entity) {
        return tokensRepository.save(entity);
    }


    public Optional<TokensEntity> findById(UUID id) {
        return tokensRepository.findById(id);
    }

    public Iterable<TokensEntity> findAll() {
        return tokensRepository.findAll();
    }

}
