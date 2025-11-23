package assignment.interview_management.repository;

import assignment.interview_management.dto.GetAllAccountQuery;
import assignment.interview_management.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT id, username, full_name AS fullName, gender, email, department, is_active AS isActive " +
            "FROM account " +
            "WHERE (UPPER(username) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(full_name) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(email) LIKE UPPER(CONCAT('%', :search, '%'))) " +
            "ORDER BY updated_date DESC " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<GetAllAccountQuery> getAllAccounts(@Param("search") String search,
                                            @Param("limit") Integer limit,
                                            @Param("offset") Integer offset);

    @Query(value = "SELECT COUNT(1) " +
            "FROM account " +
            "WHERE (UPPER(username) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(full_name) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(email) LIKE UPPER(CONCAT('%', :search, '%'))) ", nativeQuery = true)
    Integer countAccount(String search);

    Optional<Account> findByUsername(String username);
}
