-- 删除表格
DROP TABLE IF EXISTS team_favorite_dorm;
DROP TABLE IF EXISTS region_building_dorm;
DROP TABLE IF EXISTS student_account;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS dormitory;
DROP TABLE IF EXISTS layout;
DROP TABLE IF EXISTS building;
DROP TABLE IF EXISTS region;
DROP TABLE IF EXISTS admin_account;

-- 管理员账号表
CREATE TABLE admin_account (
    account_id SERIAL PRIMARY KEY,
    account_name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- 区划表
CREATE TABLE region (
    region_id SERIAL PRIMARY KEY,
    region_name VARCHAR(100) NOT NULL
);

-- 楼栋表
CREATE TABLE building (
    building_id SERIAL PRIMARY KEY,
    building_name VARCHAR(100) NOT NULL,
    description TEXT
);

-- 布局表
CREATE TABLE layout (
    layout_id SERIAL PRIMARY KEY,
    layout_name VARCHAR(100) NOT NULL,
    description TEXT,
    image_url VARCHAR(255)
);

-- 宿舍表
CREATE TABLE dormitory (
    dormitory_id SERIAL PRIMARY KEY,
    floor INT NOT NULL,
    room_number VARCHAR(20) NOT NULL,
    bed_count INT,
    description TEXT,
    layout_id INT,
    is_empty BOOLEAN,
    gender VARCHAR(20),
    degree VARCHAR(20),
    FOREIGN KEY (layout_id) REFERENCES layout(layout_id)
);

-- 队伍表
CREATE TABLE team (
    team_id SERIAL PRIMARY KEY,
    dormitory_id INT,
    FOREIGN KEY (dormitory_id) REFERENCES dormitory(dormitory_id)
);

-- 学生账户表
CREATE TABLE student_account (
    student_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    gender VARCHAR(20),
    student_number VARCHAR(20) NOT NULL UNIQUE,
    photo_url VARCHAR(255),
    personal_description TEXT,
    sleep_time TIME,
    wake_up_time TIME,
    air_conditioner_temperature FLOAT,
    is_snores BOOLEAN,
    qq VARCHAR(20),
    email VARCHAR(100),
    wechat VARCHAR(50),
    team_id INT,
    FOREIGN KEY (team_id) REFERENCES team(team_id)
);

-- 区划-楼栋-宿舍关系表
CREATE TABLE region_building_dorm (
    region_id INT,
    building_id INT,
    dormitory_id INT,
    PRIMARY KEY (region_id, building_id, dormitory_id),
    FOREIGN KEY (region_id) REFERENCES region(region_id),
    FOREIGN KEY (building_id) REFERENCES building(building_id),
    FOREIGN KEY (dormitory_id) REFERENCES dormitory(dormitory_id)
);

-- 队伍-收藏宿舍关系表
CREATE TABLE team_favorite_dorm (
    team_id INT,
    dormitory_id INT,
    PRIMARY KEY (team_id, dormitory_id),
    FOREIGN KEY (team_id) REFERENCES team(team_id),
    FOREIGN KEY (dormitory_id) REFERENCES dormitory(dormitory_id)
);
