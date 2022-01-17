package com.icia.board;

import com.icia.board.dto.MemberSaveDTO;
import com.icia.board.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService ms;

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원가입 테스트")
    public void saveTest() {
        assertThat(ms.save(new MemberSaveDTO("저장용이메일", "저장용비밀번호", "저장용이름"))).isNotNull();
    }

}
