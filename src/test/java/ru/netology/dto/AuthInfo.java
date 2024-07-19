package ru.netology.dto;

import lombok.Value;

@Value
public class AuthInfo {
    String login;
    String password;
}
