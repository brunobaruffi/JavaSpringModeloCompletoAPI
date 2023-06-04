package project.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import project.domain.entity.MongoTest;
@Repository
public interface MongoRepo extends MongoRepository<MongoTest, String> {

}
