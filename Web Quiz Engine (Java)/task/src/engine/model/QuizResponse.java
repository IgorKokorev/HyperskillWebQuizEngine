package engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizResponse {
    private boolean success;
    private String feedback;

    public QuizResponse(boolean success) {
        this.success = success;
        if (success) feedback = "Congratulations, you're right!";
        else feedback = "Wrong answer! Please, try again.";
    }
}
