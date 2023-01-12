package transportation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import transportation.model.User;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("update User u set u.firstName = :firstname, u.lastName = :lastname, u.patronymic = :patronymic," +
            "u.passport = :passport, u.issueDate = :issueDate, u.issuePlace = :issuePlace" +
            " where u.id = :id")
    public void updateUser(@Param("id") Long id, @Param("firstname") String firstname, @Param("lastname") String lastname,
                           @Param("patronymic") String patronymic, @Param("passport") String passport,
                           @Param("issueDate") LocalDate issueDate, @Param("issuePlace") String issuePlace);
}
