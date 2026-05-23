package br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories;

import br.com.twoapprovalcontentbackend.infraestructure.enums.StatusEvaluationEnum;
import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.ContentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends MongoRepository<ContentDocument, String> {
    List<ContentDocument> findAllByEvaluationKeyAndStatusOrderByCreatedAtAsc(String evaluationKey, StatusEvaluationEnum status);
}
