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
DROP TABLE IF EXISTS invitation CASCADE;
DROP TABLE IF EXISTS notification CASCADE;
DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS reply CASCADE;
DROP TABLE IF EXISTS authentication CASCADE;
DROP TABLE IF EXISTS chat_message;

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
    gender       INT, -- 0:女, 1:男
    degree       INT, -- 0:本科, 1:硕士, 2:博士
    building_id  INT,
    FOREIGN KEY (layout_id) REFERENCES layout (layout_id),
    FOREIGN KEY (building_id) REFERENCES building (building_id)
);

-- 队伍表
CREATE TABLE team
(
    team_id      SERIAL PRIMARY KEY,
    dormitory_id INT,
    owner_id     VARCHAR,
    FOREIGN KEY (dormitory_id) REFERENCES dormitory (dormitory_id)
);

-- 学生账户表
CREATE TABLE student_account
(
    student_id                  VARCHAR(20) PRIMARY KEY,
    name                        VARCHAR(100),
    gender                      INT, -- 0:女, 1:男
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
    id           SERIAL PRIMARY KEY,
    team_id      INT,
    dormitory_id INT,
    UNIQUE (team_id, dormitory_id),
    FOREIGN KEY (team_id) REFERENCES team (team_id),
    FOREIGN KEY (dormitory_id) REFERENCES dormitory (dormitory_id)
);

CREATE TABLE allocation_relation
(
    id           SERIAL PRIMARY KEY,
    entry_year   INT, -- eg: 2019
    degree       INT, -- 0:本科, 1:硕士, 2:博士
    gender       INT, -- 0:女, 1:男
    dormitory_id INT,
    UNIQUE (entry_year, degree, gender, dormitory_id)
);

CREATE TABLE allocation_stage
(
    id         SERIAL PRIMARY KEY,
    entry_year INT,          -- eg: 2019
    degree     INT,          -- 0:本科, 1:硕士, 2:博士
    gender     INT,          -- 0:女, 1:男
    stage      INT NOT NULL, -- 0:组队阶段, 1:收藏阶段, 2:正选阶段, 3:结束阶段
    start_time TIMESTAMP,
    end_time   TIMESTAMP,
    UNIQUE (entry_year, degree, gender, stage)
);

CREATE TABLE invitation
(
    id         SERIAL PRIMARY KEY,
    inviter_id VARCHAR,
    invitee_id VARCHAR,
    time       TIMESTAMP
);

CREATE TABLE notification
(
    id           SERIAL PRIMARY KEY,
    publisher_id INT,
    entry_year   INT, -- eg: 2019
    degree       INT, -- 0:本科, 1:硕士, 2:博士
    gender       INT, -- 0:女, 1:男
    title        TEXT,
    content      TEXT,
    publish_time TIMESTAMP,
    FOREIGN KEY (publisher_id) REFERENCES admin_account (account_id)
);

CREATE TABLE comment
(
    id           SERIAL PRIMARY KEY,
    publisher_id VARCHAR(20),
    dormitory_id INT,
    content      TEXT,
    publish_time TIMESTAMP,
    FOREIGN KEY (publisher_id) REFERENCES student_account (student_id),
    FOREIGN KEY (dormitory_id) REFERENCES dormitory (dormitory_id)
);

CREATE TABLE reply
(
    id           SERIAL PRIMARY KEY,
    publisher_id VARCHAR(20),
    dormitory_id INT,
    content      TEXT,
    publish_time TIMESTAMP,
    comment_id   INT,
    FOREIGN KEY (publisher_id) REFERENCES student_account (student_id),
    FOREIGN KEY (dormitory_id) REFERENCES dormitory (dormitory_id)
);

CREATE TABLE authentication
(
    student_id         VARCHAR PRIMARY KEY,
    student_password   VARCHAR(100),
    token              VARCHAR(100),
    token_failure_time TIME,
    online_amount      INT, -- 该账户已在线设备数
    FOREIGN KEY (student_id) REFERENCES student_account (student_id)
);

CREATE TABLE chat_message
(
    id          SERIAL PRIMARY KEY,                  -- 消息ID
    sender_id   VARCHAR(20) NOT NULL,                -- 发送者ID
    receiver_id VARCHAR(20),                         -- 接收者ID（对于一对一消息）
    team_id     INT REFERENCES team,                 -- 聊天室ID（对于群聊）
    content     TEXT        NOT NULL,                -- 消息内容
    timestamp   TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 时间戳
    status      VARCHAR(50)                          -- 消息状态（例如：已发送、已接收、已读等）
);
