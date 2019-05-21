package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.QuestionModel;
import com.example.postgresdemo.repository.QuestionRepository;
import org.jetbrains.annotations.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class QuestionController {

    private QuestionRepository questionRepository;

    @Contract(pure = true)
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/questions")
    public Page<QuestionModel> getQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @PostMapping("/questions")
    public QuestionModel createQuestion(@Valid @RequestBody
                                        QuestionModel questionModel) {
        return questionRepository.save(questionModel);
    }

    @PutMapping("/questions/{questionId}")
    public QuestionModel updateQuestion(@PathVariable Long questionId,
                                        @Valid @RequestBody QuestionModel questionModel) {
        return questionRepository.findById(questionId)
                .map(questionModelMod -> {
                    questionModelMod.setTitle(questionModel.getTitle());
                    questionModelMod.setDescription(questionModel.getDescription());
                    return questionRepository.save(questionModelMod);
                }).orElseThrow(
                        () -> new ResourceNotFoundException("Question with that ID not found")
                );
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable Long questionId) {
        return questionRepository.findById(questionId)
                .map(questionModel -> {
                    questionRepository.delete(questionModel);
                    return ResponseEntity.ok().build();
                }).orElseThrow(
                        () -> new ResourceNotFoundException("Question with that ID not found")
                );
    }
}
