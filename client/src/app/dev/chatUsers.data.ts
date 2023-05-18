import { ChatUser } from "../models/models";

export const CHAT_USERS: ChatUser[] = [
    {
      id: 1, 
      username: "test-client-1", 
      lastMessage:"hello world", 
      lastMessageTime: new Date(), 
      imageUrl: "https://shorturl.at/DOSWZ"
    },
    {
      id: 2, 
      username: "test-client-2", 
      lastMessage:"goodbye world", 
      lastMessageTime: new Date(), 
      imageUrl: "https://shorturl.at/DOSWZ"
    },
    {
      id: 3, 
      username: "test-client-3", 
      lastMessage:"hehehehhehehe", 
      lastMessageTime: new Date(), 
      imageUrl: "https://shorturl.at/DOSWZ"
    },
  ];