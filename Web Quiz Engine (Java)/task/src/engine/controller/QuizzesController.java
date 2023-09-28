package engine.controller;

import engine.model.*;
import engine.repository.QuizOptionRepository;
import engine.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizzesController {
    private final QuizOptionRepository quizOptionRepository;
    private final QuizRepository quizRepository;

    @PostMapping
    public ResponseEntity<QuizWithID> postNewQuiz(@Valid @RequestBody PostQuiz postQuiz) {

        Quiz quiz = new Quiz();
        quiz.setTitle(postQuiz.getTitle());
        quiz.setText(postQuiz.getText());

        int optionsNumber = postQuiz.getOptions() == null ? 0 : postQuiz.getOptions().size();
        if (optionsNumber < 2) return ResponseEntity.badRequest().build();

        for (int i = 0; i < optionsNumber; i++) {
            String option = postQuiz.getOptions().get(i);
            QuizOption quizOption = new QuizOption(i, option);
            quiz.addOption(quizOption);
        }

        int answersNumber = postQuiz.getAnswer() == null ? 0 : postQuiz.getAnswer().size();
        for (int i = 0; i < answersNumber; i++) {
            Integer index = postQuiz.getAnswer().get(i);
            QuizAnswer quizAnswer = new QuizAnswer(index);
            quiz.addAnswer(quizAnswer);
        }

        quiz = quizRepository.save(quiz);

        return ResponseEntity.ok(new QuizWithID(quiz));
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
    public ResponseEntity<QuizResponse> postQuizAnswer(@PathVariable Integer id, @RequestBody PostAnswers answer) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isEmpty()) return ResponseEntity.notFound().build();
        Quiz quiz = quizOptional.get();
        List<Integer> quizAnswers = quiz.getAnswers().stream()
                .map(QuizAnswer::getIndex)
                .sorted()
                .toList();

        List<Integer> postedAnswers = answer.getAnswer().stream()
                .sorted()
                .toList();

        if (quizAnswers.isEmpty() && postedAnswers.isEmpty()) return ResponseEntity.ok(new QuizResponse(true));

        if (quizAnswers.equals(postedAnswers)) return ResponseEntity.ok(new QuizResponse(true));
        else return ResponseEntity.ok(new QuizResponse(false));
    }
}
