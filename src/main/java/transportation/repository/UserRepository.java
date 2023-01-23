package transportation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import transportation.model.User;
import users.UserPostDto;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndDeletionDateIsNull(Long id);

    Optional<User> findByPassport(String passport);

    Page<User> getAllByDeletionDateIsNull(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update User u set u.firstName = :firstname, u.lastName = :lastname, u.patronymic = :patronymic," +
            "u.passport = :passport, u.issueDate = :issueDate, u.issuePlace = :issuePlace, u.lastUpdateDate = CURRENT_TIMESTAMP" +
            " where u.id = :id")
    void update(@Param("id") Long id, @Param("firstname") String firstname, @Param("lastname") String lastname,
                @Param("patronymic") String patronymic, @Param("passport") String passport,
                @Param("issueDate") String issueDate, @Param("issuePlace") String issuePlace);

    @Modifying
    @Transactional
    @Query("update User u set u.deletionDate = CURRENT_TIMESTAMP where u.id = :id")
    void delete(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.deletionDate = CURRENT_TIMESTAMP")
    void deleteAll();
}
