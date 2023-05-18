import { ChatMessage } from "../models/models";

export const MESSAGES: ChatMessage[] = [
    {
      id: 1,
      fromId: 153628701,
      toId: 153628701,
    //   chatId: "1",
      date: new Date(),
      content: "message one",
      contentType: "text"
    },
    {
      id: 2,
      fromId: 6127352122,
      toId: 153628701,
    //   chatId: "1",
      date: new Date(),
      content: "message two",
      contentType: "text"
    },
    {
      id: 3,
      fromId: 153628701,
      toId: 153628701,
    //   chatId: "1",
      date: new Date(),
      content: "message three",
      contentType: "text"
    }
  ];