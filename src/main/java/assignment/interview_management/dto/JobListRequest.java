package assignment.interview_management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobListRequest {

    private PaginationDTO pagination;

    private String search;
}
