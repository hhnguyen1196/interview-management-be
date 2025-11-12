package assignment.interview_management.enums;

public enum CandidateStatusEnum {
    // Hoạt động: Khi ứng viên được tạo mới và chưa được sắp lịch phỏng vấn nào.
    OPEN,
    // Chờ phỏng vấn: Khi lịch phỏng vấn đã được lên cho ứng viên.
    WAITING_FOR_INTERVIEW,
    // Hủy phỏng vấn: Khi ứng viên đang trong quá trình phỏng vấn.
    CANCELLED_INTERVIEW
}
