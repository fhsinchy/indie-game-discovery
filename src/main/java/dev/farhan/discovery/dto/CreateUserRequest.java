package dev.farhan.discovery.dto;

import dev.farhan.discovery.domain.Preferences;

public record CreateUserRequest(String username, Preferences preferences) {
}
