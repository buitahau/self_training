package owt.training.fluentd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import owt.training.fluentd.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
