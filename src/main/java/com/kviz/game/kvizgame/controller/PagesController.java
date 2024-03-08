package com.kviz.game.kvizgame.controller;

import com.kviz.game.kvizgame.entity.Question;
import com.kviz.game.kvizgame.entity.User;
import com.kviz.game.kvizgame.repository.QuestionRepository;
import com.kviz.game.kvizgame.repository.UserRepository;
import com.kviz.game.kvizgame.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/kviz")
@AllArgsConstructor
public class PagesController {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuestionService questionService;

    @GetMapping("/start")
    public String getStartPage(HttpSession session) {
        session.invalidate();
        return "start";
    }

    @GetMapping("/record")
    public String getRecordPage(Model model) {
        List<User> users = userRepository.findAll().stream().sorted((user1, user2) -> user2.getScore() - user1.getScore()).toList();
        model.addAttribute("players", users);
        return "record";
    }

    @PostMapping("/game")
    public String getGamePage(@RequestParam("name") String name, HttpSession session, Model model) {
        if (session.getAttribute("userUUID") == null) {
            User user = User.builder()
                    .id(null)
                    .score(0)
                    .name(name)
                    .uuid(String.valueOf(UUID.randomUUID()))
                    .build();
            session.setAttribute("userUUID", user.getUuid());
            session.setAttribute("score", user.getScore());
            session.setAttribute("num", 0);
            userRepository.save(user);
        }
        List<Question> list = questionRepository.findAll();
        if (list.isEmpty()) {
            return "exception";
        }
        Question question = list.getFirst();
        model.addAttribute("question", question.getQuestion());
        model.addAttribute("answerA", question.getAnswerA());
        model.addAttribute("answerB", question.getAnswerB());
        model.addAttribute("answerC", question.getAnswerC());
        model.addAttribute("answerD", question.getAnswerD());
        return "main";
    }

    @PostMapping("/check")
    public String getNextPage(@RequestParam("question") String question, @RequestParam("answer") String answer,
                              HttpSession session, Model model) {
        Object userAttribute = session.getAttribute("userUUID");
        if (userAttribute == null) {
            return "exception";
        }
        String userUUID = (String) userAttribute;
        User user = userRepository.findUserByUuid(userUUID);
        Question questionEntity = questionRepository.findQuestionByQuestion(question);
        model.addAttribute("question", questionEntity.getQuestion());
        model.addAttribute("answerA", questionEntity.getAnswerA());
        model.addAttribute("answerB", questionEntity.getAnswerB());
        model.addAttribute("answerC", questionEntity.getAnswerC());
        model.addAttribute("answerD", questionEntity.getAnswerD());
        Integer num = (Integer) session.getAttribute("num");
        if (questionEntity.getCurrentAnswer().equals(answer)) {
            user.setScore(user.getScore() + questionEntity.getPrice());
            model.addAttribute("colorA", questionEntity.getAnswerA().equals(answer) ? "#36ff00" : "#f2f2f2");
            model.addAttribute("colorB", questionEntity.getAnswerB().equals(answer) ? "#36ff00" : "#f2f2f2");
            model.addAttribute("colorC", questionEntity.getAnswerC().equals(answer) ? "#36ff00" : "#f2f2f2");
            model.addAttribute("colorD", questionEntity.getAnswerD().equals(answer) ? "#36ff00" : "#f2f2f2");
            model.addAttribute("prize", questionEntity.getPrize());
            session.setAttribute("num", ++num);
        } else {
            model.addAttribute("colorA", questionEntity.getAnswerA().equals(answer) ? "#ff0000" : questionEntity.getAnswerA().equals(questionEntity.getCurrentAnswer()) ? "#36ff00" : "#f2f2f2");
            model.addAttribute("colorB", questionEntity.getAnswerB().equals(answer) ? "#ff0000" : questionEntity.getAnswerB().equals(questionEntity.getCurrentAnswer()) ? "#36ff00" : "#f2f2f2");
            model.addAttribute("colorC", questionEntity.getAnswerC().equals(answer) ? "#ff0000" : questionEntity.getAnswerC().equals(questionEntity.getCurrentAnswer()) ? "#36ff00" : "#f2f2f2");
            model.addAttribute("colorD", questionEntity.getAnswerD().equals(answer) ? "#ff0000" : questionEntity.getAnswerD().equals(questionEntity.getCurrentAnswer()) ? "#36ff00" : "#f2f2f2");
            model.addAttribute("prize", "");
        }
        userRepository.save(user);
        return "check";
    }

    @PostMapping("/next")
    public String getGamePage(HttpSession session, Model model) {
        if (session.getAttribute("userUUID") == null) {
            return "exception";
        }
        List<Question> list = questionRepository.findAll();
        if (list.isEmpty()) {
            return "exception";
        }
        String userUUID = (String) session.getAttribute("userUUID");
        Integer num = (Integer) session.getAttribute("num");
        Question question = questionService.getNextQuestion(num);
        User user = userRepository.findUserByUuid(userUUID);
        if (question == null) {
            model.addAttribute("userScore", user.getScore());
            int totalScore = questionRepository.findAll().stream().mapToInt(Question::getPrice).sum();
            model.addAttribute("totalScore", totalScore);
            return "final";
        }
        model.addAttribute("question", question.getQuestion());
        model.addAttribute("answerA", question.getAnswerA());
        model.addAttribute("answerB", question.getAnswerB());
        model.addAttribute("answerC", question.getAnswerC());
        model.addAttribute("answerD", question.getAnswerD());
        return "main";
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        List<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "admin";
    }

    @PostMapping("/add-kviz")
    public String addKviz(@RequestParam("question") String question,
                          @RequestParam("answerA") String answerA,
                          @RequestParam("answerB") String answerB,
                          @RequestParam("answerC") String answerC,
                          @RequestParam("answerD") String answerD,
                          @RequestParam("correctAnswer") String correctAnswer,
                          @RequestParam("prize") String prize) {
        String answerStr = "";
        switch (correctAnswer) {
            case "A":
                answerStr = answerA;
                break;
            case "B":
                answerStr = answerB;
                break;
            case "C":
                answerStr = answerC;
                break;
            case "D":
                answerStr = answerD;
                break;
            default:
        }
        Question questionEntity = Question.builder()
                .id(null)
                .question(question)
                .answerA(answerA)
                .answerB(answerB)
                .answerC(answerC)
                .answerD(answerD)
                .currentAnswer(answerStr)
                .price(1)
                .prize(prize)
                .build();
        questionRepository.save(questionEntity);
        return "redirect:/kviz/admin";
    }

    @PostMapping("delete-kviz")
    public String deleteKviz(@RequestParam("questionId") String questionId, Model model) {
        questionRepository.deleteById(Long.valueOf(questionId));
        return "redirect:/kviz/admin";
    }
}
