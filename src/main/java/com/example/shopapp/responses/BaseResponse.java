package com.example.shopapp.responses;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseResponse {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
