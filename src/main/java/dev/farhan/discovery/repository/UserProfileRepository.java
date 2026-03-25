package dev.farhan.discovery.repository;

import dev.farhan.discovery.domain.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
}
