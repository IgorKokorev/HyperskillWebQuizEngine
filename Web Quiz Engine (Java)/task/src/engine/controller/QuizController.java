package engine.controller;

import engine.model.Quiz;
import engine.model.QuizOption;
import engine.model.QuizResponse;
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
    public QuizResponse postQuizAnswer(@RequestParam int answer) {
        if (answer == 2) return new QuizResponse(true, "Congratulations, you're right!");
        else return new QuizResponse(false, "Wrong answer! Please, try again.");
    }
}
