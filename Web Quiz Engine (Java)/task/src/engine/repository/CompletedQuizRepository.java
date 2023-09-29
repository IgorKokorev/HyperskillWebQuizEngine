package engine.repository;

import engine.model.CompletedQuiz;
import engine.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Integer> {
    Page<CompletedQuiz> findAll(Pageable pageable);

    Page<CompletedQuiz> findAllByUser(User user, Pageable pageable);
}
