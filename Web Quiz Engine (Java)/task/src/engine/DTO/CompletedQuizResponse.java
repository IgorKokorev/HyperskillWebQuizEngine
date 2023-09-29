package engine.DTO;

import engine.model.CompletedQuiz;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CompletedQuizResponse {
    private Integer id;
    private Instant completedAt;

    public CompletedQuizResponse(CompletedQuiz quiz) {
        this.id = quiz.getQuiz().getId();
        this.completedAt = quiz.getCompletedAt();
    }
}
