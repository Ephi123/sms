
CREATE TABLE IF NOT EXISTS users
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
    update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    account_non_expired boolean NOT NULL,
    account_non_locked boolean NOT NULL,
    bio character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    age integer,
    gender character varying(255) COLLATE pg_catalog."default",
    address character varying(255) COLLATE pg_catalog."default",
    enable boolean NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default",
    image_url character varying(255) COLLATE pg_catalog."default",
    last_login timestamp(6) without time zone,
    last_name character varying(255) COLLATE pg_catalog."default",
    login_attempt integer,
    mfa boolean NOT NULL,
    phone character varying(255) COLLATE pg_catalog."default",
    qr_code_image text COLLATE pg_catalog."default",
    qr_code_secret character varying(255) COLLATE pg_catalog."default",
    user_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_users_id PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_user_id UNIQUE (user_id),
    CONSTRAINT fk_users_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_users_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);


-- Table: public.credentials

-- DROP TABLE IF EXISTS public.credentials;

CREATE TABLE IF NOT EXISTS credentials
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
    update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    password character varying(255) COLLATE pg_catalog."default",
    user_id bigint NOT NULL,
    CONSTRAINT pk_credentials_id PRIMARY KEY (id),
    CONSTRAINT uq_credentials_user_id UNIQUE (user_id),
    CONSTRAINT fk_credentials_user_id  FOREIGN KEY (user_id) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_credentials_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_credentials_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);


-- Table: public.confirmations

-- DROP TABLE IF EXISTS public.confirmations;

CREATE TABLE IF NOT EXISTS confirmations
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
    update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    token character varying(255) COLLATE pg_catalog."default",
    user_id bigint NOT NULL,
    CONSTRAINT pk_confirmations_id PRIMARY KEY (id),
    CONSTRAINT uk_confirmation_token unique (token),
    CONSTRAINT uq_confirmations_user_id UNIQUE (user_id),
    CONSTRAINT fk_confirmations_user_id FOREIGN KEY (user_id) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_confirmations_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_confirmations_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE


);


-- Table: public.roles

-- DROP TABLE IF EXISTS public.roles;

CREATE TABLE IF NOT EXISTS roles
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
    update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    authority character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT pk_roles_id PRIMARY KEY (id),
    CONSTRAINT fk_roles_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_roles_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE

);

-- Table: public.user_role

-- DROP TABLE IF EXISTS public.user_role;

