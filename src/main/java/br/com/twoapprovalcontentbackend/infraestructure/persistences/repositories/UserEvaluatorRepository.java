package br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories;

import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.UserEvaluatorDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEvaluatorRepository extends MongoRepository<UserEvaluatorDocument, String> {
    Optional<UserEvaluatorDocument> findByEmailAndActiveTrue(String email);
}
