package com.solution.service;

import com.solution.config.constant.Messages;
import com.solution.config.exception.DuplicateRecordException;
import com.solution.config.exception.RecordNotFoundException;
import com.solution.config.exception.ServiceException;
import com.solution.dto.MemberDto;
import com.solution.formatter.DtoFormatter;
import com.solution.model.Member;
import com.solution.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Messages messages;

    /**
     * Retrieve All Members From Member Table
     * @return MemberDto
     */
    @DtoFormatter
    public List<MemberDto> findAll() {
        log.info("[MemberService] findAll starts");
        try {
            List<Member> members = memberRepository.findAll();
            if (!members.isEmpty()) {
                List<MemberDto> memberDtos = new ArrayList<>();
                for (Member member : members) {
                    MemberDto memberDto = modelMapper.map(member, MemberDto.class);
                    memberDtos.add(memberDto);
                }
                log.info("[MemberService] findAll ends");
                return memberDtos;
            } else {
                log.error("[MemberService] findAll no records found");
                throw new RecordNotFoundException(messages.get("no.records.found"), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("[MemberService] findAll Exception:" + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Save The Member In Table From Provided Data in MemberDto
     * @param memberDto
     * @return Saved MemberDto
     */
    @Transactional
    public MemberDto save(MemberDto memberDto) {
        log.info("[MemberService] save starts");
        try {
            memberDto.setId(null);
            Boolean isMemberExist = memberRepository.findDuplicates(memberDto.getEmail(), memberDto.getCardNo(), Long.MAX_VALUE);
            log.info("[MemberService] save : Fetched Data To Check Duplicates");

            if (!isMemberExist) {
                log.info("[MemberService] save isMemberExist is false");

                memberDto.setStatus(true);
                Member member = modelMapper.map(memberDto, Member.class);
                Member mem = memberRepository.save(member);
                MemberDto memDto = modelMapper.map(mem, MemberDto.class);

                log.info("[MemberService] save ends");
                return memDto;
            } else {
                log.error("[MemberService] save duplicate record");
                throw new DuplicateRecordException(messages.get("duplicate.record"));
            }
        } catch (Exception e) {
            log.error("[MemberService] save Exception:" + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Update Member From Provided Data With Corresponding Member Id
     * @param memberDto
     * @param id
     */
    @Transactional
    public void update(MemberDto memberDto, Long id) {
        log.info("[MemberService] update Starts");
        try {
            Member mem = memberRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(messages.get("no.records.found")));
            log.info("[MemberService]: update Find The Existing Member By Id From DB");

            if (mem != null) {
                memberDto.setId(id);
                Boolean isMemberExist = memberRepository.findDuplicates(memberDto.getEmail(), memberDto.getCardNo(), id);
                log.info("[MemberService]: update Fetched Data To Check Duplicate Member");

                if (!isMemberExist) {
                    Member member = modelMapper.map(memberDto, Member.class);
                    memberRepository.save(member);
                    log.info("[MemberService]: update Member Updated");
                    log.info("[MemberService]: update ends");
                } else {
                    log.error("[MemberService]  update duplicate record");
                    throw new DuplicateRecordException(messages.get("duplicate.record"));
                }
            } else {
                log.error("[MemberService] update no record found");
                throw new RecordNotFoundException(messages.get("no.records.found"));
            }
        } catch (Exception e) {
            log.error("[MemberService]: update Error: " + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Find Member From Provided Member Id
     * @param id
     * @return MemberDto
     */
    public MemberDto findMemberById(Long id) {
        log.info("[MemberService] findMemberById starts");
        try {
            Member member = memberRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(messages.get("no.records.found")));
            MemberDto memberDto = modelMapper.map(member, MemberDto.class);
            log.info("[MemberService] findMemberById ends");
            return memberDto;
        } catch (Exception e) {
            log.error("[MemberService] findMemberById Service Error: " + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Soft Delete the Member. Setting The Status To false.
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        log.info("[MemberService]: delete Starts");
        try {
            Member mem = memberRepository.findByIdAndStatusTrue(id);
            log.info("[MemberService]: delete Find The Existing Member By Id From DB");

            if (mem != null) {
                mem.setStatus(false);
                memberRepository.save(mem);
                log.info("[MemberService]: delete Member Deleted");
                log.info("[MemberService]: delete ends");
            } else {
                log.info("[MemberService]: delete Record Not Found Exception");
                throw new RecordNotFoundException(messages.get("no.records.found"));
            }
        } catch (Exception e) {
            log.error("[MemberService]: delete Error: " + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
