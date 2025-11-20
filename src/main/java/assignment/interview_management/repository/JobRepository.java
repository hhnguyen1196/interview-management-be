package assignment.interview_management.repository;

import assignment.interview_management.dto.GetAllJobQuery;
import assignment.interview_management.dto.GetJobSkillQuery;
import assignment.interview_management.dto.JobsForInterviewQuery;
import assignment.interview_management.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(value = "SELECT id, title, level, salary, start_date AS startDate, end_date AS endDate, " +
            "working_address, description, status " +
            "FROM job " +
            "WHERE (UPPER(title) LIKE UPPER(CONCAT('%', :search, '%'))) " +
            "ORDER BY updated_date DESC " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<GetAllJobQuery> getAllJobs(@Param("search") String search,
                                    @Param("limit") Integer limit,
                                    @Param("offset") Integer offset);

    @Query(value = "SELECT job_id AS jobId, skill " +
            "FROM job_skill WHERE job_id IN :jobIdList", nativeQuery = true)
    List<GetJobSkillQuery> getAllJobSkill(@Param("jobIdList") List<Long> jobIdList);

    @Query(value = "SELECT COUNT(1) " +
            "FROM job " +
            "WHERE (UPPER(title) LIKE UPPER(CONCAT('%', :search, '%'))) ", nativeQuery = true)
    Integer countJob(@Param("search") String search);

    @Query(value = "SELECT id, title " +
            "FROM job WHERE status = 'OPEN' " +
            "UNION ALL " +
            "SELECT id, title " +
            "FROM job WHERE id = (SELECT job_id FROM interview WHERE id = :id) ", nativeQuery = true)
    List<JobsForInterviewQuery> findAllJob(@Param("id") Long id);
}
