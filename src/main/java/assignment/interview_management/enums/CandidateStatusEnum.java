package assignment.interview_management.enums;

public enum CandidateStatusEnum {
    // Hoạt động: Khi ứng viên được tạo mới và chưa được sắp lịch phỏng vấn nào.
    OPEN,
    // Chờ phỏng vấn: Khi lịch phỏng vấn đã được lên cho ứng viên.
    WAITING_FOR_INTERVIEW,
    // Đang phỏng vấn: Khi ứng viên đang trong quá trình phỏng vấn.
    IN_PROGRESS,
    // Phỏng vấn bị hủy: Khi buổi phỏng vấn bị hủy.
    CANCELLED_INTERVIEW,
    // Đậu phỏng vấn: Khi ứng viên vượt qua buổi phỏng vấn.
    PASSED_INTERVIEW,
    // Rớt phỏng vấn: Khi ứng viên không đạt trong buổi phỏng vấn.
    FAILED_INTERVIEW,
    // Chờ phê duyệt: Khi thư mời làm việc (offer) đã được tạo cho ứng viên và đang chờ quản lý xem xét.
    WAITING_FOR_APPROVAL,
    // Offer được phê duyệt: Khi quản lý chấp thuận thư mời làm việc cho ứng viên.
    APPROVED_OFFER,
    // Offer bị từ chối: Khi quản lý từ chối thư mời làm việc cho ứng viên.
    REJECTED_OFFER,
    // Chờ phản hồi: Khi thư mời làm việc đã được gửi và đang chờ ứng viên phản hồi.
    WAITING_FOR_RESPONSE,
    // Đã chấp nhận offer: Khi ứng viên đồng ý nhận thư mời làm việc.
    ACCEPTED_OFFER,
    // Từ chối offer: Khi ứng viên từ chối thư mời làm việc.
    DECLINED_OFFER,
    // Offer bị hủy: Khi nhà tuyển dụng hủy thư mời làm việc
    CANCELLED_OFFER,
    //  Bị cấm: Khi ứng viên bị nhà tuyển dụng cấm tham gia tuyển dụng
    BANNED
}
