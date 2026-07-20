CREATE TABLE institution (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(100) UNIQUE NOT NULL,
    cnpj VARCHAR(14) UNIQUE NOT NULL,
    subscription_id VARCHAR(255),
    subscription_status VARCHAR(20) NOT NULL DEFAULT 'TRIAL',
    expires_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

ALTER TABLE professor ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE professor DROP CONSTRAINT IF EXISTS professor_email_key;
ALTER TABLE professor ADD CONSTRAINT fk_professor_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;
ALTER TABLE professor ADD CONSTRAINT unique_professor_email_per_institution
    UNIQUE (institution_id, email);

ALTER TABLE subject ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE subject DROP CONSTRAINT IF EXISTS subject_code_key;
ALTER TABLE subject ADD CONSTRAINT fk_subject_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;
ALTER TABLE subject ADD CONSTRAINT unique_subject_code_per_institution
    UNIQUE (institution_id, code);

ALTER TABLE course ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE course ADD CONSTRAINT fk_course_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;

ALTER TABLE semester ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE semester DROP CONSTRAINT IF EXISTS semester_year_term_key;
ALTER TABLE semester ADD CONSTRAINT fk_semester_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;
ALTER TABLE semester ADD CONSTRAINT unique_semester_per_institution
    UNIQUE (institution_id, year, term);

ALTER TABLE classroom ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE classroom DROP CONSTRAINT IF EXISTS classroom_number_key;
ALTER TABLE classroom ADD CONSTRAINT fk_classroom_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;
ALTER TABLE classroom ADD CONSTRAINT unique_classroom_per_institution
    UNIQUE (institution_id, number);

ALTER TABLE time_slot ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE time_slot ADD CONSTRAINT fk_time_slot_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;

ALTER TABLE subject_offering ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE subject_offering DROP CONSTRAINT IF EXISTS subject_offering_course_id_subject_id_semester_id_section_key;
ALTER TABLE subject_offering ADD CONSTRAINT fk_subject_offering_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;
ALTER TABLE subject_offering ADD CONSTRAINT unique_subject_offering_per_institution
    UNIQUE (institution_id, course_id, subject_id, semester_id, section);

ALTER TABLE schedule ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE schedule ADD CONSTRAINT fk_schedule_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;

ALTER TABLE availability ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE availability ADD CONSTRAINT fk_availability_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;

ALTER TABLE professor_qualification ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE professor_qualification ADD CONSTRAINT fk_professor_qualification_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;

ALTER TABLE schedule_entry ADD COLUMN institution_id BIGINT NOT NULL;
ALTER TABLE schedule_entry ADD CONSTRAINT fk_schedule_entry_institution
    FOREIGN KEY (institution_id) REFERENCES institution(id) ON DELETE CASCADE;


CREATE INDEX idx_professor_institution ON professor(institution_id);
CREATE INDEX idx_subject_institution ON subject(institution_id);
CREATE INDEX idx_course_institution ON course(institution_id);
CREATE INDEX idx_semester_institution ON semester(institution_id);
CREATE INDEX idx_classroom_institution ON classroom(institution_id);
CREATE INDEX idx_time_slot_institution ON time_slot(institution_id);
CREATE INDEX idx_subject_offering_institution ON subject_offering(institution_id);
CREATE INDEX idx_schedule_institution ON schedule(institution_id);
CREATE INDEX idx_availability_institution ON availability(institution_id);
CREATE INDEX idx_prof_qualification_institution ON professor_qualification(institution_id);
CREATE INDEX idx_schedule_entry_institution ON schedule_entry(institution_id);