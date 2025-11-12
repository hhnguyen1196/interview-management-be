package assignment.interview_management.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class PaginationDTO {

    private Integer pageNumber;

    private Integer pageSize;
}
