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


    @Transactional
    @Query("select u.firstName, u.lastName, u.patronymic, u.creationDate, u.lastUpdateDate," +
            "u.passport, u.issueDate, u.issuePlace, u.amountOfOrders from User u where u.deletionDate = null and u.id = :id")
    User getById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.firstName = :firstname, u.lastName = :lastname, u.patronymic = :patronymic," +
            "u.passport = :passport, u.issueDate = :issueDate, u.issuePlace = :issuePlace" +
            " where u.id = :id")
    void updateUser(@Param("id") Long id, @Param("firstname") String firstname, @Param("lastname") String lastname,
                           @Param("patronymic") String patronymic, @Param("passport") String passport,
                           @Param("issueDate") LocalDate issueDate, @Param("issuePlace") String issuePlace);

    @Modifying
    @Transactional
    @Query("update User u set u.deletionDate = :deletionDate where u.id = :id")
    void deleteBy(@Param("id") Long id, @Param("deletionDate") LocalDate deletionDate);

    @Modifying
    @Transactional
    @Query("update User u set u.deletionDate = :deletionDate")
    void deleteAllBy();
}
