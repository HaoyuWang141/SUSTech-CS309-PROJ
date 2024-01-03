package com.ooad.dormitory.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ooad.dormitory.entity.Comment;
import com.ooad.dormitory.entity.StudentAccount;
import com.ooad.dormitory.mapper.AuthenticationMapper;
import com.ooad.dormitory.service.CommentService;
import com.ooad.dormitory.service.DormitoryService;
import com.ooad.dormitory.service.StudentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ForumController(CommentService commentService, DormitoryService dormitoryService, StudentAccountService studentAccountService, AuthenticationMapper authenticationMapper) {
        this.commentService = commentService;
        this.dormitoryService = dormitoryService;
        this.studentAccountService = studentAccountService;
        this.authenticationMapper = authenticationMapper;
    }

    @PostMapping("/launch")
    public ResponseEntity<?> launch(@RequestBody StudentAccount studentAccount, @RequestBody Integer dormitoryId, @RequestBody String content, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        try {
            Comment comment = new Comment();
            comment.setPublisherId(studentAccount.getStudentId());
            comment.setPublisher(studentAccount);
            if (dormitoryId != null) {
                comment.setDormitoryId(dormitoryId);
                comment.setDormitory(dormitoryService.getById(dormitoryId));
                assert (comment.getDormitory() != null);
            }
            else {
                comment.setDormitoryId(null);
                comment.setDormitory(null);
            }
            comment.setContent(content);
            comment.setPublishTime(new java.sql.Time(System.currentTimeMillis()));
            commentService.save(comment);
        } catch (Exception e) {
            throw new RuntimeException("launch comment failed!");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reply")
    public ResponseEntity<?> reply(@RequestBody StudentAccount studentAccount, @RequestBody Integer replyingCommentId, @RequestBody String content, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        try {
            Comment comment = new Comment();
            comment.setPublisherId(studentAccount.getStudentId());
            comment.setPublisher(studentAccount);
            comment.setReplyingCommentId(replyingCommentId);
            comment.setReplyingComment(commentService.getById(replyingCommentId));
            assert comment.getReplyingComment() != null;
            comment.setDormitory(comment.getReplyingComment().getDormitory());
            comment.setDormitoryId(comment.getReplyingComment().getDormitoryId());
            comment.setContent(content);
            comment.setPublishTime(new java.sql.Time(System.currentTimeMillis()));
            commentService.save(comment);
        } catch (Exception e) {
            throw new RuntimeException("reply comment failed!");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getComment")
    public List<Comment> getComment(@RequestBody Integer commentId, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return commentService.list(new QueryWrapper<Comment>().eq("id", commentId));
    }

    @GetMapping("/getComments")
    public List<Comment> getComments(@RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return commentService.list();
    }

    @GetMapping("/getCommentsByDormitory")
    public List<Comment> getCommentsByDormitory(@RequestBody Integer dormitoryId, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return commentService.list(new QueryWrapper<Comment>().eq("dormitoryId", dormitoryId));
    }

    @GetMapping("/getCommentsByBuilding")
    public List<Comment> getCommentsByBuilding(@RequestBody Integer buildingId, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return commentService.list().stream()
                .filter(comment -> comment.getDormitory() != null && comment.getDormitory().getBuildingId().equals(buildingId))
                .collect(Collectors.toList());
    }

    @GetMapping("/getCommentsByRegion")
    public List<Comment> getCommentsByRegion(@RequestBody Integer regionId, @RequestBody String token) {
        LoginController.checkAuthentication(authenticationMapper, token);
        return commentService.list().stream()
                .filter(comment -> comment.getDormitory() != null && comment.getDormitory().getBuilding().getRegionId().equals(regionId))
                .collect(Collectors.toList());
    }

}
