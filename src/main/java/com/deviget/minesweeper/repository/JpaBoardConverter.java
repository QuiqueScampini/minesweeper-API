package com.deviget.minesweeper.repository;

import com.deviget.minesweeper.domain.exception.InternalException;
import com.deviget.minesweeper.model.Cell;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JpaBoardConverter implements AttributeConverter<List<List<Cell>>, byte[]> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] convertToDatabaseColumn(List<List<Cell>> board) {
		byte[] jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsBytes(board);
		} catch (JsonProcessingException e) {
			throw new InternalException(e.getMessage());
		}
		return jsonString;
	}

	@Override
	public List<List<Cell>> convertToEntityAttribute(byte[] dbData) {
		List<List<Cell>> board = new ArrayList<>();
		try {
			board = objectMapper.readValue(dbData, new TypeReference<>() {});
		} catch (IOException e) {
			throw new InternalException(e.getMessage());
		}
		return board;
	}
}
