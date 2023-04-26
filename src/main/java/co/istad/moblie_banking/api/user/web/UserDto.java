package co.istad.moblie_banking.api.user.web;

public record UserDto(String name,
                      String gender,
                      String oneSignalId,
                      String studentCardId,
                      Boolean isStudent) {
}
