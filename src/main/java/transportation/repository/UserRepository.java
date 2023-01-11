package transportation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import users.UserPostRequest;

@Repository
public interface UserRepository extends JpaRepository<UserPostRequest, Long> {
}
