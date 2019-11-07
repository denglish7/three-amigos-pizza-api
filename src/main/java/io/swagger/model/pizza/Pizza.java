package io.swagger.model.pizza;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pizzas")
public class Pizza {

  @ApiModelProperty(hidden=true)
  private String _id;
  private String name;
  private String sizeId;
  private String crustId;
  private List<String> toppingIds;

  public Pizza(String _id, String name, String sizeId, String crustId,
      List<String> toppingIds) {
    this._id = _id;
    this.name = name;
    this.sizeId = sizeId;
    this.crustId = crustId;
    this.toppingIds = toppingIds;
  }

  public String get_id() {
    return _id;
  }

  /**
   * Get name
   * @return name
   */
  @ApiModelProperty(example = "Cheese")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get sizeId
   * @return sizeId
   */
  public String getSizeId() {
    return sizeId;
  }

  public void setSizeId(String sizeId) {
    this.sizeId = sizeId;
  }

  /**
   * Get crustId
   * @return crustId
   */
  public String getCrustId() {
    return crustId;
  }

  public void setCrustId(String crustId) {
    this.crustId = crustId;
  }

  /**
   * Get toppingIds
   * @return toppingIds
   */
  public List<String> getToppingIds() {
    return toppingIds;
  }

  public void setToppingIds(List<String> toppingIds) {
    this.toppingIds = toppingIds;
  }
}
