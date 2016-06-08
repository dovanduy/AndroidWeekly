package io.github.cyning.mobilenews.cartoon.model;

import java.util.List;

/**
 * Created by cyning on 4/9/16.
 */
public class CartoonListWrapper {

  private int error_code = -1;
  private List<CartoonInfo> data;

  @Override public String toString() {
    return "CartoonListWrapper{" +
        "error_code=" + error_code +
        ", data=" + data +
        '}';
  }

  public int getError_code() {
    return error_code;
  }

  public List<CartoonInfo> getData() {
    return data;
  }

  public boolean isSuccess(){
    return  error_code == 0;
  }
}
