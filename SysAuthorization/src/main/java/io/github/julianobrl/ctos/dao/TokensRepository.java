package io.github.julianobrl.archtecture.dao;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.julianobrl.archtecture.model.TokensEntity;

@Repository
public interface TokensRepository extends CrudRepository<TokensEntity, UUID> {}
