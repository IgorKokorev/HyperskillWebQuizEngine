package engine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "quiz_answer")
public class QuizAnswer implements Comparable<QuizAnswer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer index;

    public QuizAnswer(Integer index) {
        this.index = index;
    }

    @Override
    public int compareTo(QuizAnswer quizAnswer) {
        return this.index.compareTo(quizAnswer.getIndex());
    }
}
