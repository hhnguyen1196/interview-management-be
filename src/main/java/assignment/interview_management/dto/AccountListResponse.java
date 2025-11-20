package assignment.interview_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountListResponse {

    private List<Account> accountList;

    private Integer totalElements;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Account {

        private Long id;

        private String username;

        private String fullName;

        private String gender;

        private String email;

        private String department;

        private Boolean isActive;
    }
}
