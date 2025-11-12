package assignment.interview_management.service.impl;

import assignment.interview_management.dto.*;
import assignment.interview_management.entity.Job;
import assignment.interview_management.entity.JobSkill;
import assignment.interview_management.enums.JobStatusEnum;
import assignment.interview_management.exceptions.EntityNotFoundException;
import assignment.interview_management.repository.JobRepository;
import assignment.interview_management.repository.JobSkillRepository;
import assignment.interview_management.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    private JobRepository jobRepository;

    private JobSkillRepository jobSkillRepository;

    @Override
    public JobListResponse getAllJobs(JobListRequest request) {
        List<GetAllJobQuery> jobList = jobRepository.getAllJobs(request.getSearch(),
                request.getPagination().getPageSize(),
                request.getPagination().getPageSize() * request.getPagination().getPageNumber());

        List<Long> jobIdList = jobList.stream().map(GetAllJobQuery::getId).toList();

        List<GetJobSkillQuery> jobSkillList = jobRepository.getAllJobSkill(jobIdList);
        Map<Long, List<String>> jobSkillMap = jobSkillList.stream().collect(
                Collectors.groupingBy(GetJobSkillQuery::getJobId,
                        Collectors.mapping(GetJobSkillQuery::getSkill, Collectors.toList())));

        int totalElements = jobRepository.countJob(request.getSearch());

        return JobListResponse.builder()
                .jobList(jobList.stream()
                        .map(o -> JobListResponse.Job.builder()
                                .id(o.getId())
                                .title(o.getTitle())
                                .skills(jobSkillMap.get(o.getId()))
                                .level(o.getLevel())
                                .salary(o.getSalary())
                                .startDate(o.getStartDate())
                                .endDate(o.getEndDate())
                                .workingAddress(o.getWorkingAddress())
                                .description(o.getDescription())
                                .build())
                        .toList())
                .totalElements(totalElements)
                .build();
    }

    @Override
    public void createJob(SaveJobRequest request) {
        Job job = jobRepository.save(Job.builder()
                .title(request.getTitle())
                .level(request.getLevel())
                .salary(request.getSalary())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .workingAddress(request.getWorkingAddress())
                .description(request.getDescription())
                .status(JobStatusEnum.OPEN.name())
                .build());
        List<JobSkill> jobSkillList = new ArrayList<>();
        for (String skill : request.getSkills()) {
            jobSkillList.add(JobSkill.builder()
                    .jobId(job.getId())
                    .skill(skill)
                    .build());
        }
        jobSkillRepository.saveAll(jobSkillList);
    }

    @Override
    public void updateJob(SaveJobRequest request) {
        Optional<Job> jobOptional = jobRepository.findById(request.getId());
        if (jobOptional.isEmpty()) {
            throw new EntityNotFoundException("Job không tồn tai");
        }
        Job job = jobOptional.get();
        List<JobSkill> jobSkillList = jobSkillRepository.findByJobId(job.getId());
        jobSkillRepository.deleteAll(jobSkillList);
        List<JobSkill> jobSkillUpdateList = new ArrayList<>();
        for (String skill : request.getSkills()) {
            jobSkillUpdateList.add(JobSkill.builder()
                    .jobId(job.getId())
                    .skill(skill)
                    .build());
        }
        jobSkillRepository.saveAll(jobSkillUpdateList);
        job.setTitle(request.getTitle());
        job.setLevel(request.getLevel());
        job.setSalary(request.getSalary());
        job.setStartDate(request.getStartDate());
        job.setEndDate(request.getEndDate());
        job.setWorkingAddress(request.getWorkingAddress());
        job.setDescription(request.getDescription());
        jobRepository.save(job);
    }

    @Override
    public GetJobByIdResponse getJobById(Long id) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isEmpty()) {
            throw new EntityNotFoundException("Job không tồn tai");
        }
        Job job = jobOptional.get();
        List<JobSkill> jobSkillList = jobSkillRepository.findByJobId(job.getId());
        List<String> skillList = jobSkillList.stream().map(JobSkill::getSkill).toList();
        return GetJobByIdResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .skills(skillList)
                .level(job.getLevel())
                .salary(job.getSalary())
                .startDate(job.getStartDate())
                .endDate(job.getEndDate())
                .workingAddress(job.getWorkingAddress())
                .description(job.getDescription())
                .build();
    }

    @Override
    public void deleteJobById(Long id) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isEmpty()) {
            throw new EntityNotFoundException("Job không tồn tai");
        }
        jobSkillRepository.deleteByJobId(id);
        jobRepository.deleteById(id);
    }
}
