package assignment.interview_management.service.impl;

import assignment.interview_management.dto.*;
import assignment.interview_management.entity.Account;
import assignment.interview_management.entity.Candidate;
import assignment.interview_management.entity.Interview;
import assignment.interview_management.entity.Job;
import assignment.interview_management.enums.AccountRole;
import assignment.interview_management.enums.CandidateStatusEnum;
import assignment.interview_management.enums.JobStatusEnum;
import assignment.interview_management.exceptions.EntityNotFoundException;
import assignment.interview_management.repository.AccountRepository;
import assignment.interview_management.repository.CandidateRepository;
import assignment.interview_management.repository.InterviewRepository;
import assignment.interview_management.repository.JobRepository;
import assignment.interview_management.service.InterviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private CandidateRepository candidateRepository;

    private AccountRepository accountRepository;

    private InterviewRepository interviewRepository;

    private JobRepository jobRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public InterviewListResponse getAllInterviews(String search, Integer page, Integer size) {
        List<GetAllInterviewQuery> interviewList = interviewRepository.getAllInterviews(search, size, page * size);
        int totalElements = interviewRepository.countInterview(search);
        return InterviewListResponse.builder()
                .interviewList(interviewList.stream()
                        .map(o -> InterviewListResponse.Interview
                                .builder()
                                .id(o.getId())
                                .title(o.getTitle())
                                .candidateName(o.getCandidateName())
                                .interviewerName(o.getInterviewerName())
                                .recruiterName(o.getRecruiterName())
                                .schedule(o.getSchedule())
                                .status(o.getStatus())
                                .build())
                        .toList())
                .totalElements(totalElements)
                .build();
    }

    @Override
    public InterviewByIdResponse getInterviewById(Long id) {
        Optional<Interview> interviewOptional = interviewRepository.findById(id);
        if (interviewOptional.isEmpty()) {
            throw new EntityNotFoundException("Lịch phỏng vấn không tồn tai");
        }
        Interview interview = interviewOptional.get();
        return InterviewByIdResponse.builder()
                .id(interview.getId())
                .jobId(interview.getJobId())
                .candidateId(interview.getCandidateId())
                .interviewerId(interview.getInterviewerId())
                .recruiterId(interview.getRecruiterId())
                .scheduleDate(interview.getScheduleDate())
                .fromHour(interview.getFromHour().format(FORMATTER))
                .toHour(interview.getToHour().format(FORMATTER))
                .meetingId(interview.getMeetingId())
                .build();
    }

    @Override
    public void createInterview(SaveInterviewRequest request) {
        Optional<Job> jobOptional = jobRepository.findById(request.getJobId());
        if (jobOptional.isEmpty()) {
            throw new EntityNotFoundException("Công việc không tồn tai");
        }
        Job job = jobOptional.get();
        if (job.getStatus().equals(JobStatusEnum.CLOSED.name())) {
            throw new EntityNotFoundException("Công việc đã bị đóng");
        }
        Optional<Candidate> candidateOptional = candidateRepository.findById(request.getCandidateId());
        if (candidateOptional.isEmpty()) {
            throw new EntityNotFoundException("Ứng viên không tồn tai");
        }
        Optional<Account> accountInterviewerOptional = accountRepository.findById(request.getInterviewerId());
        if (accountInterviewerOptional.isEmpty()) {
            throw new EntityNotFoundException("Người phỏng vấn tồn tai");
        }
        Optional<Account> accountRecruiterOptional = accountRepository.findById(request.getRecruiterId());
        if (accountRecruiterOptional.isEmpty()) {
            throw new EntityNotFoundException("HR không tồn tai");
        }

        job.setStatus(JobStatusEnum.IN_PROGRESS.name());
        jobRepository.save(job);
        Candidate candidate = candidateOptional.get();
        candidate.setStatus(CandidateStatusEnum.WAITING_FOR_INTERVIEW.name());
        candidateRepository.save(candidate);
        Interview interview = Interview.builder()
                .jobId(request.getJobId())
                .candidateId(request.getCandidateId())
                .interviewerId(request.getInterviewerId())
                .recruiterId(request.getRecruiterId())
                .scheduleDate(request.getScheduleDate())
                .fromHour(request.getFromHour())
                .toHour(request.getToHour())
                .meetingId(request.getMeetingId())
                .build();
        interviewRepository.save(interview);
    }

    @Override
    public void updateInterview(SaveInterviewRequest request) {
        Optional<Interview> interviewOptional = interviewRepository.findById(request.getId());
        if (interviewOptional.isEmpty()) {
            throw new EntityNotFoundException("Lịch phỏng vấn không tồn tai");
        }
        Interview interview = interviewOptional.get();
        if (!interview.getJobId().equals(request.getJobId())) {
            List<Job> jobList = new ArrayList<>();
            Optional<Job> jobOptional = jobRepository.findById(interview.getJobId());
            jobOptional.ifPresent(job -> {
                job.setStatus(JobStatusEnum.OPEN.name());
                jobList.add(job);
            });

            Optional<Job> newJobOptional = jobRepository.findById(request.getJobId());
            if (newJobOptional.isEmpty()) {
                throw new EntityNotFoundException("Công việc chỉnh sửa không tồn tai");
            }
            Job job = newJobOptional.get();
            if (job.getStatus().equals(JobStatusEnum.CLOSED.name())) {
                throw new EntityNotFoundException("Công việc chỉnh sửa đã bị đóng");
            }
            job.setStatus(JobStatusEnum.IN_PROGRESS.name());
            jobList.add(job);
            jobRepository.saveAll(jobList);
            interview.setJobId(request.getJobId());
        }

        if (!interview.getCandidateId().equals(request.getCandidateId())) {
            List<Candidate> candidateList = new ArrayList<>();
            Optional<Candidate> candidateOptional = candidateRepository.findById(interview.getCandidateId());
            candidateOptional.ifPresent(candidate -> {
                candidate.setStatus(CandidateStatusEnum.OPEN.name());
                candidateList.add(candidate);
            });

            Optional<Candidate> newCandidateOptional = candidateRepository.findById(request.getCandidateId());
            if (newCandidateOptional.isEmpty()) {
                throw new EntityNotFoundException("Ứng viên chỉnh sửa không tồn tai");
            }
            Candidate candidate = newCandidateOptional.get();
            candidate.setStatus(CandidateStatusEnum.WAITING_FOR_INTERVIEW.name());
            candidateList.add(candidate);
            candidateRepository.saveAll(candidateList);
            interview.setCandidateId(request.getCandidateId());
        }
        interview.setInterviewerId(request.getInterviewerId());
        interview.setRecruiterId(request.getRecruiterId());
        interview.setScheduleDate(request.getScheduleDate());
        interview.setFromHour(request.getFromHour());
        interview.setToHour(request.getToHour());
        interview.setMeetingId(request.getMeetingId());
        interviewRepository.save(interview);
    }

    @Override
    public InterviewOptionsResponse getInterviewOptions(Long id) {
        List<JobsForInterviewQuery> jobForInterviewList = jobRepository.findAllJob(id);
        List<CandidatesForInterviewQuery> candidateForInterviewList = candidateRepository.findAllCandidate(id);
        List<UsersForInterviewQuery> accountList = accountRepository.findAllAccount();
        List<InterviewOptionsResponse.Job> jobList = jobForInterviewList.stream()
                .map(o -> InterviewOptionsResponse.Job.builder()
                        .id(o.getId())
                        .title(o.getTitle())
                        .build())
                .toList();
        List<InterviewOptionsResponse.Candidate> candidateList = candidateForInterviewList.stream()
                .map(o -> InterviewOptionsResponse.Candidate
                        .builder()
                        .id(o.getId())
                        .name(o.getName())
                        .build())
                .toList();
        List<InterviewOptionsResponse.Interviewer> interviewerAccountList = new ArrayList<>();
        List<InterviewOptionsResponse.Recruiter> recruiterAccountList = new ArrayList<>();
        accountList.forEach(account -> {
            if (AccountRole.RECRUITER.name().equals(account.getRole())) {
                recruiterAccountList.add(InterviewOptionsResponse.Recruiter
                        .builder()
                        .id(account.getId())
                        .name(account.getName())
                        .build());
            } else if (AccountRole.INTERVIEWER.name().equals(account.getRole())) {
                interviewerAccountList.add(InterviewOptionsResponse.Interviewer
                        .builder()
                        .id(account.getId())
                        .name(account.getName())
                        .build());
            }
        });
        return InterviewOptionsResponse.builder()
                .candidateList(candidateList)
                .interviewerList(interviewerAccountList)
                .recruiterList(recruiterAccountList)
                .jobList(jobList)
                .build();
    }
}
