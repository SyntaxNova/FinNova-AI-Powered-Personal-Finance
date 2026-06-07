package com.atharva.finance_tracker.dto.gemini;

import lombok.Data;
import java.util.List;

@Data
public class GeminiResponse {

    private List<Candidate> candidates;
}