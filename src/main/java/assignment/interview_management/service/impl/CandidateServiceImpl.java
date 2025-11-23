package assignment.interview_management.service.impl;

import assignment.interview_management.dto.CandidateByIdResponse;
import assignment.interview_management.dto.CandidateListResponse;
import assignment.interview_management.dto.GetAllCandidateQuery;
import assignment.interview_management.dto.SaveCandidateRequest;
import assignment.interview_management.entity.Candidate;
import assignment.interview_management.entity.CandidateSkill;
import assignment.interview_management.enums.CandidateStatusEnum;
import assignment.interview_management.exceptions.EntityNotFoundException;
import assignment.interview_management.repository.CandidateRepository;
import assignment.interview_management.repository.CandidateSkillRepository;
import assignment.interview_management.service.AsyncService;
import assignment.interview_management.service.CandidateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    private final CandidateSkillRepository candidateSkillRepository;

    private final AsyncService asyncService;

    private final String uploadDir;

    public CandidateServiceImpl(
            CandidateRepository candidateRepository,
            CandidateSkillRepository candidateSkillRepository,
            AsyncService asyncService,
            @Value("${upload-dir}") String uploadDir) {
        this.candidateRepository = candidateRepository;
        this.candidateSkillRepository = candidateSkillRepository;
        this.asyncService = asyncService;
        this.uploadDir = uploadDir;
    }

    @Override
    public CandidateListResponse getAllCandidates(String search, Integer page, Integer size) {
        List<GetAllCandidateQuery> candidateList = candidateRepository.getAllCandidates(search, size, page * size);
        int totalElements = candidateRepository.countCandidate(search);
        return CandidateListResponse.builder()
                .candidateList(candidateList.stream()
                        .map(o -> CandidateListResponse.Candidate
                                .builder()
                                .id(o.getId())
                                .fullName(o.getFullName())
                                .email(o.getEmail())
                                .phoneNumber(o.getPhoneNumber())
                                .position(o.getPosition())
                                .level(o.getLevel())
                                .status(o.getStatus())
                                .build())
                        .toList())
                .totalElements(totalElements)
                .build();
    }

    @Override
    public void createCandidate(SaveCandidateRequest request) {
        MultipartFile file = request.getCvAttachment();
        String filePath = uploadFile(file);
        Candidate candidate = candidateRepository.save(Candidate.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .cvFilePath(filePath)
                .filename(file.getOriginalFilename())
                .position(request.getPosition())
                .yearOfExperience(request.getYearOfExperience())
                .level(request.getLevel())
                .note(request.getNote())
                .status(CandidateStatusEnum.OPEN.name())
                .build());
        List<CandidateSkill> candidateSkillList = new ArrayList<>();
        for (String skill : request.getSkills()) {
            candidateSkillList.add(CandidateSkill.builder()
                    .candidateId(candidate.getId())
                    .skill(skill)
                    .build());
        }
        candidateSkillRepository.saveAll(candidateSkillList);
    }

    @Override
    public void updateCandidate(SaveCandidateRequest request) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(request.getId());
        if (candidateOptional.isEmpty()) {
            throw new EntityNotFoundException("Ứng viên không tồn tai");
        }
        Candidate candidate = candidateOptional.get();
        String filePathToDelete = candidate.getCvFilePath();
        List<CandidateSkill> candidateSkillList = candidateSkillRepository.findByCandidateId(candidate.getId());
        candidateSkillRepository.deleteAll(candidateSkillList);
        List<CandidateSkill> candidateSkillUpdateList = new ArrayList<>();
        for (String skill : request.getSkills()) {
            candidateSkillUpdateList.add(CandidateSkill.builder()
                    .candidateId(candidate.getId())
                    .skill(skill)
                    .build());
        }
        candidateSkillRepository.saveAll(candidateSkillUpdateList);
        MultipartFile file = request.getCvAttachment();
        if (file != null) {
            String filePath = uploadFile(file);
            candidate.setCvFilePath(filePath);
            candidate.setFilename(file.getOriginalFilename());
        }
        candidate.setFullName(request.getFullName());
        candidate.setEmail(request.getEmail());
        candidate.setDateOfBirth(request.getDateOfBirth());
        candidate.setAddress(request.getAddress());
        candidate.setPhoneNumber(request.getPhoneNumber());
        candidate.setGender(request.getGender());
        candidate.setPosition(request.getPosition());
        candidate.setYearOfExperience(request.getYearOfExperience());
        candidate.setLevel(request.getLevel());
        candidate.setNote(request.getNote());
        candidateRepository.save(candidate);
        asyncService.deleteFile(filePathToDelete);
    }

    @Override
    public CandidateByIdResponse getCandidateById(Long id) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(id);
        if (candidateOptional.isEmpty()) {
            throw new EntityNotFoundException("Ứng viên không tồn tai");
        }
        Candidate candidate = candidateOptional.get();
        List<CandidateSkill> candidateSkillList = candidateSkillRepository.findByCandidateId(candidate.getId());
        List<String> skillList = candidateSkillList.stream().map(CandidateSkill::getSkill).toList();
        return CandidateByIdResponse.builder()
                .id(candidate.getId())
                .fullName(candidate.getFullName())
                .email(candidate.getEmail())
                .dateOfBirth(candidate.getDateOfBirth())
                .address(candidate.getAddress())
                .phoneNumber(candidate.getPhoneNumber())
                .gender(candidate.getGender())
                .filename(candidate.getFilename())
                .cvFilePath(candidate.getCvFilePath())
                .position(candidate.getPosition())
                .yearOfExperience(candidate.getYearOfExperience())
                .skills(skillList)
                .level(candidate.getLevel())
                .note(candidate.getNote())
                .status(candidate.getStatus())
                .build();
    }

    @Override
    public void deleteCandidateById(Long id) {
        Optional<Candidate> candidateOptional = candidateRepository.findById(id);
        if (candidateOptional.isEmpty()) {
            throw new EntityNotFoundException("Ứng viên không tồn tai");
        }
        candidateSkillRepository.deleteByCandidateId(id);
        String filePathToDelete = candidateOptional.get().getCvFilePath();
        candidateRepository.deleteById(id);
        asyncService.deleteFile(filePathToDelete);
    }

    private void createFolder(Path folder) {
        try {
            Files.createDirectories(folder);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String uploadFile(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        Path folder = Paths.get(uploadDir);
        createFolder(folder);
        String filename = StringUtils.join(uuid.toString(), "-",
                Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = folder.resolve(filename);
        try (InputStream is = file.getInputStream();
             OutputStream os = new BufferedOutputStream(Files.newOutputStream(filePath))) {
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return filename;
    }
}
