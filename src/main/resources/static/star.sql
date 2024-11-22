INSERT INTO star (student_id,
                  comment_id,
                  created_date,
                  updated_date)
VALUES (1, -- student 테이블의 실제 존재하는 ID
        1, ---- comment 테이블의 실제 존재하는 ID
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);