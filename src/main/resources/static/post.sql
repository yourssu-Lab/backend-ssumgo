INSERT INTO posts (mentee_id,
                   subject_id,
                   title,
                   content,
                   created_date,
                   updated_date)
VALUES (1, -- student 테이블의 실제 존재하는 ID
        1, -- 컴퓨터 시스템 개론 과목의 실제 존재하는 ID
        '컴퓨터시스템개론 기말 어떻게 준비하면 좋을까요??',
        '컴퓨터시스템개론을 어떻게 준비하면 좋은지에 대한답변답변답변답ㅂ변ㅇ가나다라마바사아자차카타파하ㅏㅑㅓㅕㅗㅛㅜㅠㅢ',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);