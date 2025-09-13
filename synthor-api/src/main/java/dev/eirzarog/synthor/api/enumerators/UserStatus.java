package dev.eirzarog.synthor.api.enumerators;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE,
    CREDENTIALS_EXPIRED,
    LOCKED,
    EXPIRED,
    INACTIVE,
    SUSPENDED,
    DELETED

}
