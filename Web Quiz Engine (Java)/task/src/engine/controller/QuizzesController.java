package engine.controller;

import engine.model.*;
import engine.repository.QuizOptionRepository;
import engine.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizzesController {
    private final QuizOptionRepository quizOptionRepository;
    private final QuizRepository quizRepository;

    @PostMapping
    public QuizWithID postNewQuiz(@RequestBody QuizWithAnswer quizWithAnswer) {

        Quiz quiz = new Quiz();
        quiz.setTitle(quizWithAnswer.getTitle());
        quiz.setText(quizWithAnswer.getText());
        quiz.setAnswer(quizWithAnswer.getAnswer());

        for (int i = 0; i < quizWithAnswer.getOptions().size(); i++) {
            String option = quizWithAnswer.getOptions().get(i);
            QuizOption quizOption = new QuizOption(i, quiz, option);
//            quizOption = quizOptionRepository.save(quizOption);
            quiz.addOption(quizOption);
        }

        quiz = quizRepository.save(quiz);

        return new QuizWithID(quiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizWithID> getQuizById(@PathVariable Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        return quiz.map(value ->
                ResponseEntity.ok(new QuizWithID(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<QuizWithID>> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return ResponseEntity.ok(
                quizzes.stream()
                        .map(QuizWithID::new)
                        .toList()
        );
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<QuizResponse> postQuizAnswer(@PathVariable Integer id, @RequestParam Integer answer) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isEmpty()) return ResponseEntity.notFound().build();
        Quiz quiz = quizOptional.get();
        if (Objects.equals(quiz.getAnswer(), answer)) return ResponseEntity.ok(new QuizResponse(true));
        else return ResponseEntity.ok(new QuizResponse(false));
    }
}
