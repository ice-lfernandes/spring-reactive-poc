package br.com.ldf.springreactivepoc.api.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Response {
  private LocalDate date = LocalDate.now();
  private int output;

  public Response(int output) {
    this.output = output;
  }
}
