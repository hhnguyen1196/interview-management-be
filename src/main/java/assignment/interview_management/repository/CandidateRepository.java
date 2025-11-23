package assignment.interview_management.repository;

import assignment.interview_management.dto.CandidatesForInterviewQuery;
import assignment.interview_management.dto.GetAllCandidateQuery;
import assignment.interview_management.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query(value = "SELECT id, full_name AS fullName, email, phone_number AS phoneNumber, position, level, status " +
            "FROM candidate " +
            "WHERE (UPPER(full_name) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(email) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(phone_number) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(position) LIKE UPPER(CONCAT('%', :search, '%'))) " +
            "ORDER BY updated_date DESC " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<GetAllCandidateQuery> getAllCandidates(@Param("search") String search,
                                                @Param("limit") Integer limit,
                                                @Param("offset") Integer offset);

    @Query(value = "SELECT COUNT(1) " +
            "FROM candidate " +
            "WHERE (UPPER(full_name) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(email) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(phone_number) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(position) LIKE UPPER(CONCAT('%', :search, '%'))) " , nativeQuery = true)
    Integer countCandidate(@Param("search") String search);

    @Query(value = "SELECT id, CONCAT(full_name, ' - ', email) AS name " +
            "FROM candidate " +
            "WHERE status = 'OPEN' " +
            "UNION ALL " +
            "SELECT id, CONCAT(full_name, ' - ', email) AS name " +
            "FROM candidate " +
            "WHERE id = (SELECT candidate_id FROM interview WHERE id = :id) ", nativeQuery = true)
    List<CandidatesForInterviewQuery> findAllCandidate(@Param("id") Long id);

    @Query(value = "SELECT status FROM candidate WHERE id = :id", nativeQuery = true)
    String getStatusByCandidateId(@Param("id") Long candidateId);
}
