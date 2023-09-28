package engine.controller;

import engine.DTO.PostAnswersRequest;
import engine.DTO.PostQuizRequest;
import engine.DTO.QuizResponse;
import engine.DTO.QuizSuccessResponse;
import engine.model.Quiz;
import engine.model.QuizAnswer;
import engine.model.QuizOption;
import engine.model.User;
import engine.repository.QuizOptionRepository;
import engine.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<QuizResponse> postNewQuiz(
            @Valid @RequestBody PostQuizRequest postQuizRequest,
            @AuthenticationPrincipal User user) {

        Quiz quiz = new Quiz();
        quiz.setTitle(postQuizRequest.getTitle());
        quiz.setText(postQuizRequest.getText());
        quiz.setUser(user);

        int optionsNumber = postQuizRequest.getOptions() == null ? 0 : postQuizRequest.getOptions().size();
        if (optionsNumber < 2) return ResponseEntity.badRequest().build();

        for (int i = 0; i < optionsNumber; i++) {
            String option = postQuizRequest.getOptions().get(i);
            QuizOption quizOption = new QuizOption(i, option);
            quiz.addOption(quizOption);
        }

        int answersNumber = postQuizRequest.getAnswer() == null ? 0 : postQuizRequest.getAnswer().size();
        for (int i = 0; i < answersNumber; i++) {
            Integer index = postQuizRequest.getAnswer().get(i);
            QuizAnswer quizAnswer = new QuizAnswer(index);
            quiz.addAnswer(quizAnswer);
        }

        quiz = quizRepository.save(quiz);

        return ResponseEntity.ok(new QuizResponse(quiz));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        return quiz.map(value ->
                ResponseEntity.ok(new QuizResponse(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return ResponseEntity.ok(
                quizzes.stream()
                        .map(QuizResponse::new)
                        .toList()
        );
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<QuizSuccessResponse> postQuizAnswer(@PathVariable Integer id, @RequestBody PostAnswersRequest answer) {
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

        if (quizAnswers.isEmpty() && postedAnswers.isEmpty()) return ResponseEntity.ok(new QuizSuccessResponse(true));

        if (quizAnswers.equals(postedAnswers)) return ResponseEntity.ok(new QuizSuccessResponse(true));
        else return ResponseEntity.ok(new QuizSuccessResponse(false));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isEmpty()) return ResponseEntity.notFound().build();
        Quiz quiz = quizOptional.get();

        if (quiz.getUser().getId() != user.getId()) return new ResponseEntity(HttpStatus.FORBIDDEN);

        quizRepository.delete(quiz);

        return ResponseEntity.noContent().build();
    }
}
