package com.github.skipua.mongojackjacksonissue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.mongojack.Id;

@Data
public class SampleEntity {
  @Id
  private String mongoId;
  private String someProperty;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String someWriteOnlyProperty;
}
