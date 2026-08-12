package com.example.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("concat_200_response")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-08-11T21:41:58.569195509+09:00[Asia/Tokyo]", comments = "Generator version: 7.4.0")
public class Concat200Response   {
  private @Valid String out;

  /**
   **/
  public Concat200Response out(String out) {
    this.out = out;
    return this;
  }

  
  @JsonProperty("out")
  @NotNull
  public String getOut() {
    return out;
  }

  @JsonProperty("out")
  public void setOut(String out) {
    this.out = out;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Concat200Response concat200Response = (Concat200Response) o;
    return Objects.equals(this.out, concat200Response.out);
  }

  @Override
  public int hashCode() {
    return Objects.hash(out);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Concat200Response {\n");
    
    sb.append("    out: ").append(toIndentedString(out)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

