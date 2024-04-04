﻿using Lab2.DTO.Interface;

namespace Lab2.DTO
{
    public class NoteResponseTo(int Id, int? TweetId, string? Content, Tweet? Tweet) : IResponseTo
    {
        public int Id { get; set; } = Id;

        public int? TweetId { get; set; } = TweetId;

        public string? Content { get; set; } = Content;

        public virtual Tweet? Tweet { get; set; } = Tweet;
    }
}
