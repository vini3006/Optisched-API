CREATE TABLE professor (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE subject (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    workload INTEGER NOT NULL CHECK (workload > 0)
);

CREATE TABLE course (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE semester (
    id BIGSERIAL PRIMARY KEY,
    year INTEGER NOT NULL,
    term INTEGER NOT NULL CHECK (term IN (1,2)),
    UNIQUE(year, term)
);

CREATE TABLE classroom (
    id BIGSERIAL PRIMARY KEY,
    number VARCHAR(20) NOT NULL UNIQUE,
    capacity INTEGER NOT NULL CHECK (capacity > 0)
);

CREATE TABLE time_slot (
    id BIGSERIAL PRIMARY KEY,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

CREATE TABLE professor_qualification (
    professor_id BIGINT NOT NULL REFERENCES professor(id),
    subject_id BIGINT NOT NULL REFERENCES subject(id),
    PRIMARY KEY (professor_id, subject_id)
);

CREATE TABLE availability (
    professor_id BIGINT NOT NULL REFERENCES professor(id),
    time_slot_id BIGINT NOT NULL REFERENCES time_slot(id),
    PRIMARY KEY (professor_id, time_slot_id)
);

CREATE TABLE subject_offering (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL REFERENCES course(id),
    subject_id BIGINT NOT NULL REFERENCES subject(id),
    semester_id BIGINT NOT NULL REFERENCES semester(id),
    section VARCHAR(20) NOT NULL,
    expected_students INTEGER NOT NULL CHECK (expected_students > 0),
    recommended_semester INTEGER CHECK (recommended_semester BETWEEN 1 AND 12),
    UNIQUE(course_id, subject_id, semester_id, section)
);

CREATE TABLE schedule (
    id BIGSERIAL PRIMARY KEY,
    semester_id BIGINT NOT NULL REFERENCES semester(id),
    generated_at TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE schedule_entry (
    id BIGSERIAL PRIMARY KEY,
    schedule_id BIGINT NOT NULL REFERENCES schedule(id),
    professor_id BIGINT NOT NULL REFERENCES professor(id),
    subject_offering_id BIGINT NOT NULL REFERENCES subject_offering(id),
    classroom_id BIGINT NOT NULL REFERENCES classroom(id),
    time_slot_id BIGINT NOT NULL REFERENCES time_slot(id),
    UNIQUE(schedule_id, classroom_id, time_slot_id),
    UNIQUE(schedule_id, professor_id, time_slot_id),
    UNIQUE (schedule_id, professor_id, subject_offering_id, classroom_id, time_slot_id)
);