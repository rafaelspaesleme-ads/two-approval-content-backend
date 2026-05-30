package br.com.twoapprovalcontentbackend.infraestructure.persistences.repositories;

import br.com.twoapprovalcontentbackend.infraestructure.persistences.models.AIResponseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AIResponseRepository extends MongoRepository<AIResponseDocument, String> {
    List<AIResponseDocument> findAllByEvaluationKeyOrderByCreatedAtAsc(String evaluationKey);

    long deleteByContentIdAndEvaluationKey(String contentId, String evaluationKey);

    Optional<AIResponseDocument> findFirstByContentIdAndEvaluationKeyOrderByCreatedAtDesc(String contentId, String evaluationKey);
}
