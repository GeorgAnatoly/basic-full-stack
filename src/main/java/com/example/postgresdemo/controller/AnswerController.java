package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.AnswerModel;
import com.example.postgresdemo.repository.AnswerRepository;
import com.example.postgresdemo.repository.QuestionRepository;
import org.jetbrains.annotations.Contract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AnswerController {
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;

    @Contract(pure = true)
    public AnswerController(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/questions/{questionId}/answers")
    public List<AnswerModel> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerRepository.findByQuestionModelId(questionId);
    }

    @PostMapping("/questions/{questionId}/answers")
    public AnswerModel addAnswer(@PathVariable Long questionId,
                                 @Valid @RequestBody
                                 AnswerModel answerModel) {
        return questionRepository.findById(questionId)
                .map(questionModel -> {
                    answerModel.setQuestionModel(questionModel);
                    return answerRepository.save(answerModel);
                }).orElseThrow(
                        () -> new ResourceNotFoundException("Question not found with ID")
                );
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public AnswerModel updateAnswer(@PathVariable Long questionId,
                                    @PathVariable Long answerId,
                                    @Valid @RequestBody AnswerModel answerModelRequest) {
        if(!questionRepository.existsById(questionId))
            throw new ResourceNotFoundException("Question not found with id");

        return answerRepository.findById(answerId)
                .map(answerModel -> {
                    answerModel.setText(
                            answerModelRequest.getText()
                    );
                    return answerRepository.save(answerModel);
                }).orElseThrow(
                        () -> new ResourceNotFoundException("Answer not found with ID")
                );
    }

    @DeleteMapping("/questions/{questionId}/answers]{answerId}")
    public ResponseEntity deleteAnswer(@PathVariable Long questionId,
                                       @PathVariable Long answerId) {
        if(!questionRepository.existsById(questionId))
            throw new ResourceNotFoundException("Question with ID not found");

        return answerRepository.findById(answerId)
                .map(answerModel -> {
                    answerRepository.delete(answerModel);
                    return ResponseEntity.ok().build();
                }).orElseThrow(
                        () -> new ResourceNotFoundException("Answer with ID not found")
                );
    }
}
