package cit.edu.inosanto.backend.CarGo.features.users;

import cit.edu.inosanto.backend.CarGo.features.users.User;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
