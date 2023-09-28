package engine.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizSuccessResponse {
    private boolean success;
    private String feedback;

    public QuizSuccessResponse(boolean success) {
        this.success = success;
        if (success) feedback = "Congratulations, you're right!";
        else feedback = "Wrong answer! Please, try again.";
    }
}
