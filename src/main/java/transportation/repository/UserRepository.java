package transportation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import transportation.model.User;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByExternalIdAndDeletionDateIsNull(UUID externalId);
    Optional<User> findByPassport(String passport);
    Optional<User> findByExternalId(UUID externalId);
    Page<User> getAllByDeletionDateIsNull(Pageable pageable);
    Page<User> getAllByDeletionDateIsNullAndFirstNameContainsIgnoreCaseAndLastNameContainsIgnoreCase(Pageable pageable, String firstName, String lastName);
    Page<User> getAllByDeletionDateIsNullAndFirstNameContainsIgnoreCase(Pageable pageable, String firstName);
    Page<User> getAllByDeletionDateIsNullAndLastNameContainsIgnoreCase(Pageable pageable, String lastName);

    @Modifying
    @Transactional
    @Query("update User u set u.firstName = :firstname, u.lastName = :lastname, u.patronymic = :patronymic," +
            "u.passport = :passport, u.issueDate = :issueDate, u.issuePlace = :issuePlace, u.lastUpdateDate = CURRENT_TIMESTAMP" +
            " where u.externalId = :externalId")
    void update(@Param("externalId") UUID externalId, @Param("firstname") String firstname, @Param("lastname") String lastname,
                @Param("patronymic") String patronymic, @Param("passport") String passport,
                @Param("issueDate") LocalDate issueDate, @Param("issuePlace") String issuePlace);

    @Modifying
    @Transactional
    @Query("update User u set u.deletionDate = CURRENT_TIMESTAMP where u.externalId = :externalId")
    void delete(@Param("externalId") UUID externalId);

    @Modifying
    @Transactional
    @Query("update User u set u.deletionDate = CURRENT_TIMESTAMP")
    void deleteAll();

    @Modifying
    @Transactional
    @Query("update User u set u.deletionDate = null where u.externalId = :externalId")
    void reestablish(@Param("externalId") UUID externalId);
}
