-- 删除表格
DROP TABLE IF EXISTS team_favorite_dorm CASCADE;
DROP TABLE IF EXISTS student_account CASCADE;
DROP TABLE IF EXISTS team CASCADE;
DROP TABLE IF EXISTS dormitory CASCADE;
DROP TABLE IF EXISTS layout CASCADE;
DROP TABLE IF EXISTS building CASCADE;
DROP TABLE IF EXISTS region CASCADE;
DROP TABLE IF EXISTS admin_account CASCADE;
DROP TABLE IF EXISTS allocation_relation CASCADE;
DROP TABLE IF EXISTS allocation_stage CASCADE;

-- 管理员账号表
CREATE TABLE admin_account
(
    account_id   SERIAL PRIMARY KEY,
    account_name VARCHAR(50) UNIQUE NOT NULL,
    password     VARCHAR(50)        NOT NULL
);

-- 区划表
CREATE TABLE region
(
    region_id   SERIAL PRIMARY KEY,
    region_name VARCHAR(100) NOT NULL
);

-- 楼栋表
CREATE TABLE building
(
    building_id   SERIAL PRIMARY KEY,
    building_name VARCHAR(100) NOT NULL,
    description   TEXT,
    region_id     INT REFERENCES region (region_id)
);

-- 布局表
CREATE TABLE layout
(
    layout_id   SERIAL PRIMARY KEY,
    layout_name VARCHAR(100) NOT NULL,
    description TEXT,
    image_url   VARCHAR(255)
);

-- 宿舍表
CREATE TABLE dormitory
(
    dormitory_id SERIAL PRIMARY KEY,
    floor        INT         NOT NULL,
    room_number  VARCHAR(20) NOT NULL,
    bed_count    INT,
    description  TEXT,
    layout_id    INT,
    is_empty     BOOLEAN,
    gender       VARCHAR(20),
    degree       VARCHAR(20),
    building_id  INT,
    FOREIGN KEY (layout_id) REFERENCES layout (layout_id),
    FOREIGN KEY (building_id) REFERENCES building (building_id)
);

-- 队伍表
CREATE TABLE team
(
    team_id      SERIAL PRIMARY KEY,
    dormitory_id INT,
    FOREIGN KEY (dormitory_id) REFERENCES dormitory (dormitory_id)
);

-- 学生账户表
CREATE TABLE student_account
(
    student_id                  VARCHAR(20) PRIMARY KEY,
    name                        VARCHAR(100),
    gender                      VARCHAR(20),
    photo_url                   VARCHAR(255),
    personal_description        TEXT,
    sleep_time                  TIME,
    wake_up_time                TIME,
    air_conditioner_temperature INT,
    snore                       BOOLEAN,
    qq                          VARCHAR(20),
    email                       VARCHAR(100),
    wechat                      VARCHAR(50),
    team_id                     INT,
    FOREIGN KEY (team_id) REFERENCES team (team_id)
);

-- 队伍-收藏宿舍关系表
CREATE TABLE team_favorite_dorm
(
    team_id      INT,
    dormitory_id INT,
    PRIMARY KEY (team_id, dormitory_id),
    FOREIGN KEY (team_id) REFERENCES team (team_id),
    FOREIGN KEY (dormitory_id) REFERENCES dormitory (dormitory_id)
);

CREATE TABLE allocation_relation
(
    entry_year      INT,
    degree          INT,
    gender          INT,
    dormitory_id    INT,
    UNIQUE (entry_year, degree, gender, dormitory_id)
);

CREATE TABLE allocation_stage
(
    entry_year  INT,
    degree      INT,
    gender      INT,
    stage       INT NOT NULL,
    PRIMARY KEY (entry_year, degree)
);
