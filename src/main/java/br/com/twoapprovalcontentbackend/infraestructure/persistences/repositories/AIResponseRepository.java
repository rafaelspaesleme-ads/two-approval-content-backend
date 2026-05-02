package br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories;

import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.AIResponseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIResponseRepository extends MongoRepository<AIResponseDocument, String> {
}
