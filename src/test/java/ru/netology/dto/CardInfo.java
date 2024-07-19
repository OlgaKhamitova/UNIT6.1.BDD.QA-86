package ru.netology.dto;

import lombok.*;

@Data
@RequiredArgsConstructor
public class CardInfo {
    @NonNull
    String number;
    int balance;
}
