package engine.controller;

import engine.DTO.QuizSuccessResponse;
import engine.model.Quiz;
import engine.model.QuizOption;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @GetMapping
    public Quiz getQuiz() {
        Quiz quiz = new Quiz();
        quiz.setTitle("The Java Logo");
        quiz.setText("What is depicted on the Java logo?");
        quiz.addOption(new QuizOption(0, "Robot"));
        quiz.addOption(new QuizOption(1, "Tea leaf"));
        quiz.addOption(new QuizOption(2, "Cup of coffee"));
        quiz.addOption(new QuizOption(3, "Bug"));

        return quiz;
    }

    @PostMapping
    public QuizSuccessResponse postQuizAnswer(@RequestParam int answer) {
        if (answer == 2) return new QuizSuccessResponse(true);
        else return new QuizSuccessResponse(false);
    }
}
