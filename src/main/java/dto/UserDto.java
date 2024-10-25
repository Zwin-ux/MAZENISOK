package dto;

import java.math.BigDecimal;

public class UserDto extends BaseDto {

    private String userName;
    private String email;
    private BigDecimal balance;

    public UserDto() {
        this.balance = BigDecimal.ZERO;
    }

    public UserDto(String uniqueId, String userName, String email) {
        super(uniqueId);
        this.userName = userName;
        this.email = email;
        this.balance = BigDecimal.ZERO;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "uniqueId='" + getUniqueId() + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
