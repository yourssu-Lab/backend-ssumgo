INSERT INTO student (
    yourssu_id,
    nickname,
    department,
    student_id_number,
    small_url,
    mid_url,
    large_url,
    created_date,
    updated_date
)
VALUES
    (
        'nggus5',
        '학생1',
        '글로벌미디어학부',
        20241122,
        'https://example.com/images/small/1.jpg',
        'https://example.com/images/medium/1.jpg',
        'https://example.com/images/large/1.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'student2',
        '학생2',
        '글로벌미디어학부',
        20201235,
        'https://example.com/images/small/2.jpg',
        'https://example.com/images/medium/2.jpg',
        'https://example.com/images/large/2.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );