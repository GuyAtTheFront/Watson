export interface TelegramUser {
  id: number;
  is_bot: boolean;
  first_name?: string;
  last_name?: string;
  username?: string;
  language_code?: string;
  is_premium?: boolean;
  added_to_attachment_menu?: boolean;
  can_join_groups?: boolean;
  can_read_all_group_messages?: boolean;
  supports_inline_queries?: boolean;
}

export interface Bot {
  id: number;
  username: string;
  imageUrl?: string;
  token?: string;
}

export interface ChatMessage {
  id: number;
  fromId: number;
  toId: number;
  date: Date;
  content: string;
  contentType: string;
}

export interface ChatUser {
  id: number;
  username: string;
  lastMessage?: string;
  lastMessageTime?: Date;
  imageUrl: string;
}

export interface AuthInfo{
  username: string;
  userToken: string;
  expiresAt: Date;
}