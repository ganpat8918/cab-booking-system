package com.ganpat.cabbooking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
	@NotBlank(message = "Full name is required")
	private String fullName;
	
	@Email(message ="Invalid email")
	@NotBlank(message ="Email is required")
	private String email;
	
	@Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be 10 digits")
    private String phone;

    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;
}
