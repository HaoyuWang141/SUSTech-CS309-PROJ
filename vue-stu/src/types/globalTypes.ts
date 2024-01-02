export interface Region {
    region_id: number;
    region_name: string;
}

export interface Building {
    building_id: number;
    building_name: string;
    description: string;
    region_id: number;
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
    is_empty: boolean;
    gender: string;
    degree: string;
    building_id: number;
}