CREATE TABLE IF NOT EXISTS user_role
(
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT PK_user_role_user_id UNIQUE (user_id),
    CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES roles (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES users (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS documents (
   id SERIAL,
   document_id CHARACTER VARYING(255) NOT NULL,
   extension CHARACTER VARYING(10),
   formatted_size CHARACTER VARYING(255),
    icon CHARACTER VARYING(255),
    name CHARACTER VARYING(255),
    doc_size BIGINT NOT NULL,
    url CHARACTER VARYING(255),
    description  CHARACTER VARYING(255),
   created_at timestamp(6) without time zone NOT NULL ,
    created_by bigint NOT NULL,
    reference_id character varying(255) ,
    update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    CONSTRAINT pk_document_id  PRIMARY KEY (id),
    CONSTRAINT uq_document_document_id UNIQUE(document_id),
    CONSTRAINT fk_document_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_document_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE

    );

-----Student Management System  -------


CREATE TABLE IF NOT EXISTS semesters
(
  id SERIAL,
   semester  smallint unique  Not null,
   is_current boolean NOT NULL,
   created_at timestamp(6) without time zone NOT NULL,
   created_by bigint NOT NULL,
   update_at timestamp(6) without time zone NOT NULL,
   updated_by bigint NOT NULL,
   reference_id character varying(255) COLLATE pg_catalog."default",
   CONSTRAINT pk_semesters_id PRIMARY KEY (id),
  CONSTRAINT uq_semesters_is_current  UNIQUE(is_current ),
  CONSTRAINT fk_semesters_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_semesters_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE

);



CREATE TABLE IF NOT EXISTS fields
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
	update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	field_name character varying(255)NOT NULL,
	field_code integer unique not null ,
    CONSTRAINT pk_field_id PRIMARY KEY (id),

	 --Section relationship will be defined --
    CONSTRAINT fk_fields_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_fields_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS grades
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
	 update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	grade integer unique not null ,

	 --Grade relationship will be defined Here --

     CONSTRAINT pk_grades_id PRIMARY KEY (id),
    CONSTRAINT fk_grades_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_grades_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS subjects
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
	update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	subject_name character varying(255),
	grade_id bigint NOT NULL,

	 --Section relationship will be defined --
	   CONSTRAINT pk_subjects_id PRIMARY KEY (id),
    CONSTRAINT fk_subjects_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_subjects_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_subjects_grade_id   FOREIGN KEY (grade_id) REFERENCES grades(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);


  CREATE TABLE IF NOT EXISTS subject_field
  (
     subject_id bigint NOT NULL,
	 field_id bigint NOT NULL,
	 CONSTRAINT uq_subject_field_subjectIid UNIQUE (subject_id),
	 CONSTRAINT fk_subject_field_subject_id FOREIGN KEY(subject_id) REFERENCES subjects(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_subject_field_field_id   FOREIGN KEY(field_id) REFERENCES fields(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
  );





CREATE TABLE IF NOT EXISTS sections
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
	 update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	room character varying(255) unique,
	block  character varying(255) COLLATE pg_catalog."default",
    qr_code_image text COLLATE pg_catalog."default",
    qr_code_secret character varying(255) COLLATE pg_catalog."default",
    grade_id NOT NULL,
    CONSTRAINT pk_sections_id PRIMARY KEY (id),
     --Section relationship will be defined --
	CONSTRAINT fk_sections_grade_id   FOREIGN KEY (grade_id) REFERENCES grades(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_sections_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_sections_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS section_field(
  section_id bigint not null;
  field_id bigint not null;
  constraint uq_section_field_section_id unique(section_id),
  CONSTRAINT fk_sections_field_section_id  FOREIGN KEY (section_id) REFERENCES sections(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_sections_field_field_id    FOREIGN KEY (field_id) REFERENCES fields(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS enrols
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
	 update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	academic_year integer,
	is_valid boolean not null,
    CONSTRAINT pk_enrols_id PRIMARY KEY (id),
    CONSTRAINT fk_enrols_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_enrols_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);






CREATE TABLE IF NOT EXISTS days
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
	update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	day character varying(255),
    CONSTRAINT pk_days_id PRIMARY KEY (id),
	CONSTRAINT fk_days_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_days_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS periods
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
	 update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	period integer,
    CONSTRAINT pk_periods_id PRIMARY KEY (id),
	CONSTRAINT fk_periods_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_periods_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS assessments
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
    semester smallint default 1,
   update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	assessment_name character varying(255) NOT NULL,
	wight integer NOT NULL,
	academic_year integer NOT NULL,
	section_id bigint NOT NULL,
	subject_id bigint NOT NULL,
	semester_id bigint NOT NULL,
	teacher_id bigint NOT NULL,
	 CONSTRAINT pk_assessments_id PRIMARY KEY (id),
	 CONSTRAINT fk_assessments_created_by   FOREIGN KEY (created_by)   REFERENCES users(id)      MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_assessments_updated_by   FOREIGN KEY (updated_by)   REFERENCES users(id)      MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
	 CONSTRAINT fk_assessments_section_id   FOREIGN KEY (section_id)   REFERENCES sections(id)   MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_assessments_subject_id   FOREIGN KEY (subject_id)   REFERENCES subjects(id)   MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_assessments_teacher_id   FOREIGN KEY (teacher_id)   REFERENCES users(id)      MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE

);


CREATE TABLE IF NOT EXISTS results
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
    update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	mark integer NOT NULL,
	status character varying(20) default 'active'
	assessment_id bigint NOT NULL,
	student_id bigint NOT NULL,
	 CONSTRAINT pk_results_id PRIMARY KEY (id),
	 CONSTRAINT fk_results_created_by   FOREIGN KEY (created_by)   REFERENCES users(id)       MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_results_updated_by   FOREIGN KEY (updated_by)   REFERENCES users(id)       MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
	 CONSTRAINT fk_results_assessment_id FOREIGN KEY (assessment_id) REFERENCES assessments(id)   MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_results_student_id   FOREIGN KEY (student_id)   REFERENCES users(id)       MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS attendances
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
	 update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	assessment_name character varying(255) NOT NULL,
	academic_year integer NOT NULL,
	date timestamp(6) without time zone NOT NULL,
	is_preset boolean NOT NULL,
	section_id bigint NOT NULL,
	semester_id bigint NOT NULL,
	student_id bigint NOT NULL,
	 CONSTRAINT pk_attendances_id PRIMARY KEY (id),
	 CONSTRAINT fk_attendances_created_by   FOREIGN KEY (created_by)   REFERENCES users(id)      MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_attendances_updated_by   FOREIGN KEY (updated_by)   REFERENCES users(id)      MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
	 CONSTRAINT fk_attendances_section_id   FOREIGN KEY (section_id)   REFERENCES sections(id)   MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_attendances_semester_id  FOREIGN KEY (semester_id)  REFERENCES semesters(id)  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_attendances_student_id   FOREIGN KEY (student_id)   REFERENCES users(id)      MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE

);




CREATE TABLE IF NOT EXISTS assigns
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
	 update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	academic_year integer NOT NULL,
	section_id bigint NOT NULL,
	subject_id bigint NOT NULL,
	teacher_id bigint NOT NULL,
	 CONSTRAINT pk_assigns_id PRIMARY KEY (id),
	 CONSTRAINT fk_assigns_created_by   FOREIGN KEY (created_by)   REFERENCES users(id)      MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_assigns_updated_by   FOREIGN KEY (updated_by)   REFERENCES users(id)      MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
	 CONSTRAINT fk_assigns_section_id   FOREIGN KEY (section_id)   REFERENCES sections(id)   MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_assigns_subject_id   FOREIGN KEY (subject_id)   REFERENCES subjects(id)   MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_assigns_teacher_id   FOREIGN KEY (teacher_id)   REFERENCES users(id)      MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
	 );


CREATE TABLE IF NOT EXISTS time_tables
(
    id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
	 update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
	academic_year integer,
	period_id bigint NOT NULL,
	day_id bigint NOT NULL,
    section_id bigint NOT NULL,
	teacher_id bigint NOT NULL,
	--Section relationship will be defined --
 CONSTRAINT pk_time_table_id PRIMARY KEY (id),
 CONSTRAINT fk_time_table_created_by FOREIGN KEY (created_by) REFERENCES users(id)    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
 CONSTRAINT fk_time_table_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
 CONSTRAINT fk_time_table_period_id  FOREIGN KEY (period_id)  REFERENCES periods(id)  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
 CONSTRAINT fk_time_table_day_id     FOREIGN KEY (day_id)     REFERENCES days(id)     MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
 CONSTRAINT fk_time_table_section_id FOREIGN KEY (section_id) REFERENCES sections(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
 CONSTRAINT fk_time_table_teacher_id FOREIGN KEY (teacher_id) REFERENCES users(id)    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);





--enrol relations--

CREATE TABLE IF NOT EXISTS enrol_section
(
    enrol_id bigint NOT NULL,
    section_id bigint NOT NULL,

    CONSTRAINT fk_enrol_section_enrol_id FOREIGN KEY (enrol_id) REFERENCES enrols (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT fk_enrol_section_section_id FOREIGN KEY (section_id) REFERENCES sections (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE

);





CREATE TABLE IF NOT EXISTS enrol_student
(
    enrol_id bigint NOT NULL,
    user_id bigint NOT NULL,

    CONSTRAINT fk_enrol_student_enrol_id FOREIGN KEY (enrol_id) REFERENCES enrols (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT fk_enrol_student_user_id FOREIGN KEY (user_id) REFERENCES users (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE

);


CREATE TABLE IF NOT EXISTS enrol_grade
(
    enrol_id bigint NOT NULL,
    grade_id bigint NOT NULL,

    CONSTRAINT fk_enrol_grade_enrol_id FOREIGN KEY (enrol_id) REFERENCES enrols (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT fk_enrol_grade_grade_id FOREIGN KEY (grade_id) REFERENCES grades (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE

);



CREATE TABLE IF NOT EXISTS enrol_field
(
    enrol_id bigint NOT NULL,
    field_id bigint NOT NULL,
	CONSTRAINT uq_enrol_field_enrol_id unique(enrol_id),
    CONSTRAINT fk_enrol_field_enrol_id FOREIGN KEY (enrol_id) REFERENCES enrols (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT fk_enrol_field_field_id FOREIGN KEY (field_id) REFERENCES fields (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE

);

create table if not exists id_generator(
  last_value bigint,
  user_id character varying(255) primary key
);
create table if not exists next_payments(
     id SERIAL,
      created_at timestamp(6) without time zone NOT NULL,
      created_by bigint NOT NULL,
      reference_id character varying(255) COLLATE pg_catalog."default",
      update_at timestamp(6) without time zone NOT NULL,
      updated_by bigint NOT NULL,
  month smallInt,
  student_id bigint,
  constraint uk_next_payments_user_id unique(student_id),
  constraint fk_next_payments_user_id foreign key(student_id) references users(id) match simple on update cascade on delete cascade,
     CONSTRAINT fk_next_payments_user_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
      CONSTRAINT fk_next_payments_user_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
  );

create table if not exists payments(
   id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
    update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
academic_year smallInt,
month character varying,
student_id bigInt,
payment smallInt,
penalty smallInt default 0,
total smallInt,
date timestamp(6) without time zone NOT NULL,
constraint fk_student_payments_user_id foreign key(student_id) references users(id) match simple on update cascade on delete cascade,
   CONSTRAINT fk_student_payments_user_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_student_payments_user_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

create table if not exists student_payments(
   id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
    update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
academic_year smallInt,
month_fee smallInt,
grade_id bigInt,
constraint uk_student_payments_user_id unique(grade_id),
constraint fk_student_payments_user_id foreign key(grade_id) references grades(id) match simple on update cascade on delete cascade,
   CONSTRAINT fk_student_payments_user_created_by FOREIGN KEY (created_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_student_payments_user_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);
create table if not exists registrations(
id character varying(20),
registration_fee smallint,
registration_start_date date,
registration_end_date date,
CONSTRAINT pk_registrations_id PRIMARY KEY (id)
);
create table if not exists penalties(
penalty_id character varying primary key,
penalty decimal(10,2) not null,
penalty_after smallint not null
)
alter table payments add column is_checked boolean default false;
alter table sections add column student_number integer
alter table enrols alter column is_valid set default false
alter table enrols add column date timestamp(6) without time zone NOT NULL
alter table assessments drop column semester_id
alter table results add column status character varying(255) default 'active'

create table if not exists subject_status(
 id SERIAL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by bigint NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
    update_at timestamp(6) without time zone NOT NULL,
    updated_by bigint NOT NULL,
	academic_year smallint not null,
	subject_id bigint not null,
	section_id bigint not null,
	semester smallint not null,
	status character varying(255) default 'active',
	CONSTRAINT pk_subject_status_id PRIMARY KEY (id),
	Constraint fk_subject_status_subject_id foreign key(subject_id) references subjects(id) match simple on delete cascade on update cascade,
	Constraint fk_subject_status_section_id foreign key(section_id) references sections(id) match simple on delete cascade on update cascade
