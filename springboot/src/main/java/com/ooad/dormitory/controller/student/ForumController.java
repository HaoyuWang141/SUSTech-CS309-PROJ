package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Comment;
import com.ooad.dormitory.entity.Reply;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.exception.BackEndException;
import com.ooad.dormitory.mapper.AuthenticationMapper;
import com.ooad.dormitory.service.CommentService;
import com.ooad.dormitory.service.DormitoryService;
import com.ooad.dormitory.service.ReplyService;
import com.ooad.dormitory.service.StudentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student/forum")
@CrossOrigin
public class ForumController {

    private final CommentService commentService;
    private final DormitoryService dormitoryService;
    private final StudentAccountService studentAccountService;
    private final AuthenticationMapper authenticationMapper;
    @Autowired
    private ReplyService replyService;

    @Autowired
    public ForumController(CommentService commentService, DormitoryService dormitoryService, StudentAccountService studentAccountService, AuthenticationMapper authenticationMapper) {
        this.commentService = commentService;
        this.dormitoryService = dormitoryService;
        this.studentAccountService = studentAccountService;
        this.authenticationMapper = authenticationMapper;
    }

    @PostMapping("/launch")
    public ResponseEntity<?> launch(String studentAccountId, Integer dormitoryId, String content) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        assert studentAccount != null;

        try {
            Comment comment = new Comment();
            comment.setPublisherId(studentAccount.getStudentId());
            comment.setDormitoryId(dormitoryId);
            comment.setContent(content);
            comment.setPublishTime(new Timestamp(System.currentTimeMillis()));
            commentService.save(comment);
        } catch (Exception e) {
            throw new BackEndException("launch comment failed!");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reply")
    public ResponseEntity<?> reply(String studentAccountId, Integer commentId, String content) {
        StudentAccount studentAccount = studentAccountService.getById(studentAccountId);
        assert studentAccount != null;

        try {
            Comment comment = commentService.getById(commentId);
            Reply reply = new Reply();
            reply.setPublisherId(studentAccount.getStudentId());
            reply.setDormitoryId(comment.getDormitoryId());
            reply.setContent(content);
            reply.setPublishTime(new Timestamp(System.currentTimeMillis()));
            reply.setCommentId(commentId);
            System.out.println(reply);
            replyService.save(reply);
        } catch (Exception e) {
            throw new BackEndException("reply comment failed!");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getComment")
    public List<Comment> getComment(@RequestBody Integer commentId) {

        return commentService.list(new QueryWrapper<Comment>().eq("id", commentId));
    }

    @GetMapping("/getComments")
    public List<Comment> getComments(Integer dormitoryId) {
        if (dormitoryId != null) {
            return commentService.list(new QueryWrapper<Comment>().eq("dormitory_id", dormitoryId));
        }
        return commentService.list();
    }

    @GetMapping("/getReply")
    public List<Reply> getReply(Integer commentId) {
        return replyService.list(new QueryWrapper<Reply>().eq("comment_id", commentId));
    }
}
