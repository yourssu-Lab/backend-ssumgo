INSERT INTO comment (mentor_id,
                     posts_id,
                     title,
                     content,
                     created_date,
                     updated_date)
VALUES (1, -- student 테이블의 실제 존재하는 ID (멘토 역할의 학생)
        1, -- posts 테이블의 실제 존재하는 ID
        '답변',
        '컴퓨터시스템개론을 어떻게 준비하면 좋은지에 대한답변답변답변답ㅂ변ㅇ가나다라마바사아자차카타파하ㅏㅑㅓㅕㅗㅛㅜㅠㅢ',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);