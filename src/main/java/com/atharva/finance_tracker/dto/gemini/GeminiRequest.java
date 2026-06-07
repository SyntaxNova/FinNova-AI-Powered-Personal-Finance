package com.atharva.finance_tracker.dto.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GeminiRequest {

    private List<Content> contents;
}