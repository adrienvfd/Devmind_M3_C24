package devmind.c24.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("user"),
    ADMIN("admin"),
    STAFF("staff");

    private final String role;
}
