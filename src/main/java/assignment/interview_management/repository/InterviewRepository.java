package assignment.interview_management.repository;

import assignment.interview_management.dto.GetAllInterviewQuery;
import assignment.interview_management.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query(value = "SELECT i.id, j.title, c.full_name AS candidateName,  a1.full_name AS interviewerName, " +
            "a2.full_name AS recruiterName, CONCAT(DATE_FORMAT(i.schedule_date, '%d/%m/%Y'), ' ', " +
            "DATE_FORMAT(i.from_hour, '%H:%i'), ' - ', DATE_FORMAT(i.to_hour, '%H:%i')) AS schedule, c.status " +
            "FROM interview i " +
            "INNER JOIN job j ON i.job_id = j.id " +
            "INNER JOIN candidate c ON i.candidate_id = c.id " +
            "INNER JOIN account a1 ON i.interviewer_id = a1.id " +
            "INNER JOIN account a2 ON i.recruiter_id = a2.id " +
            "WHERE " +
            "(UPPER(j.title) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(c.full_name) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(a1.full_name) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(a2.full_name) LIKE UPPER(CONCAT('%', :search, '%'))) " +
            "ORDER BY " +
            "i.updated_date DESC " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<GetAllInterviewQuery> getAllInterviews(@Param("search") String search,
                                                @Param("limit") Integer limit,
                                                @Param("offset") Integer offset);

    @Query(value = "SELECT COUNT(1) " +
            "FROM interview i " +
            "INNER JOIN job j ON i.job_id = j.id " +
            "INNER JOIN candidate c ON i.candidate_id = c.id " +
            "INNER JOIN account a1 ON i.interviewer_id = a1.id " +
            "INNER JOIN account a2 ON i.recruiter_id = a2.id " +
            "WHERE " +
            "(UPPER(j.title) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(c.full_name) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(a1.full_name) LIKE UPPER(CONCAT('%', :search, '%')) " +
            "OR UPPER(a2.full_name) LIKE UPPER(CONCAT('%', :search, '%')))", nativeQuery = true)
    Integer countInterview(@Param("search") String search);
}
