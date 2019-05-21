package com.example.postgresdemo.model;

import com.example.postgresdemo.ClassPreamble;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * The entity that will contain the Answer model
 *
 * See {@link com.example.postgresdemo.model.AuditModel} for AuditModel details
 *
 */

@ClassPreamble(
        author = "Blake Olinger",
        date = "5/21/19",
        reviewers = "none"
)
@Entity
@Table(name = "answers")
@Data
@EqualsAndHashCode(callSuper = false)
public class AnswerModel extends AuditModel {

    @Id
    @GeneratedValue(generator = "answer_generator")
    @SequenceGenerator(
            name = "answer_generator",
            sequenceName = "answer_sequence",
            initialValue = 1000
    )
    private Long id;

    /**
     * Answer definition text
     */
    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private QuestionModel questionModel;

}
