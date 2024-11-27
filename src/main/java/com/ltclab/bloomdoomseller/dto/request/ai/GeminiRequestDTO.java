package com.ltclab.bloomdoomseller.dto.request.ai;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GeminiRequestDTO {
    public ArrayList<Content> contents;
}

