package io.github.julianobrl.ctos.dao;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.julianobrl.ctos.model.TokensEntity;

@Repository
public interface TokensRepository extends CrudRepository<TokensEntity, UUID> {}
