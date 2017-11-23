/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.tools;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author csiqueira
 */
@Component
public class CustomDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = jp.getText();
		try {
			return format.parse(date);
		} catch (ParseException e) {
			try {
				return new Date(jp.getLongValue());
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}

	}
}
