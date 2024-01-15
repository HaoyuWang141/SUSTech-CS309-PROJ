export interface Region {
    region_id: number;
    region_name: string;
}

export interface Building {
    building_id: number;
    building_name: string;
    description: string;
    region_id: number;
    region: Region;
}

export interface Layout {
    layout_id: number;
    layout_name: string;
    description: string;
    image_url: string;
}

export interface Dormitory {
    dormitory_id: number;
    floor: number;
    room_number: string;
    bed_count: number;
    description: string;
    layout_id: number;
    layout: Layout;
    is_empty: boolean;
    gender: string;
    degree: string;
    building_id: number;
    building: Building;
}

export interface ReplyType {
    id: number;
    publisher_id: number;
    dormitory_id: number;
    content: string;
    publish_time: string;
    comment_id: number;
}

export interface CommentType {
    id: number;
    publisher_id: number;
    dormitory_id: number;
    content: string;
    publish_time: string;
    reply_list: ReplyType[];
}

export interface Team {
    team_id: number,
    dormitory_id: number,
    owner_id: number,
    dormitory: Dormitory,
}

export interface StudentAccount {
    student_id: string,
    name: string,
    gender: number,
    photoUrl: string,
    sleep_time: string,
    wake_up_time: string,
    air_conditioner_temperature: number,
    snore: false,
    qq: string,
    email: string,
    wechat: string,
    teamId: number,
    team: Team,
}