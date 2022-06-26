package kr.co.antoon.character.dto.reqeust;

public record CharacterRequest (
        String name,
        String content,
        String color,
        Long webtoonId,
        String imageUrl
){ }
