package com.example.gameconnect.dto;

import com.example.gameconnect.domain.Preferences;

public record CreateUserRequest(String username, Preferences preferences) {
}
