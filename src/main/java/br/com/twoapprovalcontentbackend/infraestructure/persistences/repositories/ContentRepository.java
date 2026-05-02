package br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories;

import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends MongoRepository<ContentDocument, String> {
}
