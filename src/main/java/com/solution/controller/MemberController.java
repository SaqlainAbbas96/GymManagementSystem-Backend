package com.solution.controller;

import com.solution.config.constant.Constants;
import com.solution.config.constant.Messages;
import com.solution.dto.MemberDto;
import com.solution.dto.ResponseDto;
import com.solution.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private Messages messages;

    /**
     * @return all member list
     */
    @GetMapping(value = Constants.GET_ALL_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<List<MemberDto>>> get() {
        return new ResponseEntity<>(new ResponseDto<>(messages.get("list.success", "Members"), false, memberService.findAll()), HttpStatus.OK);
    }

    /**
     * save member
     * @param memberDto
     * @return saved Member
     */
    @PostMapping(value = Constants.SAVE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(new ResponseDto<>(messages.get("create.success", "Member"), false, memberService.save(memberDto)), HttpStatus.CREATED);
    }

    /**
     * Update Member
     * @param memberDto
     * @param id
     * @return NO_CONTENT status as per PUT request
     */
    @PutMapping(value = Constants.UPDATE_URL)
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody MemberDto memberDto, @PathVariable("id") Long id) {
        memberService.update(memberDto, id);
        return new ResponseEntity<>(new ResponseDto<>(messages.get("update.success", "Member"), false), HttpStatus.NO_CONTENT);
    }

    /**
     * @param id
     * @return Member of provided id
     */
    @GetMapping(value = Constants.FIND_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<MemberDto>> findOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new ResponseDto<>(messages.get("list.success", "Member"), false, memberService.findMemberById(id)), HttpStatus.OK);
    }

    /**
     * Soft Delete Member by setting the Status check to false
     * @param id
     * @return OK status as per DELETE request
     */
    @DeleteMapping(value = Constants.DELETE_URL)
    public ResponseEntity<ResponseDto> delete(@PathVariable("id") Long id) {
        memberService.delete(id);
        return new ResponseEntity<>(new ResponseDto<>(messages.get("delete.success", "Member"), false), HttpStatus.OK);
    }
}
