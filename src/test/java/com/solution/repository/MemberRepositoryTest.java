package com.solution.repository;

import com.solution.model.Member;
import com.solution.model.enums.Gender;
import com.solution.model.enums.MaritalStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void MemberRepository_SaveAll_ReturnSavedMember() {

        //Arrange
        String email = "john@gmail.com";
        String cardNo = "my-90";
        Member member = new Member(
                null,
                "John",
                email,
                "03339090889",
                cardNo,
                "JLK",
                Gender.Male,
                MaritalStatus.Single,
                "19-05-2024",
                true
        );

        //Act
        Member savedMember = memberRepository.save(member);

        //Assert
        Assertions.assertThat(savedMember).isNotNull();
        Assertions.assertThat(savedMember.getId()).isGreaterThan(0);
    }

    @Test
    public void MemberRepository_GetAll_ReturnMoreThenOneMember() {
        String email = "john@gmail.com";
        String cardNo = "my-90";
        Member member = new Member(
                null,
                "John",
                email,
                "03339090889",
                cardNo,
                "JLK",
                Gender.Male,
                MaritalStatus.Single,
                "19-05-2024",
                true
        );

        email = "abbas572@gmail.com";
        cardNo = "my-91";
        Member member2 = new Member(
                null,
                "Abbas",
                email,
                "03339090889",
                cardNo,
                "JLK",
                Gender.Male,
                MaritalStatus.Single,
                "19-05-2024",
                true
        );

        memberRepository.save(member);
        memberRepository.save(member2);

        List<Member> memberList = memberRepository.findAll();

        Assertions.assertThat(memberList).isNotNull();
        Assertions.assertThat(memberList.size()).isEqualTo(2);
    }

    @Test
    public void MemberRepository_FindById_ReturnMember() {
        String email = "john@gmail.com";
        String cardNo = "my-90";
        Member member = new Member(
                null,
                "John",
                email,
                "03339090889",
                cardNo,
                "JLK",
                Gender.Male,
                MaritalStatus.Single,
                "19-05-2024",
                true
        );

        memberRepository.save(member);

        Member memberList = memberRepository.findById(member.getId()).get();

        Assertions.assertThat(memberList).isNotNull();
    }

    @Test
    public void MemberRepository_FindByIdAndStatusTrue_ReturnMemberNotNull() {
        String email = "john@gmail.com";
        String cardNo = "my-90";
        Member member = new Member(
                null,
                "John",
                email,
                "03339090889",
                cardNo,
                "JLK",
                Gender.Male,
                MaritalStatus.Single,
                "19-05-2024",
                true
        );

        memberRepository.save(member);

        Member memberList = memberRepository.findByIdAndStatusTrue(member.getId());

        Assertions.assertThat(memberList).isNotNull();
    }

    @Test
    public void MemberRepository_FindDuplicates_ReturnFalse() {
        String email = "john@gmail.com";
        String cardNo = "my-90";
        Member member = new Member(
                null,
                "John",
                email,
                "03339090889",
                cardNo,
                "JLK",
                Gender.Male,
                MaritalStatus.Single,
                "19-05-2024",
                true
        );

        Member member2 = new Member(
                null,
                "Abbas",
                email,
                "03339090889",
                cardNo,
                "JLK",
                Gender.Male,
                MaritalStatus.Single,
                "19-05-2024",
                true
        );

        memberRepository.save(member);
        memberRepository.save(member2);

        Boolean duplicate = memberRepository.findDuplicates(email, cardNo, member.getId());

        Assertions.assertThat(duplicate).isTrue();
    }

    @Test
    public void MemberRepository_UpdateMember_ReturnMemberNotNull() {
        String email = "john@gmail.com";
        String cardNo = "my-90";
        Member member = new Member(
                null,
                "John",
                email,
                "03339090889",
                cardNo,
                "JLK",
                Gender.Male,
                MaritalStatus.Single,
                "19-05-2024",
                true
        );

        memberRepository.save(member);

        Member memberSave = memberRepository.findById(member.getId()).get();
        memberSave.setAddress("SB");
        memberSave.setName("Ali");

        Member updatedMember = memberRepository.save(memberSave);

        Assertions.assertThat(updatedMember.getAddress()).isNotNull();
        Assertions.assertThat(updatedMember.getName()).isNotNull();
    }

    @Test
    public void MemberRepository_MemberDelete_ReturnMemberIsEmpty() {
        String email = "john@gmail.com";
        String cardNo = "my-90";
        Member member = new Member(
                null,
                "John",
                email,
                "03339090889",
                cardNo,
                "JLK",
                Gender.Male,
                MaritalStatus.Single,
                "19-05-2024",
                true
        );

        memberRepository.save(member);

        memberRepository.deleteById(member.getId());
        Optional<Member> memberReturn = memberRepository.findById(member.getId());

        Assertions.assertThat(memberReturn).isEmpty();
    }
}